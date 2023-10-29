package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.DocumentStorageService;
import com.api.documentmanagementservice.config.FileProperties;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.BucketDataDto;
import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileOperationServiceImpl.class, FileProperties.class,
        HeaderContext.class})
@ExtendWith(SpringExtension.class)
class FileOperationServiceImplTest {
    @Autowired
    private HeaderContext headerContext;

    @MockBean
    private DocumentStorageService documentStorageService;

    @MockBean
    private FileDetailsRepository fileDetailsRepository;

    @Autowired
    private FileOperationServiceImpl fileOperationServiceImpl;

    /**
     * Method under test: {@link FileOperationServiceImpl#getDocumentFromDocumentStorage(String, HeaderContext)}
     */
    @Test
    void testGetDocumentFromDocumentStorage() throws DmsException, IOException {
        Optional<BucketDataDto> ofResult = Optional.of(new BucketDataDto("bucket-name", "Path", "42", "bucket-name"));
        when(fileDetailsRepository.getBucketDataRecordById(Mockito.<String>any())).thenReturn(ofResult);
        when(documentStorageService.downloadDocument(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
        ResponseEntity<Resource> actualDocumentFromDocumentStorage = fileOperationServiceImpl
                .getDocumentFromDocumentStorage("File Hash", headerContext);
        assertTrue(actualDocumentFromDocumentStorage.hasBody());
        assertEquals(200, actualDocumentFromDocumentStorage.getStatusCodeValue());
        assertEquals(4, actualDocumentFromDocumentStorage.getHeaders().size());
        Resource body = actualDocumentFromDocumentStorage.getBody();
        assertEquals("Byte array resource [resource loaded from byte array]", body.getDescription());
        assertEquals(0, body.getContentAsByteArray().length);
        verify(fileDetailsRepository).getBucketDataRecordById(Mockito.<String>any());
        verify(documentStorageService).downloadDocument(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileOperationServiceImpl#getDocumentFromDocumentStorage(String, HeaderContext)}
     */
    @Test
    void testGetDocumentFromDocumentStorage2() throws DmsException, IOException {
        Optional<BucketDataDto> ofResult = Optional.of(new BucketDataDto("bucket-name", "Path", "42", "bucket-name"));
        when(fileDetailsRepository.getBucketDataRecordById(Mockito.<String>any())).thenReturn(ofResult);
        when(documentStorageService.downloadDocument(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new DmsException("An error occurred", "An error occurred"));
        assertThrows(DmsException.class,
                () -> fileOperationServiceImpl.getDocumentFromDocumentStorage("File Hash", headerContext));
        verify(fileDetailsRepository).getBucketDataRecordById(Mockito.<String>any());
        verify(documentStorageService).downloadDocument(Mockito.<String>any(), Mockito.<String>any());
    }


    /**
     * Method under test: {@link FileOperationServiceImpl#getDocumentFromDocumentStorage(String, HeaderContext)}
     */
    @Test
    void testGetDocumentFromDocumentStorage5() throws DmsException, IOException {
        Optional<BucketDataDto> emptyResult = Optional.empty();
        when(fileDetailsRepository.getBucketDataRecordById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(DmsException.class,
                () -> fileOperationServiceImpl.getDocumentFromDocumentStorage("File Hash", headerContext));
        verify(fileDetailsRepository).getBucketDataRecordById(Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileOperationServiceImpl#getDocumentFromDocumentStorage(String, HeaderContext)}
     */
    @Test
    void testGetDocumentFromDocumentStorage6() throws DmsException, IOException {
        Optional<BucketDataDto> ofResult = Optional.of(new BucketDataDto("bucket-name", "Path", "42", "bucket-name"));
        when(fileDetailsRepository.getBucketDataRecordById(Mockito.<String>any())).thenReturn(ofResult);
        when(documentStorageService.downloadDocument(Mockito.<String>any(), Mockito.<String>any())).thenReturn(null);
        assertThrows(DmsException.class,
                () -> fileOperationServiceImpl.getDocumentFromDocumentStorage("File Hash", headerContext));
        verify(fileDetailsRepository).getBucketDataRecordById(Mockito.<String>any());
        verify(documentStorageService).downloadDocument(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileOperationServiceImpl#getFileFromDocumentStorage(String, String)}
     */
    @Test
    void testGetFileFromDocumentStorage() throws DmsException, UnsupportedEncodingException {
        when(documentStorageService.downloadDocument(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
        Resource actualFileFromDocumentStorage = fileOperationServiceImpl
                .getFileFromDocumentStorage("s3://bucket-name/object-key", "/directory/foo.txt");
        assertEquals(0, ((ByteArrayResource) actualFileFromDocumentStorage).getByteArray().length);
        assertEquals("Byte array resource [resource loaded from byte array]",
                actualFileFromDocumentStorage.getDescription());
        verify(documentStorageService).downloadDocument(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileOperationServiceImpl#uploadAndZipFileToTemporaryPath(InputStream, FileDataDto, String, String, String, String, String)}
     */
    @Test
    void testUploadAndZipFileToTemporaryPath4() throws DmsException, IOException {
        doThrow(new DmsException("An error occurred", "An error occurred")).when(documentStorageService)
                .uploadDocument(Mockito.<String>any(), Mockito.<String>any(), Mockito.<InputStream>any(),
                        Mockito.<File>any());
        ByteArrayInputStream fileInputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));
        assertThrows(DmsException.class,
                () -> fileOperationServiceImpl.uploadAndZipFileToTemporaryPath(fileInputStream,
                        new FileDataDto("Name", "Key", 1), "File Upload Path", "text/plain", "42", "s3://bucket-name/object-key",
                        "Document Path"));
        verify(documentStorageService).uploadDocument(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<InputStream>any(), Mockito.<File>any());
    }


    /**
     * Method under test: {@link FileOperationServiceImpl#uploadAndZipToTemporaryPath(MultipartFile, FileDataDto, String, String, String)}
     */
    @Test
    void testUploadAndZipToTemporaryPath4() throws DmsException, IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenReturn(new FileInputStream(new FileDescriptor()));
        when(multipartFile.getContentType()).thenReturn("text/plain");
        assertThrows(DmsException.class, () -> fileOperationServiceImpl.uploadAndZipToTemporaryPath(multipartFile,
                new FileDataDto("Name", "Key", 1), "42", "s3://bucket-name/object-key", "Document Path"));
        verify(multipartFile).getInputStream();
        verify(multipartFile).getContentType();
    }


    /**
     * Method under test: {@link FileOperationServiceImpl#uploadAndZipToTemporaryPath(MultipartFile, FileDataDto, String, String, String)}
     */
    @Test
    void testUploadAndZipToTemporaryPath6() throws DmsException, IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenThrow(new IOException("foo"));
        assertThrows(DmsException.class, () -> fileOperationServiceImpl.uploadAndZipToTemporaryPath(multipartFile,
                new FileDataDto("Name", "Key", 1), "42", "s3://bucket-name/object-key", "Document Path"));
        verify(multipartFile).getInputStream();
    }


}

