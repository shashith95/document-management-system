package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.service.UserPreferenceService;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.table.UserPreference;
import com.api.documentmanagementservice.repository.FolderHierarchyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DirectoryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DirectoryServiceImplTest {
    @MockBean
    private CustomMongoService customMongoService;

    @Autowired
    private DirectoryServiceImpl directoryServiceImpl;

    @MockBean
    private FolderHierarchyRepository folderHierarchyRepository;

    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private UserPreferenceService userPreferenceService;

    /**
     * Method under test: {@link DirectoryServiceImpl#getDirectory(Map)}
     */
    @Test
    void testGetDirectory2() throws BadRequestException {
        UserPreference userPreference = mock(UserPreference.class);
        when(userPreference.getPreference()).thenReturn(-1L);
        doNothing().when(userPreference).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference).setHospitalId(Mockito.<String>any());
        doNothing().when(userPreference).setId(Mockito.<Long>any());
        doNothing().when(userPreference).setPreference(Mockito.<Long>any());
        doNothing().when(userPreference).setPreferenceType(Mockito.<String>any());
        doNothing().when(userPreference).setTenantId(Mockito.<String>any());
        doNothing().when(userPreference).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference).setUser(Mockito.<String>any());
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        Optional<UserPreference> ofResult = Optional.of(userPreference);
        when(userPreferenceService.isUserPreferenceExist(Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        assertThrows(BadRequestException.class, () -> directoryServiceImpl.getDirectory(new HashMap<>()));
        verify(userPreferenceService).isUserPreferenceExist(Mockito.<String>any());
        verify(userPreference, atLeast(1)).getPreference();
        verify(userPreference).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference).setHospitalId(Mockito.<String>any());
        verify(userPreference).setId(Mockito.<Long>any());
        verify(userPreference).setPreference(Mockito.<Long>any());
        verify(userPreference).setPreferenceType(Mockito.<String>any());
        verify(userPreference).setTenantId(Mockito.<String>any());
        verify(userPreference).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference).setUser(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }
}

