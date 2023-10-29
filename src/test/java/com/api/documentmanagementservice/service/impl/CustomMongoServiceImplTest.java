package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.commons.constant.EventTypes;
import com.api.documentmanagementservice.config.MongoProperties;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.collection.AuditTrailCollection;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.table.FileDetails;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.api.documentmanagementservice.repository.mongo.AuditTrailMongoRepository;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomMongoServiceImpl.class, MongoProperties.class,
        HeaderContext.class})
@ExtendWith(SpringExtension.class)
class CustomMongoServiceImplTest {
    @Autowired
    private HeaderContext headerContext;

    @MockBean
    private AuditTrailMongoRepository auditTrailMongoRepository;

    @Autowired
    private CustomMongoServiceImpl customMongoServiceImpl;

    @MockBean
    private FileDetailsRepository fileDetailsRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto2() {
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("Retrieving mongo data..."));
        assertThrows(IllegalStateException.class,
                () -> customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital"));
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto3() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);

        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto4() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);

        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        mapList.add(new HashMap<>());
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto5() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("Retrieving mongo data...", "42");
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto6() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Retrieving mongo data...", "42");
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }


    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto8() {
        Optional<List<FileDetails>> emptyResult = Optional.empty();
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(emptyResult);

        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto9() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Tenant", "42");
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto10() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Status", "42");
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto11() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Hospital", "42");
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto12() {
        Optional<List<FileDetails>> ofResult = Optional.of(new ArrayList<>());
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Retrieving mongo data...", new ArrayList<>());
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(requestMap, "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentFileDetailsDto(Map, String)}
     */
    @Test
    void testGetDocumentFileDetailsDto13() {
        ArrayList<FileDetails> fileDetailsList = new ArrayList<>();
        fileDetailsList.add(FileDetails.builder()
                .documentId("42")
                .extension("Extension")
                .fileName("foo.txt")
                .id("42")
                .seq(1L)
                .status(true)
                .build());
        Optional<List<FileDetails>> ofResult = Optional.of(fileDetailsList);
        when(fileDetailsRepository.findByDocumentIdIn(Mockito.<Set<String>>any())).thenReturn(ofResult);

        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        assertTrue(customMongoServiceImpl.getDocumentFileDetailsDto(new HashMap<>(), "Hospital").isEmpty());
        verify(fileDetailsRepository).findByDocumentIdIn(Mockito.<Set<String>>any());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }



    /**
     * Method under test: {@link CustomMongoServiceImpl#save(Map)}
     */
    @Test
    void testSave() throws DmsException {
        Document document = new Document();
        when(mongoTemplate.save(Mockito.<Document>any(), Mockito.<String>any())).thenReturn(document);
        Document actualSaveResult = customMongoServiceImpl.save(new HashMap<>());
        assertSame(document, actualSaveResult);
        assertTrue(actualSaveResult.isEmpty());
        verify(mongoTemplate).save(Mockito.<Document>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#save(Map)}
     */
    @Test
    void testSave2() throws DmsException {
        when(mongoTemplate.save(Mockito.<Document>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("Document saved successfully. ID: {}"));
        assertThrows(DmsException.class, () -> customMongoServiceImpl.save(new HashMap<>()));
        verify(mongoTemplate).save(Mockito.<Document>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentById(String)}
     */
    @Test
    void testGetDocumentById() throws DmsException {
        when(
                mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        assertTrue(customMongoServiceImpl.getDocumentById("42").isEmpty());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentById(String)}
     */
    @Test
    void testGetDocumentById2() throws DmsException {
        when(
                mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("Error occurred in mongo get document by id operation"));
        assertThrows(DmsException.class, () -> customMongoServiceImpl.getDocumentById("42"));
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#findAndReplaceDocument(String, Map)}
     */
    @Test
    void testFindAndReplaceDocument() throws DmsException {
        when(mongoTemplate.findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any()))
                .thenReturn("And Replace");
        assertThrows(DmsException.class,
                () -> customMongoServiceImpl.findAndReplaceDocument("42", new HashMap<>()));
        verify(mongoTemplate).findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#findAndReplaceDocument(String, Map)}
     */
    @Test
    void testFindAndReplaceDocument2() throws DmsException {
        when(mongoTemplate.findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());
        assertTrue(customMongoServiceImpl.findAndReplaceDocument("42", new HashMap<>()).isEmpty());
        verify(mongoTemplate).findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#findAndReplaceDocument(String, Map)}
     */
    @Test
    void testFindAndReplaceDocument3() throws DmsException {
        when(mongoTemplate.findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any()))
                .thenReturn(null);
        assertThrows(DmsException.class,
                () -> customMongoServiceImpl.findAndReplaceDocument("42", new HashMap<>()));
        verify(mongoTemplate).findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#findAndReplaceDocument(String, Map)}
     */
    @Test
    void testFindAndReplaceDocument4() throws DmsException {
        when(mongoTemplate.findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("Error occurred in mongo get document by id operation"));
        assertThrows(DmsException.class,
                () -> customMongoServiceImpl.findAndReplaceDocument("42", new HashMap<>()));
        verify(mongoTemplate).findAndReplace(Mockito.<Query>any(), Mockito.<Object>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#updateDocument(Map, Map)}
     */
    @Test
    void testUpdateDocument() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(), Mockito.<String>any()))
                .thenReturn(null);
        HashMap<String, Object> condition = new HashMap<>();
        customMongoServiceImpl.updateDocument(condition, new HashMap<>());
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#updateDocument(Map, Map)}
     */
    @Test
    void testUpdateDocument2() {
        when(mongoTemplate.updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("foo"));
        HashMap<String, Object> condition = new HashMap<>();
        assertThrows(IllegalStateException.class,
                () -> customMongoServiceImpl.updateDocument(condition, new HashMap<>()));
        verify(mongoTemplate).updateFirst(Mockito.<Query>any(), Mockito.<UpdateDefinition>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#doesDocumentExist(String)}
     */
    @Test
    void testDoesDocumentExist() {
        when(mongoTemplate.count(Mockito.<Query>any(), Mockito.<String>any())).thenReturn(3L);
        assertTrue(customMongoServiceImpl.doesDocumentExist("42"));
        verify(mongoTemplate).count(Mockito.<Query>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#doesDocumentExist(String)}
     */
    @Test
    void testDoesDocumentExist2() {
        when(mongoTemplate.count(Mockito.<Query>any(), Mockito.<String>any())).thenReturn(0L);
        assertFalse(customMongoServiceImpl.doesDocumentExist("42"));
        verify(mongoTemplate).count(Mockito.<Query>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#doesDocumentExist(String)}
     */
    @Test
    void testDoesDocumentExist3() {
        when(mongoTemplate.count(Mockito.<Query>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> customMongoServiceImpl.doesDocumentExist("42"));
        verify(mongoTemplate).count(Mockito.<Query>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.CREATE);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail2() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.UPDATE);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail3() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.GET);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail4() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.DELETE);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail5() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.RESTORE);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail6() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any()))
                .thenThrow(new IllegalStateException("UTC"));
        assertThrows(IllegalStateException.class,
                () -> customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.CREATE));
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#saveAuditTrail(String, HeaderContext, EventTypes)}
     */
    @Test
    void testSaveAuditTrail7() {
        when(auditTrailMongoRepository.save(Mockito.<AuditTrailCollection>any())).thenReturn(new AuditTrailCollection());
        customMongoServiceImpl.saveAuditTrail("42", headerContext, EventTypes.VERIFICATION);
        verify(auditTrailMongoRepository).save(Mockito.<AuditTrailCollection>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl
                .getDocumentDetailsByUserFilers(new HashMap<>(), "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers2() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put(" ", "42");
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers3() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put(" ", "42");
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers4() {
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenThrow(new IllegalStateException(" "));
        assertThrows(IllegalStateException.class,
                () -> customMongoServiceImpl.getDocumentDetailsByUserFilers(new HashMap<>(), "Hospital"));
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers5() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Tenant", "42");
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers6() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Status", "42");
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers7() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put("Hospital", "42");
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomMongoServiceImpl#getDocumentDetailsByUserFilers(Map, String)}
     */
    @Test
    void testGetDocumentDetailsByUserFilers8() {
        ArrayList<Map<Object, Object>> mapList = new ArrayList<>();
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(), Mockito.<String>any()))
                .thenReturn(mapList);

        HashMap<String, Object> requestMap = new HashMap<>();
        requestMap.put("ClusterMRN", "42");
        requestMap.put(" ", new ArrayList<>());
        List<Map> actualDocumentDetailsByUserFilers = customMongoServiceImpl.getDocumentDetailsByUserFilers(requestMap,
                "Hospital");
        assertSame(mapList, actualDocumentDetailsByUserFilers);
        assertTrue(actualDocumentDetailsByUserFilers.isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<Map<Object, Object>>>any(),
                Mockito.<String>any());
    }
}

