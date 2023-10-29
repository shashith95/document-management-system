package com.api.documentmanagementservice.commons.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    CREATED("Created successfully"),
    RESTORE("Restored successfully"),
    UPDATED("Updated successfully"),
    DELETED("Deleted successfully"),
    SUCCESS("Returned data successfully"),
    UPLOAD_SETTING_EXISTS("File uploading settings already exist"),
    UPLOAD_SETTING_NOT_FOUND("File uploading settings not found"),
    SCANNER_PROFILE_EXISTS("Scanner profile already exist"),
    SCANNER_PROFILE_NOT_FOUND("Scanner profile is not available"),
    VIEW_COMPONENT_EXISTS("View component settings already exist for this component"),
    DEFAULT_PROPERTY_NOT_FOUND("Default property attribute not found for this property"),
    FILE_PROPERTY_NOT_FOUND("File properties not found"),

    DM_DMS_001("Created successfully"),
    DM_DMS_002("Updated successfully"),
    DM_DMS_003("Database issue"),
    DM_DMS_004("Request body is not valid"),
    DM_DMS_005("Conflict"),
    DM_DMS_006("Required representation not available in request payload"),
    DM_DMS_007("Issue in backend services"),
    DM_DMS_008("No matching data to update"),
    DM_DMS_009("Returned data successfully"),
    DM_DMS_010("Deleted successfully"),
    DM_DMS_011("No x-group in request headers"),
    DM_DMS_012("No x-hospital in request headers"),
    DM_DMS_013("No x-user in request headers"),
    DM_DMS_014("x-group is empty"),
    DM_DMS_015("x-hospital is empty"),
    DM_DMS_016("x-user value is empty"),
    DM_DMS_017("Internal error in DSE"),
    DM_DMS_018("No matching data to delete"),
    DM_DMS_021("Folder hierarchy already exists"),
    DM_DMS_022("Folder hierarchy not found"),
    DM_DMS_023("Property already exists"),
    DM_DMS_024("Attribute already exists"),
    DM_DMS_025("No file property found for this id"),
    DM_DMS_026("This property is not deletable"),
    DM_DMS_027("This attribute is not deletable"),
    DM_DMS_028("File uploading settings already exist"),
    DM_DMS_029("File uploading settings not found"),
    DM_DMS_030("No attribute found for this id"),
    DM_DMS_031("Duplicate attributes available"),
    DM_DMS_032("Scanner profile already exists"),
    DM_DMS_033("No scanner profile found for this id"),
    DM_DMS_040("Must post with a file and 'data' form part"),
    DM_DMS_041("Must post with a file and 'file-data' form part"),
    DM_DMS_042("No file data"),
    DM_DMS_043("Must post with a file and 'document-data' form part"),
    DM_DMS_044("Each file data JSON must contain 'Key' data, 'index' data, and 'name' data"),
    DM_DMS_045("'key' data in file data doesn't match the uploaded file key"),
    DM_DMS_046("File indexes not in the sequence or have duplicates"),
    DM_DMS_047("Few mandatory properties missing"),
    DM_DMS_048("Unidentified error occurs"),
    DM_DMS_049("Bad request"),
    DM_DMS_050("Property id and default attribute id not matching"),
    DM_DMS_051("Default property attribute not found for this property"),
    DM_DMS_052("Cannot get data from a different tenant"),
    DM_DMS_053("Invalid bucket name or you don't have the bucket permission"),
    DM_DMS_054("Cannot change the file status"),
    DM_DMS_055("Data for this component is not available"),
    DM_DMS_056("Cannot update data from a different tenant"),
    DM_DMS_057("No data available for this document id"),
    DM_DMS_058("Uploaded file successfully"),
    DM_DMS_059("Status in document data cannot be false"),
    DM_DMS_060("Request contains invalid filter keys"),
    DM_DMS_061("Invalid property ids contained in the folder hierarchy array"),
    DM_DMS_062("Duplicate property ids available"),
    DM_DMS_063("No hierarchy setting found"),
    DM_DMS_064("View component settings already exist for this component"),
    DM_DMS_065("Scan component settings already exist for this component"),
    DM_DMS_066("Error while unzipping files"),
    DM_DMS_067("Error while creating an input stream from the source"),
    DM_DMS_068("Error while creating input stream from the source"),
    DM_DMS_069("No documents for this file id"),
    DM_DMS_070("Error while renaming"),
    DM_DMS_071("The selected file has been deleted by another user"),
    DM_DMS_072("Invalid property data or attribute data contained in the request body"),
    DM_DMS_073("Document name already exists"),
    DM_DMS_074("Component id must be a positive integer"),
    DM_DMS_075("No matching document metadata found"),
    DM_DMS_076("File name should not contain '/'"),
    DM_DMS_077("One or more header values are empty"),
    DM_DMS_078("Invalid Request, validation issue"),
    DM_DMS_079("Request Conflict, Id already exists"),
    DM_DMS_080("Request Conflict, Id already exists");
    private final String message;
}
