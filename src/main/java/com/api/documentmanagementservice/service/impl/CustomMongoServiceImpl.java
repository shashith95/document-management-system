package com.api.documentmanagementservice.service.impl;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.commons.constant.EventTypes;
import com.api.documentmanagementservice.config.MongoProperties;
import com.api.documentmanagementservice.model.collection.AuditTrailCollection;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.FileDetailsDto;
import com.api.documentmanagementservice.model.table.FileDetails;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.api.documentmanagementservice.repository.mongo.AuditTrailMongoRepository;
import com.api.documentmanagementservice.service.CustomMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.api.documentmanagementservice.commons.DateTimeUtils.getCurrentTime;
import static com.api.documentmanagementservice.commons.constant.CommonContent.*;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_RETRIEVAL_ERROR;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_UPDATE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomMongoServiceImpl implements CustomMongoService {
    private final MongoTemplate mongoTemplate;
    private final FileDetailsRepository fileDetailsRepository;
    private final MongoProperties mongoProperties;
    private final AuditTrailMongoRepository auditTrailMongoRepository;

    /**
     * Converts a list of FileDetails objects to FileDetailsDto objects.
     *
     * @param fileDetailsList The list of FileDetails to convert.
     * @return List of FileDetailsDto objects.
     */
    @NotNull
    private static List<FileDetailsDto> getDocumentFileDetailsDto(List<FileDetails> fileDetailsList) {

        return fileDetailsList.stream()
                .map(fileDetails -> new FileDetailsDto(
                        fileDetails.getId(),
                        fileDetails.getFileName(),
                        fileDetails.getExtension(),
                        fileDetails.getSeq().intValue()
                ))
                .toList();
    }

    /**
     * Retrieves document details along with associated file details based on dynamic filters.
     *
     * @param requestMap A map containing dynamic filters for the query.
     * @param hospital   The hospital value obtained from request headers.
     * @return A list of documents with associated file details and 'Status' field removed.
     */
    @Override
    public List<Map<String, Object>> getDocumentFileDetailsDto(Map<String, Object> requestMap,
                                                               String hospital) {
        // Generate criteria based on dynamic request filters
        Criteria criteria = generateFilterCriteria(requestMap, hospital);

        Query query = Query.query(criteria);

        log.debug("Retrieving mongo data...");
        List<Map> documentCollection = mongoTemplate.find(query, Map.class, mongoProperties.getDocumentDetailsCollection());
        Set<String> documentIdSet = documentCollection.stream()
                .map(map -> (String) map.get(ID.getContent()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        log.debug("Retrieving table data...");
        Optional<List<FileDetails>> fileDetailsList = fileDetailsRepository.findByDocumentIdIn(documentIdSet);

        log.debug("Mapping final response...");
        return documentCollection
                .stream()
                .flatMap(document -> {
                    String documentId = (String) document.get(ID.getContent());

                    List<FileDetailsDto> fileDetailsDtoList = new ArrayList<>();
                    fileDetailsList.ifPresentOrElse(
                            details -> {
                                List<FileDetails> availableList = details
                                        .stream()
                                        .filter(fileDetails -> fileDetails.getDocumentId().equals(documentId))
                                        .toList();
                                fileDetailsDtoList.addAll(getDocumentFileDetailsDto(availableList));
                            },
                            () -> log.warn("No file data for documentId = {}", documentId)
                    );

                    // Check if fileDetailsDtoList is not empty
                    if (!fileDetailsDtoList.isEmpty()) {
                        // Add fileDetails to the document
                        Map<String, Object> modifiedDocument = new HashMap<>(document);
                        modifiedDocument.put("fileDetails", fileDetailsDtoList); //adding file details list
                        modifiedDocument.remove(STATUS.getContent()); //removing Status since it's not needed for response
                        return Stream.of(modifiedDocument);
                    } else {
                        return Stream.empty(); // Skip this document if fileDetailsDtoList is empty
                    }
                })
                .toList();
    }

    /**
     * Generates filter criteria based on dynamic request filters and default fields.
     *
     * @param requestMap A map containing dynamic filters for the query.
     * @param hospital   The hospital value obtained from request headers.
     * @return A Criteria object representing the filter criteria.
     */
    private Criteria generateFilterCriteria(Map<String, Object> requestMap, String hospital) {
        Criteria criteria = new Criteria();

        // Add default filters
        if (requestMap.containsKey(CLUSTER_MRN.getContent())) {
            criteria = criteria.and(CLUSTER_MRN.getContent()).is(requestMap.get(CLUSTER_MRN.getContent()));
        }
        if (requestMap.containsKey(STATUS.getContent())) {
            criteria = criteria.and(STATUS.getContent()).is(requestMap.get(STATUS.getContent()));
        }
        // This comes in request headers, not from the request
        criteria = criteria.and(HOSPITAL.getContent()).is(hospital);
        if (requestMap.containsKey(TENANT.getContent())) {
            criteria = criteria.and(TENANT.getContent()).is(requestMap.get(TENANT.getContent()));
        }

        // Add additional filters
        Criteria finalCriteria = criteria;
        requestMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(CLUSTER_MRN.getContent()) &&
                        !entry.getKey().equals("Tenant") &&
                        !entry.getKey().equals(STATUS.getContent()) && !entry.getKey().equals("Hospital"))
                .forEach(entry -> {
                    if (entry.getValue() instanceof List) {
                        finalCriteria.and(entry.getKey()).in((List<?>) entry.getValue());
                    } else {
                        finalCriteria.and(entry.getKey()).is(entry.getValue());
                    }
                });

        return finalCriteria;
    }

    /**
     * Saves a document after removing file details.
     *
     * @param updatePropertyRequestMap The document to be saved.
     * @return The saved document.
     * @throws RuntimeException if an error occurs during the save operation.
     */
    @Override
    public Document save(Map<String, Object> updatePropertyRequestMap) throws DmsException {
        try {
            Document docToSave = new Document(updatePropertyRequestMap);

            Document savedDoc = mongoTemplate.save(docToSave, mongoProperties.getDocumentDetailsCollection());
            log.info("Document saved successfully. ID: {}", savedDoc.get(ID.getContent()));

            return savedDoc;
        } catch (Exception e) {
            log.error("Error occurred in mongo save operation", e);
            throw new DmsException(DB_DATA_UPDATE_ERROR.getCode(), "Error occurred in mongo save operation");
        }
    }

    /**
     * Retrieves a document from the MongoDB collection by its unique identifier.
     *
     * @param documentId The unique identifier of the document to be retrieved.
     * @return A {@link Document} object representing the retrieved document.
     * @throws DmsException If an error occurs during the retrieval operation.
     */
    @Override
    public Document getDocumentById(String documentId) throws DmsException {
        Criteria criteria = Criteria.where(ID.getContent()).is(documentId);

        Query query = Query.query(criteria);
        try {
            Map<String, Object> documentMap = mongoTemplate.findOne(query, Map.class, mongoProperties.getDocumentDetailsCollection());

            return new Document(documentMap);

        } catch (Exception e) {
            log.error("Error occurred in mongo get document by id operation", e);
            throw new DmsException(DB_DATA_RETRIEVAL_ERROR.getCode(), DB_DATA_RETRIEVAL_ERROR.getDescription());
        }
    }

    /**
     * Finds a document in the MongoDB collection by its unique identifier and replaces its data with new values.
     *
     * @param documentId   The unique identifier of the document to be found and replaced.
     * @param newMongoData A map containing the new data to replace the existing document's data.
     * @return A {@link Document} object representing the document with its updated data after replacement.
     * @throws DmsException If an error occurs during the find and replace operation.
     */
    @Override
    public Document findAndReplaceDocument(String documentId, Map<String, Object> newMongoData) throws DmsException {
        Criteria criteria = Criteria.where(ID.getContent()).is(documentId);

        Query query = Query.query(criteria);
        try {
            Map<String, Object> documentMap = mongoTemplate.findAndReplace(query, newMongoData,
                    mongoProperties.getDocumentDetailsCollection());

            return new Document(documentMap);

        } catch (Exception e) {
            log.error("Error occurred in mongo get document by id operation", e);
            throw new DmsException(DB_DATA_UPDATE_ERROR.getCode(), "Error occurred in mongo while replacing the document");
        }
    }

    /**
     * Updates a document in the MongoDB collection based on a given condition and the data to update.
     *
     * @param condition    A map representing the condition to match for the document to update.
     *                     Typically, this includes the document's unique identifier.
     * @param dataToUpdate A map containing the data to update in the document. This may include fields like
     *                     status and delete reason.
     */
    @Override
    public void updateDocument(Map<String, Object> condition, Map<String, Object> dataToUpdate) {
        // Create a query to find the document by its ID
        Criteria criteria = Criteria.where(ID.getContent()).is(condition.get(ID.getContent()));
        Query query = Query.query(criteria);
        // Create an update object to set the new status and delete_reason
        Update update = new Update()
                .set(STATUS.getContent(), dataToUpdate.get(STATUS.getContent()))
                .set(DELETE_REASON.getContent(), dataToUpdate.get(DELETE_REASON.getContent()));

        // Perform the update
        mongoTemplate.updateFirst(query, update, mongoProperties.getDocumentDetailsCollection());
    }

    /**
     * Checks if a document with the specified ID exists in the MongoDB collection.
     *
     * @param documentId The unique identifier of the document to check.
     * @return {@code true} if the document exists, {@code false} otherwise.
     */
    @Override
    public boolean doesDocumentExist(String documentId) {
        // Create a query to find the document by its ID
        Criteria criteria = Criteria.where(ID.getContent()).is(documentId);
        Query query = new Query(criteria);
        // Use the count method to check if the document exists
        long count = mongoTemplate.count(query, mongoProperties.getDocumentDetailsCollection());
        // If count is greater than 0, the document exists; otherwise, it doesn't
        return count > 0;
    }

    /**
     * Saves an audit trail record for a specific event.
     *
     * @param sourceId      The identifier of the event source.
     * @param headerContext The header context containing user, hospital, and tenant information.
     * @param eventTypes    The type of event (e.g., CREATE, UPDATE, DELETE).
     */
    @Override
    public void saveAuditTrail(String sourceId, HeaderContext headerContext, EventTypes eventTypes) {
        AuditTrailCollection auditTrailCollection = AuditTrailCollection.builder()
                .sourceId(sourceId)
                .source(DOCUMENT.getContent())
                .eventType(eventTypes.getValue()) // Consider using the correct event type
                .eventDateTime(getCurrentTime().toString())
                .user(headerContext.getUser())
                .hospitalId(headerContext.getHospitalId())
                .tenantId(headerContext.getTenantId())
                .build();
        auditTrailMongoRepository.save(auditTrailCollection);
        log.info("AuditRecord saved Successfully for file id: {}", sourceId);
    }

    /**
     * Retrieves document details based on user-specific filters and a hospital identifier.
     *
     * @param requestMap A Map containing user-specific filter criteria.
     * @param hospital   A String representing the hospital identifier.
     * @return A List of Map objects containing document details that match the specified filters.
     */
    @Override
    public List<Map> getDocumentDetailsByUserFilers(Map<String, Object> requestMap, String hospital) {
        Map<String, Object> mongoCondition = requestMap.entrySet().stream()
                .collect(Collectors.toMap(entryA -> entryA.getKey().replace(" ", ""), Map.Entry::getValue));
        Criteria criteria = generateFilterCriteria(mongoCondition, hospital.replace(" ", ""));
        Query query = Query.query(criteria);
        return mongoTemplate.find(query, Map.class, mongoProperties.getDocumentDetailsCollection());
    }


}
