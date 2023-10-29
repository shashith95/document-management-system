package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.commons.constant.EventTypes;
import com.api.documentmanagementservice.model.context.HeaderContext;
import org.bson.Document;

import java.util.List;
import java.util.Map;

public interface CustomMongoService {
    List<Map<String, Object>> getDocumentFileDetailsDto(Map<String, Object> requestMap,
                                                        String hospital);

    Document save(Map<String, Object> updatePropertyRequestMap) throws DmsException;

    Document getDocumentById(String documentId) throws DmsException;

    Document findAndReplaceDocument(String documentId, Map<String, Object> newMongoData) throws DmsException;

    void updateDocument(Map<String, Object> condition, Map<String, Object> dataToUpdate);

    boolean doesDocumentExist(String documentId);

    void saveAuditTrail(String sourceId, HeaderContext headerContext, EventTypes eventTypes);


    List<Map> getDocumentDetailsByUserFilers(Map<String, Object> requestMap,
                                             String hospital);
}
