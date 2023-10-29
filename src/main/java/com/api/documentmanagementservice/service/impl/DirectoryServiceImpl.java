package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.*;
import com.api.documentmanagementservice.model.table.UserPreference;
import com.api.documentmanagementservice.repository.FolderHierarchyRepository;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.service.DirectoryService;
import com.api.documentmanagementservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_NO_DATA;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_INVALID_INPUT_FORMAT;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.DM_DMS_009;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.DM_DMS_022;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectoryServiceImpl implements DirectoryService {
    private final UserPreferenceService userPreferenceService;
    private final FolderHierarchyRepository folderHierarchyRepository;
    private final HeaderContext headerContext;
    private final CustomMongoService customMongoService;

    /**
     * Retrieves directory information based on the provided request body.
     *
     * @param requestBody A Map containing the request body data, typically containing key-value pairs.
     * @return A ResponseEntity containing a CommonResponse object with directory information.
     * @throws BadRequestException If the request body is malformed or contains invalid data.
     */

    @Override
    public ResponseEntity<CommonResponse> getDirectory(Map<String, Object> requestBody) throws BadRequestException {

        // Create HeaderValues object to store header information
        HeaderValues headerValues = new HeaderValues(headerContext.getTenantId(), headerContext.getHospitalId(), headerContext.getUser());
        // Retrieve property names based on header values
        Optional<List<String>> propertyNameList = getPropertyNamesOfHierarchySetting(headerValues);
        List<Map> documentDetailsMapList = customMongoService.getDocumentDetailsByUserFilers(requestBody, headerContext.getHospitalId());
        // Get a list of properties that exist in the documents
        List<String> existingPropertyList = getPropertiesExistOnDocuments(documentDetailsMapList, propertyNameList.get());
        // Create an initial filter list and add the request body
        List<Map<String, Object>> initialFilterList = Collections.singletonList(requestBody);
        // Generate directory hierarchy using existing properties and initial filter
        return generateHierarchy(documentDetailsMapList, existingPropertyList, initialFilterList);

    }

    /**
     * Retrieves a list of properties that exist on the provided list of document details.
     * Properties are matched based on a provided list of property names, considering case and spaces.
     *
     * @param documentDetailsMapList A list of maps containing document details.
     * @param propertyNameList       A list of property names to check for existence.
     * @return A list of property names that exist in the document details.
     * @throws ResponseStatusException If no matching properties are found, a BAD_REQUEST response is thrown.
     */
    private List<String> getPropertiesExistOnDocuments(List<Map> documentDetailsMapList, List<String> propertyNameList) {

        // Filter the property names to check if they exist in the document details
        List<String> documentAvailableProperties = propertyNameList.stream().filter(propertyName -> documentDetailsMapList.stream().anyMatch(documentDetailsMap -> documentDetailsMap.keySet().stream().anyMatch(key -> {
            // Transform the document key and property name to be case-insensitive and space-removed
            String transformedDocumentKey = key.toString().replace(" ", "").toLowerCase();
            String transformedPropertyName = propertyName.replace(" ", "").toLowerCase();
            return transformedDocumentKey.equalsIgnoreCase(transformedPropertyName);
        }))).toList();

        // If matching properties are found, return the list
        if (!documentAvailableProperties.isEmpty()) {
            return documentAvailableProperties;
        } else {
            // If no matching properties are found, throw a BAD_REQUEST exception with a specific message
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DM_DMS_022.getMessage());
        }
    }


    private Optional<List<String>> getPropertyNamesOfHierarchySetting(HeaderValues headerValues) throws BadRequestException {
        return Optional.ofNullable(findSelectedHierarchy(headerValues).orElseThrow(() -> new BadRequestException(DB_NO_DATA.getCode(), "No hierarchy setting found")));
    }

    /**
     * Finds and retrieves the selected hierarchy based on user preferences and header values.
     *
     * @param headerValues The header values containing hospital ID, tenant ID, and user information.
     * @return An Optional containing a list of selected hierarchy items, or an empty Optional if not found.
     * @throws BadRequestException if an invalid property preference is encountered.
     */
    private Optional<List<String>> findSelectedHierarchy(HeaderValues headerValues) throws BadRequestException {
        Optional<UserPreference> userPreference = userPreferenceService.isUserPreferenceExist("HIERARCHY");

        if (userPreference.isEmpty() || userPreference.get().getPreference() == 1) {
            // Retrieve the default hierarchy based on hospital ID and tenant ID
            return fetchHierarchy(false, headerValues);
        } else if (userPreference.get().getPreference() == 2) {
            // Retrieve custom hierarchy based on user, hospital ID, and tenant ID
            return fetchHierarchy(true, headerValues);
        } else {
            throw new BadRequestException(REQ_INVALID_INPUT_FORMAT.getCode(), "Invalid property preference");
        }
    }

    /**
     * Fetches the hierarchy based on user preferences, hospital ID, and tenant ID.
     *
     * @param isCustom     A boolean indicating whether to retrieve custom hierarchy.
     * @param headerValues The header values containing hospital ID, tenant ID, and user information.
     * @return An Optional containing a list of property names for the selected hierarchy, or an empty Optional if not found.
     */
    private Optional<List<String>> fetchHierarchy(boolean isCustom, HeaderValues headerValues) {
        Optional<List<PropertyListDto>> propertyLists = folderHierarchyRepository.findPropertiesByUserAndIsCustomAndHospitalIdAndTenantId(
                headerValues.user(),
                isCustom,
                headerValues.hospitalId(),
                headerValues.tenantId());
        // Map PropertyListDto objects to a list of property names
        return propertyLists.map(list -> list.stream().map(PropertyListDto::name).toList());
    }


    private List<String> removePropertiesInInitialConditions(List<String> properties, Map<String, Object> initialConditions) {

        return properties
                .stream()
                .filter(eachProperty -> !initialConditions.containsKey(eachProperty))
                .toList();
    }

    /**
     * Generates a hierarchy structure based on the provided document details, initial filters, and properties.
     *
     * @param documentDetailsMapList      A list of maps containing document details.
     * @param documentAvailableProperties A list of available properties for document filtering.
     * @param listOfInitialFilters        A list of initial filters to apply to the hierarchy.
     * @return A ResponseEntity containing the generated hierarchy structure and properties.
     */
    private ResponseEntity<CommonResponse> generateHierarchy(List<Map> documentDetailsMapList,
                                                             List<String> documentAvailableProperties,
                                                             List<Map<String, Object>> listOfInitialFilters) {

        // Covert property names to match with Mongo field names
        List<String> finalHierarchyProperties = documentAvailableProperties
                .stream()
                .map(property -> property.replace(" ", ""))
                .toList();

        // Generate branches with initial conditions applied
        BranchesResult branchesWithInitialCondition = getAllBranches(
                documentDetailsMapList,
                finalHierarchyProperties,
                listOfInitialFilters);

        // Remove unnecessary values from the branches
        List<Map<String, Object>> branches = removeUnnecessaryValuesFromBranches(
                branchesWithInitialCondition.conditions(),
                listOfInitialFilters.get(0), finalHierarchyProperties);

        // Generate the hierarchy structure from the filtered branches
        Map<String, Object> result = getTrees(branches, finalHierarchyProperties);

        // Create a hierarchy data map and a properties data map
        Map<String, Object> hierarchyData = new HashMap<>();
        hierarchyData.put("hierarchy", result);
        Map<String, Object> propertiesData = new HashMap<>();
        propertiesData.put("properties", finalHierarchyProperties);

        // Create the return result as a list of hierarchy and properties data
        List<Object> returnResult = Arrays.asList(hierarchyData, propertiesData);

        // Generate a response entity with a success status code and response message
        return generateResponse(HttpStatus.OK, DM_DMS_009.getMessage(), DM_DMS_009.name(), returnResult);
    }


    /**
     * Retrieves a list of conditions that can be used to filter a list of document details.
     *
     * @param documentDetailsMapList A list of maps containing document details.
     * @param propertyList           A list of property names to filter on (with spaces).
     * @param conditionList          A list of initial conditions to apply (keys without white spaces as data stored in MongoDB).
     * @return A {@link BranchesResult} object containing the filtered conditions and properties.
     */
    private BranchesResult getAllBranches(List<Map> documentDetailsMapList, List<String> propertyList, List<Map<String, Object>> conditionList) {
        // Create a new list of conditions by applying the given conditions to the document details
        List<Map<String, Object>> newConditions = conditionList.stream().flatMap(condition -> {
            try {
                // Filter the document details based on the current condition
                List<String> distinctPropertyValues = documentDetailsMapList.stream().filter(documentDetailMap -> {
                            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                                String propertyName = entry.getKey();
                                Object expectedValue = entry.getValue();
                                // Check if the document has the same key and value as in the condition
                                if (!documentDetailMap.containsKey(propertyName) ||
                                        !String.valueOf(documentDetailMap.get(propertyName)).equals(String.valueOf(expectedValue))) {
                                    return false; // Document does not match the condition
                                }
                            }
                            return true; // Include this map in the filtered list
                        })
                        // removes leading and trailing spaces from the property name, and converts
                        .map(documentDetailMap -> documentDetailMap.get(propertyList.get(0).replace(" ", "")).toString()).distinct().toList();

                // If no distinct property values are found, wrap the original condition in a Stream
                if (distinctPropertyValues.isEmpty() || distinctPropertyValues.size() == 0) {
                    return Stream.of(condition);
                } else {
                    // Create updated conditions with distinct property values
                    return distinctPropertyValues.stream().map(distinct -> {
                        Map<String, Object> updatedCondition = new HashMap<>(condition);
                        updatedCondition.put(propertyList.get(0).replace(" ", ""), distinct);
                        return updatedCondition;
                    });
                }
            } catch (Exception e) {
                // In case of an exception, wrap the original condition in a Stream
                return Stream.of(condition);
            }
        }).toList();

        // Remove the first property from the propertyList
        List<String> tailedPropertyList = propertyList.stream().skip(1).toList();

        // Recursively call the method with the updated propertyList and new conditions
        if (!tailedPropertyList.isEmpty()) {
            return getAllBranches(documentDetailsMapList, tailedPropertyList, newConditions);
        } else {
            // When there are no more properties to filter, return the result
            return new BranchesResult(tailedPropertyList, newConditions);
        }
    }

    /**
     * Generates a tree-like structure from a list of branches based on the specified properties.
     * Each node in the tree represents a unique value of the first property in properties,
     * and child nodes represent subsequent properties.
     *
     * @param branches   A list of branches, where each branch is represented as a Map of properties.
     * @param properties A list of property names to use for generating the tree structure.
     * @return A Map representing the generated tree structure.
     */
    private Map<String, Object> getTrees(List<Map<String, Object>> branches, List<String> properties) {

        // Group branches by the first property in properties
        Map<Object, List<Map<String, Object>>> allDataToTrees = branches.stream().collect(Collectors.groupingBy(
                branch -> branch.get(properties.get(0).replace(" ", ""))));

        // Create a list of TransformedBranchesResult objects from the grouped data
        List<TransformedBranchesResult> transformedBranchesResultList = new ArrayList<>();
        for (Map.Entry<Object, List<Map<String, Object>>> entry : allDataToTrees.entrySet()) {
            transformedBranchesResultList.add(new TransformedBranchesResult(entry.getKey(), entry.getValue()));
        }

        // Generate the tree structure using the TransformedBranchesResult list and remaining properties
        return makeTree(transformedBranchesResultList, properties.subList(1, properties.size()));
    }

    /**
     * Generates a tree-like structure from a list of TransformedBranchesResult objects
     * based on the specified propertyList. Each node in the tree represents a unique value
     * of the first property in propertyList, and child nodes represent subsequent properties.
     *
     * @param dataToTree   A list of TransformedBranchesResult objects to be transformed into a tree.
     * @param propertyList A list of property names to use for generating the tree structure.
     * @return A Map representing the generated tree structure.
     */
    private Map<String, Object> makeTree(List<TransformedBranchesResult> dataToTree, List<String> propertyList) {

        // If propertyList is empty, create leaf nodes with unique values from dataToTree
        if (propertyList.isEmpty()) {
            return dataToTree.stream().collect(Collectors.toMap(data -> data.item().toString(), data -> new Object()));
        }
        // If propertyList contains only one property, create leaf nodes with unique values
        // from the specified property in dataToTree
        else if (propertyList.size() == 1) {
            return dataToTree.stream().collect(Collectors.toMap(result -> result.item().toString(), result -> {
                // Extract values of the last property in propertyList from child nodes
                Set<Object> endLeaf = result.children().stream().map(
                        child -> child.get(propertyList.get(0).replace(" ", ""))).collect(Collectors.toSet());
                // If all values are the same, use a single value; otherwise, use a set
                return endLeaf.size() == 1 ? endLeaf.iterator().next() : endLeaf;
            }));
        }
        // If propertyList has multiple properties, recursively create tree nodes
        else {
            return dataToTree.stream().collect(Collectors.toMap(result -> result.item().toString(), result -> {
                // Group child data by the current property in propertyList
                Map<Object, List<Map<String, Object>>> groupedData = result.children()
                        .stream()
                        .collect(Collectors.groupingBy(eachData ->
                                eachData.get(propertyList.get(0).replace(" ", ""))));
                // Transform grouped data into TransformedBranchesResult objects
                List<TransformedBranchesResult> transformedBranchesResultList = groupedData
                        .entrySet()
                        .stream()
                        .map(entry -> new TransformedBranchesResult(entry.getKey(), entry.getValue()))
                        .toList();
                // Recursively call makeTree with the remaining properties
                return makeTree(transformedBranchesResultList, propertyList.subList(1, propertyList.size()));
            }));
        }
    }


    private List<Map<String, Object>> removeUnnecessaryValuesFromBranches(List<Map<String, Object>> branches,
                                                                          Map<String, Object> initialConditions,
                                                                          List<String> properties) {

        List<String> keysToRemove = initialConditions
                .keySet()
                .stream()
                .filter(eachKey -> !properties.contains(eachKey))
                .toList();

        return branches
                .stream()
                .map(each -> {
                    Map<String, Object> updatedBranch = new HashMap<>(each);
                    keysToRemove.forEach(updatedBranch::remove);
                    return updatedBranch;
                })
                .toList();
    }


}
