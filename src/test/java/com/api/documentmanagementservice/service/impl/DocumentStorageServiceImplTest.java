package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DocumentStorageServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DocumentStorageServiceImplTest {
    @Autowired
    private DocumentStorageServiceImpl documentStorageServiceImpl;

    @MockBean
    private MinioServiceImpl minioServiceImpl;

    /**
     * Method under test: {@link DocumentStorageServiceImpl#downloadDocument(String, String)}
     */
    @Test
    void testDownloadDocument() throws UnsupportedEncodingException, DmsException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));
        when(minioServiceImpl.retrieveDocument(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(byteArrayInputStream);
        assertSame(byteArrayInputStream,
                documentStorageServiceImpl.downloadDocument("s3://bucket-name/object-key", "/directory/foo.txt"));
        verify(minioServiceImpl).retrieveDocument(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link DocumentStorageServiceImpl#uploadDocument(String, String, InputStream, File)}
     */
    @Test
    void testUploadDocument() throws DmsException, IOException {
        doNothing().when(minioServiceImpl)
                .uploadDocument(Mockito.<String>any(), Mockito.<String>any(), Mockito.<InputStream>any(),
                        Mockito.<File>any());
        ByteArrayInputStream documentStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));
        documentStorageServiceImpl.uploadDocument("s3://bucket-name/object-key", "/directory/foo.txt", documentStream,
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
        verify(minioServiceImpl).uploadDocument(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<InputStream>any(), Mockito.<File>any());
    }

    /**
     * Method under test: {@link DocumentStorageServiceImpl#uploadDocument(String, String, InputStream, File)}
     */
    @Test
    void testUploadDocument2() throws DmsException, IOException {
        doThrow(new DmsException("An error occurred", "An error occurred")).when(minioServiceImpl)
                .uploadDocument(Mockito.<String>any(), Mockito.<String>any(), Mockito.<InputStream>any(),
                        Mockito.<File>any());
        ByteArrayInputStream documentStream = new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"));
        assertThrows(DmsException.class,
                () -> documentStorageServiceImpl.uploadDocument("s3://bucket-name/object-key", "/directory/foo.txt",
                        documentStream, Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
        verify(minioServiceImpl).uploadDocument(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<InputStream>any(), Mockito.<File>any());
    }

    /**
     * Method under test: {@link DocumentStorageServiceImpl#isBucketExist(String)}
     */
    @Test
    void testIsBucketExist() {
        when(minioServiceImpl.isBucketExist(Mockito.<String>any())).thenReturn(true);
        assertTrue(documentStorageServiceImpl.isBucketExist("bucket-name"));
        verify(minioServiceImpl).isBucketExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DocumentStorageServiceImpl#isBucketExist(String)}
     */
    @Test
    void testIsBucketExist2() {
        when(minioServiceImpl.isBucketExist(Mockito.<String>any())).thenReturn(false);
        assertFalse(documentStorageServiceImpl.isBucketExist("bucket-name"));
        verify(minioServiceImpl).isBucketExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DocumentStorageServiceImpl#createBucket(String)}
     */
    @Test
    void testCreateBucket() {
        doNothing().when(minioServiceImpl).createBucket(Mockito.<String>any());
        documentStorageServiceImpl.createBucket("bucket-name");
        verify(minioServiceImpl).createBucket(Mockito.<String>any());
    }
}

