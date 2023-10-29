package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.*;
import com.api.documentmanagementservice.commons.mapper.CustomObjectMapper;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.api.documentmanagementservice.model.dto.UploadFileDetails;
import com.api.documentmanagementservice.model.dto.ZippedFileDetailsDto;
import com.api.documentmanagementservice.model.table.DirectoryDetails;
import com.api.documentmanagementservice.model.table.DocumentDetails;
import com.api.documentmanagementservice.model.table.FileDetails;
import com.api.documentmanagementservice.repository.DocumentDetailsRepository;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UploadServiceImpl.class,
        HeaderContext.class})
@ExtendWith(SpringExtension.class)
class UploadServiceImplTest {
    @MockBean
    private BucketService bucketService;

    @MockBean
    private CustomMongoService customMongoService;

    @MockBean
    private CustomObjectMapper customObjectMapper;

    @MockBean
    private DirectoryDetailsService directoryDetailsService;

    @MockBean
    private DocumentDetailsRepository documentDetailsRepository;

    @MockBean
    private DocumentDetailsService documentDetailsService;

    @MockBean
    private DocumentStorageService documentStorageService;

    @MockBean
    private FileDetailsRepository fileDetailsRepository;

    @MockBean
    private FileOperationsService fileOperationsService;

    @MockBean
    private HeaderContext headerContext;

    @Autowired
    private UploadServiceImpl uploadServiceImpl;


    /**
     * Method under test: {@link UploadServiceImpl#uploadFile(HttpServletRequest, String, String, String)}
     */
    @Test
    void testUploadFile2() throws BadRequestException, DmsException, JsonProcessingException {
        when(fileOperationsService.uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"));
        when(bucketService.createBucketName(Mockito.<String>any(), Mockito.<String>any())).thenReturn("bucket-name");

        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(new FileDetails());
        when(documentDetailsService.createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new DocumentDetails());
        when(customMongoService.save(Mockito.<Map<String, Object>>any()))
                .thenThrow(new DmsException("An error occurred", "An error occurred"));
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(customObjectMapper.getFileDataDtoFromJsonStringArray(Mockito.<String>any()))
                .thenReturn(new FileDataDto("Name", "Key", 1));
        when(customObjectMapper.getMapFromJsonString(Mockito.<String>any())).thenReturn(new HashMap<>());
        assertThrows(DmsException.class, () -> uploadServiceImpl.uploadFile(new MockHttpServletRequest(),
                "Document Data Json", "File Data Json", "Base64 File"));
        verify(fileOperationsService).uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(bucketService).createBucketName(Mockito.<String>any(), Mockito.<String>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(documentDetailsService).createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any());
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(customObjectMapper).getFileDataDtoFromJsonStringArray(Mockito.<String>any());
        verify(customObjectMapper).getMapFromJsonString(Mockito.<String>any());
    }


    /**
     * Method under test: {@link UploadServiceImpl#uploadFile(HttpServletRequest, String, String, String)}
     */
    @Test
    void testUploadFile8() throws BadRequestException, DmsException, JsonProcessingException {
        when(fileOperationsService.uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"));
        when(bucketService.createBucketName(Mockito.<String>any(), Mockito.<String>any())).thenReturn("bucket-name");

        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(FileDetails.builder()
                .documentId("42")
                .extension("Extension")
                .fileName("foo.txt")
                .id("42")
                .seq(1L)
                .status(true)
                .build());
        when(documentDetailsService.createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new DocumentDetails());
        Document document = new Document();
        when(customMongoService.save(Mockito.<Map<String, Object>>any())).thenReturn(document);
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(customObjectMapper.getFileDataDtoFromJsonStringArray(Mockito.<String>any()))
                .thenReturn(new FileDataDto("Name", "Key", 1));
        when(customObjectMapper.getMapFromJsonString(Mockito.<String>any())).thenReturn(new HashMap<>());
        ResponseEntity<CommonResponse> actualUploadFileResult = uploadServiceImpl.uploadFile(new MockHttpServletRequest(),
                "Document Data Json", "File Data Json", "Base64 File");
        assertTrue(actualUploadFileResult.hasBody());
        assertTrue(actualUploadFileResult.getHeaders().isEmpty());
        assertEquals(201, actualUploadFileResult.getStatusCodeValue());
        CommonResponse body = actualUploadFileResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertSame(document, resultDataResult);
        assertEquals(1, ((Document) resultDataResult).size());
        verify(fileOperationsService).uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(bucketService).createBucketName(Mockito.<String>any(), Mockito.<String>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(documentDetailsService).createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any());
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(customObjectMapper).getFileDataDtoFromJsonStringArray(Mockito.<String>any());
        verify(customObjectMapper).getMapFromJsonString(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#uploadFile(HttpServletRequest, String, String, String)}
     */
    @Test
    void testUploadFile10() throws BadRequestException, DmsException, JsonProcessingException {
        when(fileOperationsService.uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"));
        when(bucketService.createBucketName(Mockito.<String>any(), Mockito.<String>any())).thenReturn("bucket-name");

        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        FileDetails fileDetails = mock(FileDetails.class);
        when(fileDetails.getId()).thenReturn("42");
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        when(documentDetailsService.createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new DocumentDetails());
        Document document = new Document();
        when(customMongoService.save(Mockito.<Map<String, Object>>any())).thenReturn(document);
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(customObjectMapper.getFileDataDtoFromJsonStringArray(Mockito.<String>any()))
                .thenReturn(new FileDataDto("Name", "Key", 1));
        when(customObjectMapper.getMapFromJsonString(Mockito.<String>any())).thenReturn(new HashMap<>());
        ResponseEntity<CommonResponse> actualUploadFileResult = uploadServiceImpl.uploadFile(new MockHttpServletRequest(),
                "Document Data Json", "File Data Json", "Base64 File");
        assertTrue(actualUploadFileResult.hasBody());
        assertTrue(actualUploadFileResult.getHeaders().isEmpty());
        assertEquals(201, actualUploadFileResult.getStatusCodeValue());
        CommonResponse body = actualUploadFileResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertSame(document, resultDataResult);
        assertEquals(1, ((Document) resultDataResult).size());
        verify(fileOperationsService).uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(bucketService).createBucketName(Mockito.<String>any(), Mockito.<String>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(fileDetails).getId();
        verify(documentDetailsService).createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any());
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(customObjectMapper).getFileDataDtoFromJsonStringArray(Mockito.<String>any());
        verify(customObjectMapper).getMapFromJsonString(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#uploadFile(HttpServletRequest, String, String, String)}
     */
    @Test
    void testUploadFile11() throws BadRequestException, DmsException, JsonProcessingException {
        when(fileOperationsService.uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", null));
        when(bucketService.createBucketName(Mockito.<String>any(), Mockito.<String>any())).thenReturn("bucket-name");

        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        FileDetails fileDetails = mock(FileDetails.class);
        when(fileDetails.getId()).thenReturn("42");
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        when(documentDetailsService.createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new DocumentDetails());
        Document document = new Document();
        when(customMongoService.save(Mockito.<Map<String, Object>>any())).thenReturn(document);
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(customObjectMapper.getFileDataDtoFromJsonStringArray(Mockito.<String>any()))
                .thenReturn(new FileDataDto("Name", "Key", 1));
        when(customObjectMapper.getMapFromJsonString(Mockito.<String>any())).thenReturn(new HashMap<>());
        ResponseEntity<CommonResponse> actualUploadFileResult = uploadServiceImpl.uploadFile(new MockHttpServletRequest(),
                "Document Data Json", "File Data Json", "Base64 File");
        assertTrue(actualUploadFileResult.hasBody());
        assertTrue(actualUploadFileResult.getHeaders().isEmpty());
        assertEquals(201, actualUploadFileResult.getStatusCodeValue());
        CommonResponse body = actualUploadFileResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertSame(document, resultDataResult);
        assertEquals(1, ((Document) resultDataResult).size());
        verify(fileOperationsService).uploadAndZipBase64ToTemporaryPath(Mockito.<String>any(), Mockito.<FileDataDto>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(bucketService).createBucketName(Mockito.<String>any(), Mockito.<String>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(fileDetails).getId();
        verify(documentDetailsService).createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any());
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(customObjectMapper).getFileDataDtoFromJsonStringArray(Mockito.<String>any());
        verify(customObjectMapper).getMapFromJsonString(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#getMongoDoc(String)}
     */
    @Test
    void testGetMongoDoc() throws DmsException {
        when(customMongoService.getDocumentById(Mockito.<String>any())).thenReturn(new Document());
        ResponseEntity<Map<String, Object>> actualMongoDoc = uploadServiceImpl.getMongoDoc("42");
        assertTrue(actualMongoDoc.hasBody());
        assertEquals(200, actualMongoDoc.getStatusCodeValue());
        assertTrue(actualMongoDoc.getHeaders().isEmpty());
        verify(customMongoService).getDocumentById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#getMongoDoc(String)}
     */
    @Test
    void testGetMongoDoc2() throws DmsException {
        when(customMongoService.getDocumentById(Mockito.<String>any()))
                .thenThrow(new DmsException("An error occurred", "An error occurred"));
        assertThrows(DmsException.class, () -> uploadServiceImpl.getMongoDoc("42"));
        verify(customMongoService).getDocumentById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#findAndReplaceDoc(String, String)}
     */
    @Test
    void testFindAndReplaceDoc() throws DmsException, JsonProcessingException {
        when(customMongoService.findAndReplaceDocument(Mockito.<String>any(), Mockito.<Map<String, Object>>any()))
                .thenReturn(new Document());
        when(customObjectMapper.getMapFromJsonString(Mockito.<String>any())).thenReturn(new HashMap<>());
        ResponseEntity<Map<String, Object>> actualFindAndReplaceDocResult = uploadServiceImpl.findAndReplaceDoc("42",
                "New Mongo Data");
        assertTrue(actualFindAndReplaceDocResult.hasBody());
        assertEquals(200, actualFindAndReplaceDocResult.getStatusCodeValue());
        assertTrue(actualFindAndReplaceDocResult.getHeaders().isEmpty());
        verify(customMongoService).findAndReplaceDocument(Mockito.<String>any(), Mockito.<Map<String, Object>>any());
        verify(customObjectMapper).getMapFromJsonString(Mockito.<String>any());
    }


    /**
     * Method under test: {@link UploadServiceImpl#generateFileDataResponse(FileDetails)}
     */
    @Test
    void testGenerateFileDataResponse2() {
        List<String> actualGenerateFileDataResponseResult = uploadServiceImpl
                .generateFileDataResponse(FileDetails.builder()
                        .documentId("42")
                        .extension("Extension")
                        .fileName("foo.txt")
                        .id("42")
                        .seq(1L)
                        .status(true)
                        .build());
        assertEquals(1, actualGenerateFileDataResponseResult.size());
        assertEquals("42", actualGenerateFileDataResponseResult.get(0));
    }


    /**
     * Method under test: {@link UploadServiceImpl#generateFileDataResponse(FileDetails)}
     */
    @Test
    void testGenerateFileDataResponse4() {
        FileDetails savedFileDetails = mock(FileDetails.class);
        when(savedFileDetails.getId()).thenReturn("42");
        List<String> actualGenerateFileDataResponseResult = uploadServiceImpl.generateFileDataResponse(savedFileDetails);
        assertEquals(1, actualGenerateFileDataResponseResult.size());
        assertEquals("42", actualGenerateFileDataResponseResult.get(0));
        verify(savedFileDetails).getId();
    }

    /**
     * Method under test: {@link UploadServiceImpl#createBucketIfNotExists(String)}
     */
    @Test
    void testCreateBucketIfNotExists() {
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(true);
        uploadServiceImpl.createBucketIfNotExists("bucket-name");
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#createBucketIfNotExists(String)}
     */
    @Test
    void testCreateBucketIfNotExists2() {
        when(documentStorageService.isBucketExist(Mockito.<String>any())).thenReturn(false);
        doNothing().when(documentStorageService).createBucket(Mockito.<String>any());
        uploadServiceImpl.createBucketIfNotExists("bucket-name");
        verify(documentStorageService).isBucketExist(Mockito.<String>any());
        verify(documentStorageService).createBucket(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#createDocumentStoragePath(Map)}
     */
    @Test
    void testCreateDocumentStoragePath() {
        assertEquals("/", uploadServiceImpl.createDocumentStoragePath(new HashMap<>()));
    }

    /**
     * Method under test: {@link UploadServiceImpl#createOrUpdateTableData(String, String, HeaderContext, UploadFileDetails, ZippedFileDetailsDto, Boolean)}
     */
    @Test
    void testCreateOrUpdateTableData() throws DmsException {
        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        when(documentDetailsRepository.updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        FileDetails fileDetails = new FileDetails();
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        doNothing().when(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
        UploadFileDetails file = new UploadFileDetails("Key", "foo.txt", "File Type", "Path", 1);

        assertSame(fileDetails,
                uploadServiceImpl.createOrUpdateTableData("Path", "s3://bucket-name/object-key", headerContext, file,
                        new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"), true));
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(documentDetailsRepository).updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#createOrUpdateTableData(String, String, HeaderContext, UploadFileDetails, ZippedFileDetailsDto, Boolean)}
     */
    @Test
    void testCreateOrUpdateTableData2() throws DmsException {
        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> emptyResult = Optional.empty();
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(emptyResult);
        when(directoryDetailsService.createDirectory(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(directoryDetails);
        Optional<Long> ofResult = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult);
        when(documentDetailsRepository.updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        FileDetails fileDetails = new FileDetails();
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        doNothing().when(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
        UploadFileDetails file = new UploadFileDetails("Key", "foo.txt", "File Type", "Path", 1);

        assertSame(fileDetails,
                uploadServiceImpl.createOrUpdateTableData("Path", "s3://bucket-name/object-key", headerContext, file,
                        new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"), true));
        verify(directoryDetailsService).createDirectory(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(documentDetailsRepository).updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#createOrUpdateTableData(String, String, HeaderContext, UploadFileDetails, ZippedFileDetailsDto, Boolean)}
     */
    @Test
    void testCreateOrUpdateTableData3() throws DmsException {
        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        when(directoryDetailsService.createDirectory(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(directoryDetails);
        Optional<Long> emptyResult = Optional.empty();
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(emptyResult);
        when(documentDetailsRepository.updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any())).thenReturn(1);
        FileDetails fileDetails = new FileDetails();
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        doNothing().when(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
        UploadFileDetails file = new UploadFileDetails("Key", "foo.txt", "File Type", "Path", 1);

        assertSame(fileDetails,
                uploadServiceImpl.createOrUpdateTableData("Path", "s3://bucket-name/object-key", headerContext, file,
                        new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"), true));
        verify(directoryDetailsService).createDirectory(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(documentDetailsRepository).updateDocumentDetailsById(Mockito.<String>any(), Mockito.<Long>any(),
                Mockito.<String>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(fileDetailsRepository).deleteFileDetailsByDocumentId(Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#createOrUpdateTableData(String, String, HeaderContext, UploadFileDetails, ZippedFileDetailsDto, Boolean)}
     */
    @Test
    void testCreateOrUpdateTableData6() throws DmsException {
        DirectoryDetails directoryDetails = new DirectoryDetails();
        directoryDetails.setBucket("s3://bucket-name/object-key");
        directoryDetails.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        directoryDetails.setId(1L);
        directoryDetails.setPath("Path");
        directoryDetails.setStatus(true);
        directoryDetails.setTenant("Tenant");
        Optional<DirectoryDetails> ofResult = Optional.of(directoryDetails);
        when(directoryDetailsService.getDirectoryById(Mockito.<Long>any())).thenReturn(ofResult);
        Optional<Long> ofResult2 = Optional.<Long>of(1L);
        when(directoryDetailsService.checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any())).thenReturn(ofResult2);
        FileDetails fileDetails = new FileDetails();
        when(fileDetailsRepository.save(Mockito.<FileDetails>any())).thenReturn(fileDetails);
        when(documentDetailsService.createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn(new DocumentDetails());
        UploadFileDetails file = new UploadFileDetails("Key", "foo.txt", "File Type", "Path", 1);

        assertSame(fileDetails,
                uploadServiceImpl.createOrUpdateTableData("Path", "s3://bucket-name/object-key", headerContext, file,
                        new ZippedFileDetailsDto("foo.txt", "File Hash", "42", "/directory/foo.txt", "text/plain"), false));
        verify(directoryDetailsService).checkDirectoryExist(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<HeaderContext>any());
        verify(directoryDetailsService).getDirectoryById(Mockito.<Long>any());
        verify(fileDetailsRepository).save(Mockito.<FileDetails>any());
        verify(documentDetailsService).createDocumentDetails(Mockito.<Long>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link UploadServiceImpl#modifyMongoData(Map, String)}
     */
    @Test
    void testModifyMongoData() {
        assertEquals(1, uploadServiceImpl.modifyMongoData(new HashMap<>(), "42").size());
    }

    /**
     * Method under test: {@link UploadServiceImpl#extractFileFormatFromMimeType(String)}
     */
    @Test
    void testExtractFileFormatFromMimeType() {
        assertEquals("Mime Type", uploadServiceImpl.extractFileFormatFromMimeType("Mime Type"));
    }

    /**
     * Method under test: {@link UploadServiceImpl#getUploadFileDetails(FileDataDto, String, String)}
     */
    @Test
    void testGetUploadFileDetails() {
        UploadFileDetails actualUploadFileDetails = uploadServiceImpl
                .getUploadFileDetails(new FileDataDto("Name", "Key", 1), "Minio Path", "text/plain");
        assertEquals("Name", actualUploadFileDetails.fileName());
        assertEquals("Minio Path", actualUploadFileDetails.path());
        assertEquals("Key", actualUploadFileDetails.key());
        assertEquals(1, actualUploadFileDetails.index());
        assertEquals("text/plain", actualUploadFileDetails.fileType());
    }

}

