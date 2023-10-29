package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.table.FolderHierarchy;
import com.api.documentmanagementservice.model.table.UploadSetting;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FolderHierarchyCreateResponse;
import com.api.documentmanagementservice.model.dto.request.FolderHierarchyRequest;
import com.api.documentmanagementservice.model.dto.request.SettingUploadRequest;
import com.api.documentmanagementservice.model.table.HierarchyResource;
import com.api.documentmanagementservice.repository.FilePropertyRepository;
import com.api.documentmanagementservice.repository.FolderHierarchyRepository;
import com.api.documentmanagementservice.repository.HierarchyResourceRepository;
import com.api.documentmanagementservice.repository.UploadSettingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileSettingServiceImpl.class,
        HeaderContext.class})
@ExtendWith(SpringExtension.class)
class FileSettingServiceImplTest {
    @MockBean
    private FilePropertyRepository filePropertyRepository;

    @Autowired
    private FileSettingServiceImpl fileSettingServiceImpl;

    @MockBean
    private FolderHierarchyRepository folderHierarchyRepository;

    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private HierarchyResourceRepository hierarchyResourceRepository;

    @MockBean
    private UploadSettingRepository uploadSettingRepository;

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(new FolderHierarchy());
        Optional<FolderHierarchy> ofResult2 = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult2);
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any())).thenReturn(new ArrayList<>());
        doNothing().when(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy2() throws BadRequestException, DmsException {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Validating folder hierarchy properties");
        Optional<List<String>> ofResult = Optional.of(stringList);
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(BadRequestException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy3() throws BadRequestException, DmsException {
        Optional<List<String>> emptyResult = Optional.empty();
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(BadRequestException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy5() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);
        Optional<FolderHierarchy> ofResult2 = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult2);
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any()))
                .thenReturn(new ArrayList<>());
        doNothing().when(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy).getId();
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy6() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.toBuilder()).thenReturn(FolderHierarchy.builder());
        Optional<FolderHierarchy> ofResult2 = Optional.of(folderHierarchy);
        FolderHierarchy folderHierarchy2 = mock(FolderHierarchy.class);
        when(folderHierarchy2.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy2);
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult2);
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any()))
                .thenReturn(new ArrayList<>());
        doNothing().when(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy2).getId();
        verify(folderHierarchy).toBuilder();
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy8() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        Optional<FolderHierarchy> emptyResult = Optional.empty();
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy10() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.toBuilder()).thenReturn(FolderHierarchy.builder());
        Optional<FolderHierarchy> ofResult2 = Optional.of(folderHierarchy);
        FolderHierarchy folderHierarchy2 = mock(FolderHierarchy.class);
        when(folderHierarchy2.getIsCustom()).thenReturn(true);
        when(folderHierarchy2.getHospitalId()).thenReturn("42");
        when(folderHierarchy2.getTenantId()).thenReturn("42");
        when(folderHierarchy2.getUser()).thenReturn("User");
        when(folderHierarchy2.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(folderHierarchy2.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(folderHierarchy2.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy2);
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult2);
        HierarchyResource hierarchyResource = mock(HierarchyResource.class);
        when(hierarchyResource.getId()).thenReturn(1L);

        ArrayList<HierarchyResource> hierarchyResourceList = new ArrayList<>();
        hierarchyResourceList.add(hierarchyResource);
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any()))
                .thenReturn(hierarchyResourceList);
        doNothing().when(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<Long> folderHierarchy3 = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateFolderHierarchyResult = fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, folderHierarchy3, true));
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.hasBody());
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateOrUpdateFolderHierarchyResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateFolderHierarchyResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertEquals(folderHierarchy3, body.errorList());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof FolderHierarchyCreateResponse);
        assertEquals("User", ((FolderHierarchyCreateResponse) resultDataResult).user());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((FolderHierarchyCreateResponse) resultDataResult).isCustom());
        assertEquals(1L, ((FolderHierarchyCreateResponse) resultDataResult).id().longValue());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).hospitalId());
        assertEquals(1, ((FolderHierarchyCreateResponse) resultDataResult).folderHierarchy().size());
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy2).getIsCustom();
        verify(folderHierarchy2, atLeast(1)).getId();
        verify(folderHierarchy2).getHospitalId();
        verify(folderHierarchy2).getTenantId();
        verify(folderHierarchy2).getUser();
        verify(folderHierarchy2).getCreatedDateTime();
        verify(folderHierarchy2).getUpdatedDateTime();
        verify(folderHierarchy).toBuilder();
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        verify(hierarchyResource).getId();
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy11() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);

        ArrayList<HierarchyResource> hierarchyResourceList = new ArrayList<>();
        hierarchyResourceList.add(mock(HierarchyResource.class));
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any()))
                .thenReturn(hierarchyResourceList);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.empty();
        ArrayList<Long> folderHierarchy2 = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateFolderHierarchyResult = fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, folderHierarchy2, true));
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.hasBody());
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateOrUpdateFolderHierarchyResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateFolderHierarchyResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(folderHierarchy2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : Optional.empty updated", body.resultData());
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchy).getId();
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateFolderHierarchy(FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateFolderHierarchy12() throws BadRequestException, DmsException {
        Optional<List<String>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(ofResult);
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getIsCustom()).thenReturn(true);
        when(folderHierarchy.getHospitalId()).thenReturn("42");
        when(folderHierarchy.getTenantId()).thenReturn("42");
        when(folderHierarchy.getUser()).thenReturn("User");
        when(folderHierarchy.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(folderHierarchy.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(folderHierarchy.getId()).thenReturn(1L);
        Optional<FolderHierarchy> ofResult2 = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);
        HierarchyResource hierarchyResource = mock(HierarchyResource.class);
        when(hierarchyResource.getId()).thenReturn(1L);

        ArrayList<HierarchyResource> hierarchyResourceList = new ArrayList<>();
        hierarchyResourceList.add(hierarchyResource);
        when(hierarchyResourceRepository.saveAll(Mockito.<Iterable<HierarchyResource>>any()))
                .thenReturn(hierarchyResourceList);
        doNothing().when(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<Long> folderHierarchy2 = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateFolderHierarchyResult = fileSettingServiceImpl
                .createOrUpdateFolderHierarchy(new FolderHierarchyRequest(id, folderHierarchy2, false));
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.hasBody());
        assertTrue(actualCreateOrUpdateFolderHierarchyResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateOrUpdateFolderHierarchyResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateFolderHierarchyResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertEquals(folderHierarchy2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof FolderHierarchyCreateResponse);
        assertEquals("User", ((FolderHierarchyCreateResponse) resultDataResult).user());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((FolderHierarchyCreateResponse) resultDataResult).isCustom());
        assertEquals(1L, ((FolderHierarchyCreateResponse) resultDataResult).id().longValue());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).hospitalId());
        assertEquals(1, ((FolderHierarchyCreateResponse) resultDataResult).folderHierarchy().size());
        verify(filePropertyRepository).findByIdListHospitalTenant(Mockito.<List<Long>>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy).getIsCustom();
        verify(folderHierarchy, atLeast(1)).getId();
        verify(folderHierarchy).getHospitalId();
        verify(folderHierarchy).getTenantId();
        verify(folderHierarchy).getUser();
        verify(folderHierarchy).getCreatedDateTime();
        verify(folderHierarchy).getUpdatedDateTime();
        verify(hierarchyResourceRepository).saveAll(Mockito.<Iterable<HierarchyResource>>any());
        verify(hierarchyResourceRepository).deleteAllByFolderHierarchyId(Mockito.<Long>any());
        verify(hierarchyResource).getId();
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#getFolderHierarchy(Boolean)}
     */
    @Test
    void testGetFolderHierarchy2() throws DmsException {
        ArrayList<FolderHierarchy> folderHierarchyList = new ArrayList<>();
        folderHierarchyList.add(new FolderHierarchy());
        Optional<List<FolderHierarchy>> ofResult = Optional.of(folderHierarchyList);
        when(folderHierarchyRepository.findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<HierarchyResource> hierarchyResourceList = new ArrayList<>();
        Optional<List<HierarchyResource>> ofResult2 = Optional.of(hierarchyResourceList);
        when(hierarchyResourceRepository.findAllByFolderHierarchyIdOrderByIndex(Mockito.<Long>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualFolderHierarchy = fileSettingServiceImpl.getFolderHierarchy(true);
        assertTrue(actualFolderHierarchy.hasBody());
        assertTrue(actualFolderHierarchy.getHeaders().isEmpty());
        assertEquals(200, actualFolderHierarchy.getStatusCodeValue());
        CommonResponse body = actualFolderHierarchy.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(hierarchyResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof FolderHierarchyCreateResponse);
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).createdDateTime());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).user());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).updatedDateTime());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).tenantId());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).isCustom());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).id());
        assertNull(((FolderHierarchyCreateResponse) resultDataResult).hospitalId());
        assertTrue(((FolderHierarchyCreateResponse) resultDataResult).folderHierarchy().isEmpty());
        verify(folderHierarchyRepository).findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(hierarchyResourceRepository).findAllByFolderHierarchyIdOrderByIndex(Mockito.<Long>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#getFolderHierarchy(Boolean)}
     */
    @Test
    void testGetFolderHierarchy4() throws DmsException {
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getIsCustom()).thenReturn(true);
        when(folderHierarchy.getId()).thenReturn(1L);
        when(folderHierarchy.getHospitalId()).thenReturn("42");
        when(folderHierarchy.getTenantId()).thenReturn("42");
        when(folderHierarchy.getUser()).thenReturn("User");
        when(folderHierarchy.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(folderHierarchy.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<FolderHierarchy> folderHierarchyList = new ArrayList<>();
        folderHierarchyList.add(folderHierarchy);
        Optional<List<FolderHierarchy>> ofResult = Optional.of(folderHierarchyList);
        when(folderHierarchyRepository.findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<HierarchyResource> hierarchyResourceList = new ArrayList<>();
        Optional<List<HierarchyResource>> ofResult2 = Optional.of(hierarchyResourceList);
        when(hierarchyResourceRepository.findAllByFolderHierarchyIdOrderByIndex(Mockito.<Long>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualFolderHierarchy = fileSettingServiceImpl.getFolderHierarchy(true);
        assertTrue(actualFolderHierarchy.hasBody());
        assertTrue(actualFolderHierarchy.getHeaders().isEmpty());
        assertEquals(200, actualFolderHierarchy.getStatusCodeValue());
        CommonResponse body = actualFolderHierarchy.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(hierarchyResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof FolderHierarchyCreateResponse);
        assertEquals("User", ((FolderHierarchyCreateResponse) resultDataResult).user());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((FolderHierarchyCreateResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((FolderHierarchyCreateResponse) resultDataResult).isCustom());
        assertEquals(1L, ((FolderHierarchyCreateResponse) resultDataResult).id().longValue());
        assertEquals("42", ((FolderHierarchyCreateResponse) resultDataResult).hospitalId());
        assertTrue(((FolderHierarchyCreateResponse) resultDataResult).folderHierarchy().isEmpty());
        verify(folderHierarchyRepository).findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(folderHierarchy).getIsCustom();
        verify(folderHierarchy, atLeast(1)).getId();
        verify(folderHierarchy).getHospitalId();
        verify(folderHierarchy).getTenantId();
        verify(folderHierarchy).getUser();
        verify(folderHierarchy).getCreatedDateTime();
        verify(folderHierarchy).getUpdatedDateTime();
        verify(hierarchyResourceRepository).findAllByFolderHierarchyIdOrderByIndex(Mockito.<Long>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#getFolderHierarchy(Boolean)}
     */
    @Test
    void testGetFolderHierarchy5() throws DmsException {
        Optional<List<FolderHierarchy>> emptyResult = Optional.empty();
        when(folderHierarchyRepository.findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        assertThrows(DmsException.class, () -> fileSettingServiceImpl.getFolderHierarchy(true));
        verify(folderHierarchyRepository).findByUserAndHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#getFolderHierarchy(Boolean)}
     */
    @Test
    void testGetFolderHierarchy8() throws DmsException {
        Optional<List<FolderHierarchy>> emptyResult = Optional.empty();
        when(folderHierarchyRepository.findByHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        assertThrows(DmsException.class, () -> fileSettingServiceImpl.getFolderHierarchy(false));
        verify(folderHierarchyRepository).findByHospitalIdAndTenantIdAndIsCustom(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateUploadSettings(SettingUploadRequest)}
     */
    @Test
    void testCreateOrUpdateUploadSettings2() {
        UploadSetting uploadSetting = new UploadSetting();
        uploadSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting.setFileNamingFormat("File Naming Format");
        uploadSetting.setFileSizeUnit(3);
        uploadSetting.setFileTypes("File Types");
        uploadSetting.setHospitalId("42");
        uploadSetting.setId(1L);
        uploadSetting.setIsSizeLimited(true);
        uploadSetting.setMaxFileSize(3);
        uploadSetting.setTenantId("42");
        uploadSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<UploadSetting> uploadSettingList = new ArrayList<>();
        uploadSettingList.add(uploadSetting);
        List<UploadSetting> ofResult = uploadSettingList;

        UploadSetting uploadSetting2 = new UploadSetting();
        uploadSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting2.setFileNamingFormat("File Naming Format");
        uploadSetting2.setFileSizeUnit(3);
        uploadSetting2.setFileTypes("File Types");
        uploadSetting2.setHospitalId("42");
        uploadSetting2.setId(1L);
        uploadSetting2.setIsSizeLimited(true);
        uploadSetting2.setMaxFileSize(3);
        uploadSetting2.setTenantId("42");
        uploadSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(uploadSettingRepository.save(Mockito.<UploadSetting>any())).thenReturn(uploadSetting2);
        when(uploadSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateUploadSettingsResult = fileSettingServiceImpl
                .createOrUpdateUploadSettings(
                        new SettingUploadRequest(id, 10.0f, 3, true, "File Types", "File Naming Format"));
        assertTrue(actualCreateOrUpdateUploadSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateUploadSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateUploadSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateUploadSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(uploadSettingRepository).save(Mockito.<UploadSetting>any());
        verify(uploadSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateUploadSettings(SettingUploadRequest)}
     */
    @Test
    void testCreateOrUpdateUploadSettings3() {
        UploadSetting uploadSetting = new UploadSetting();
        uploadSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting.setFileNamingFormat("File Naming Format");
        uploadSetting.setFileSizeUnit(3);
        uploadSetting.setFileTypes("File Types");
        uploadSetting.setHospitalId("42");
        uploadSetting.setId(1L);
        uploadSetting.setIsSizeLimited(true);
        uploadSetting.setMaxFileSize(3);
        uploadSetting.setTenantId("42");
        uploadSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<UploadSetting> uploadSettingList = new ArrayList<>();
        uploadSettingList.add(uploadSetting);
        List<UploadSetting> ofResult = uploadSettingList;
        UploadSetting uploadSetting2 = mock(UploadSetting.class);
        when(uploadSetting2.getId()).thenReturn(1L);
        doNothing().when(uploadSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(uploadSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(uploadSetting2).setFileNamingFormat(Mockito.<String>any());
        doNothing().when(uploadSetting2).setFileSizeUnit(Mockito.<Integer>any());
        doNothing().when(uploadSetting2).setFileTypes(Mockito.<String>any());
        doNothing().when(uploadSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(uploadSetting2).setId(Mockito.<Long>any());
        doNothing().when(uploadSetting2).setIsSizeLimited(Mockito.<Boolean>any());
        doNothing().when(uploadSetting2).setMaxFileSize(Mockito.<Integer>any());
        doNothing().when(uploadSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(uploadSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        uploadSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting2.setFileNamingFormat("File Naming Format");
        uploadSetting2.setFileSizeUnit(3);
        uploadSetting2.setFileTypes("File Types");
        uploadSetting2.setHospitalId("42");
        uploadSetting2.setId(1L);
        uploadSetting2.setIsSizeLimited(true);
        uploadSetting2.setMaxFileSize(3);
        uploadSetting2.setTenantId("42");
        uploadSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(uploadSettingRepository.save(Mockito.<UploadSetting>any())).thenReturn(uploadSetting2);
        when(uploadSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateUploadSettingsResult = fileSettingServiceImpl
                .createOrUpdateUploadSettings(
                        new SettingUploadRequest(id, 10.0f, 3, true, "File Types", "File Naming Format"));
        assertTrue(actualCreateOrUpdateUploadSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateUploadSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateUploadSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateUploadSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(uploadSettingRepository).save(Mockito.<UploadSetting>any());
        verify(uploadSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(uploadSetting2, atLeast(1)).getId();
        verify(uploadSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(uploadSetting2).setCreatedUser(Mockito.<String>any());
        verify(uploadSetting2).setFileNamingFormat(Mockito.<String>any());
        verify(uploadSetting2).setFileSizeUnit(Mockito.<Integer>any());
        verify(uploadSetting2).setFileTypes(Mockito.<String>any());
        verify(uploadSetting2).setHospitalId(Mockito.<String>any());
        verify(uploadSetting2).setId(Mockito.<Long>any());
        verify(uploadSetting2).setIsSizeLimited(Mockito.<Boolean>any());
        verify(uploadSetting2).setMaxFileSize(Mockito.<Integer>any());
        verify(uploadSetting2).setTenantId(Mockito.<String>any());
        verify(uploadSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateUploadSettings(SettingUploadRequest)}
     */
    @Test
    void testCreateOrUpdateUploadSettings4() {
        UploadSetting uploadSetting = mock(UploadSetting.class);
        doNothing().when(uploadSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(uploadSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(uploadSetting).setFileNamingFormat(Mockito.<String>any());
        doNothing().when(uploadSetting).setFileSizeUnit(Mockito.<Integer>any());
        doNothing().when(uploadSetting).setFileTypes(Mockito.<String>any());
        doNothing().when(uploadSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(uploadSetting).setId(Mockito.<Long>any());
        doNothing().when(uploadSetting).setIsSizeLimited(Mockito.<Boolean>any());
        doNothing().when(uploadSetting).setMaxFileSize(Mockito.<Integer>any());
        doNothing().when(uploadSetting).setTenantId(Mockito.<String>any());
        doNothing().when(uploadSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        uploadSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting.setFileNamingFormat("File Naming Format");
        uploadSetting.setFileSizeUnit(3);
        uploadSetting.setFileTypes("File Types");
        uploadSetting.setHospitalId("42");
        uploadSetting.setId(1L);
        uploadSetting.setIsSizeLimited(true);
        uploadSetting.setMaxFileSize(3);
        uploadSetting.setTenantId("42");
        uploadSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<UploadSetting> uploadSettingList = new ArrayList<>();
        uploadSettingList.add(uploadSetting);
        List<UploadSetting> ofResult = uploadSettingList;
        UploadSetting uploadSetting2 = mock(UploadSetting.class);
        when(uploadSetting2.getId()).thenReturn(1L);
        doNothing().when(uploadSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(uploadSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(uploadSetting2).setFileNamingFormat(Mockito.<String>any());
        doNothing().when(uploadSetting2).setFileSizeUnit(Mockito.<Integer>any());
        doNothing().when(uploadSetting2).setFileTypes(Mockito.<String>any());
        doNothing().when(uploadSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(uploadSetting2).setId(Mockito.<Long>any());
        doNothing().when(uploadSetting2).setIsSizeLimited(Mockito.<Boolean>any());
        doNothing().when(uploadSetting2).setMaxFileSize(Mockito.<Integer>any());
        doNothing().when(uploadSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(uploadSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        uploadSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting2.setFileNamingFormat("File Naming Format");
        uploadSetting2.setFileSizeUnit(3);
        uploadSetting2.setFileTypes("File Types");
        uploadSetting2.setHospitalId("42");
        uploadSetting2.setId(1L);
        uploadSetting2.setIsSizeLimited(true);
        uploadSetting2.setMaxFileSize(3);
        uploadSetting2.setTenantId("42");
        uploadSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(uploadSettingRepository.save(Mockito.<UploadSetting>any())).thenReturn(uploadSetting2);
        when(uploadSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateUploadSettingsResult = fileSettingServiceImpl
                .createOrUpdateUploadSettings(
                        new SettingUploadRequest(id, 10.0f, 3, true, "File Types", "File Naming Format"));
        assertTrue(actualCreateOrUpdateUploadSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateUploadSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateUploadSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateUploadSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(uploadSettingRepository).save(Mockito.<UploadSetting>any());
        verify(uploadSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(uploadSetting2, atLeast(1)).getId();
        verify(uploadSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(uploadSetting2).setCreatedUser(Mockito.<String>any());
        verify(uploadSetting2).setFileNamingFormat(Mockito.<String>any());
        verify(uploadSetting2).setFileSizeUnit(Mockito.<Integer>any());
        verify(uploadSetting2).setFileTypes(Mockito.<String>any());
        verify(uploadSetting2).setHospitalId(Mockito.<String>any());
        verify(uploadSetting2).setId(Mockito.<Long>any());
        verify(uploadSetting2).setIsSizeLimited(Mockito.<Boolean>any());
        verify(uploadSetting2).setMaxFileSize(Mockito.<Integer>any());
        verify(uploadSetting2).setTenantId(Mockito.<String>any());
        verify(uploadSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(uploadSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(uploadSetting, atLeast(1)).setCreatedUser(Mockito.<String>any());
        verify(uploadSetting, atLeast(1)).setFileNamingFormat(Mockito.<String>any());
        verify(uploadSetting, atLeast(1)).setFileSizeUnit(Mockito.<Integer>any());
        verify(uploadSetting, atLeast(1)).setFileTypes(Mockito.<String>any());
        verify(uploadSetting, atLeast(1)).setHospitalId(Mockito.<String>any());
        verify(uploadSetting).setId(Mockito.<Long>any());
        verify(uploadSetting, atLeast(1)).setIsSizeLimited(Mockito.<Boolean>any());
        verify(uploadSetting, atLeast(1)).setMaxFileSize(Mockito.<Integer>any());
        verify(uploadSetting, atLeast(1)).setTenantId(Mockito.<String>any());
        verify(uploadSetting, atLeast(1)).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#getUploadSettings()}
     */
    @Test
    void testGetUploadSettings4() {
        List<UploadSetting> emptyResult = new ArrayList<>();
        when(uploadSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualUploadSettings = fileSettingServiceImpl.getUploadSettings();
        assertTrue(actualUploadSettings.hasBody());
        assertTrue(actualUploadSettings.getHeaders().isEmpty());
        assertEquals(204, actualUploadSettings.getStatusCodeValue());
        CommonResponse body = actualUploadSettings.getBody();
        assertEquals("DM_DMS_029", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("File uploading settings not found", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(uploadSettingRepository, atLeast(1)).findAllByHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable() throws DmsException {
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(new FolderHierarchy());
        Optional<FolderHierarchy> ofResult = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        Optional<Long> id = Optional.<Long>of(1L);
        assertTrue(fileSettingServiceImpl
                .createOrUpdateHierarchyTable(headerContext, new FolderHierarchyRequest(id, new ArrayList<>(), true))
                .isPresent());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable3() throws DmsException {
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);
        Optional<FolderHierarchy> ofResult = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        Optional<Long> id = Optional.<Long>of(1L);
        assertTrue(fileSettingServiceImpl
                .createOrUpdateHierarchyTable(headerContext, new FolderHierarchyRequest(id, new ArrayList<>(), true))
                .isPresent());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy).getId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable4() throws DmsException {
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.toBuilder()).thenReturn(FolderHierarchy.builder());
        Optional<FolderHierarchy> ofResult = Optional.of(folderHierarchy);
        FolderHierarchy folderHierarchy2 = mock(FolderHierarchy.class);
        when(folderHierarchy2.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy2);
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        Optional<Long> id = Optional.<Long>of(1L);
        assertTrue(fileSettingServiceImpl
                .createOrUpdateHierarchyTable(headerContext, new FolderHierarchyRequest(id, new ArrayList<>(), true))
                .isPresent());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy2).getId();
        verify(folderHierarchy).toBuilder();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable6() throws DmsException {
        Optional<FolderHierarchy> emptyResult = Optional.empty();
        when(folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(emptyResult);
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> fileSettingServiceImpl.createOrUpdateHierarchyTable(headerContext,
                new FolderHierarchyRequest(id, new ArrayList<>(), true)));
        verify(folderHierarchyRepository).findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable7() throws DmsException {
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getId()).thenReturn(1L);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);
        Optional<Long> id = Optional.empty();
        assertTrue(fileSettingServiceImpl
                .createOrUpdateHierarchyTable(headerContext, new FolderHierarchyRequest(id, new ArrayList<>(), true))
                .isPresent());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchy).getId();
    }

    /**
     * Method under test: {@link FileSettingServiceImpl#createOrUpdateHierarchyTable(HeaderContext, FolderHierarchyRequest)}
     */
    @Test
    void testCreateOrUpdateHierarchyTable8() throws DmsException {
        FolderHierarchy folderHierarchy = mock(FolderHierarchy.class);
        when(folderHierarchy.getId()).thenReturn(1L);
        Optional<FolderHierarchy> ofResult = Optional.of(new FolderHierarchy());
        when(folderHierarchyRepository.findByIdAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(folderHierarchyRepository.save(Mockito.<FolderHierarchy>any())).thenReturn(folderHierarchy);
        Optional<Long> id = Optional.<Long>of(1L);
        assertTrue(fileSettingServiceImpl
                .createOrUpdateHierarchyTable(headerContext, new FolderHierarchyRequest(id, new ArrayList<>(), false))
                .isPresent());
        verify(folderHierarchyRepository).save(Mockito.<FolderHierarchy>any());
        verify(folderHierarchyRepository).findByIdAndIsCustomAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Boolean>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(folderHierarchy).getId();
    }
}

