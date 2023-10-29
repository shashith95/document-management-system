package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceRequest;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceUpdateRequest;
import com.api.documentmanagementservice.model.table.UserPreference;
import com.api.documentmanagementservice.repository.UserPreferenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserPreferenceImpl.class})
@ExtendWith(SpringExtension.class)
class UserPreferenceImplTest {
    @MockBean
    private HeaderContext headerContext;

    @Autowired
    private UserPreferenceImpl userPreferenceImpl;

    @MockBean
    private UserPreferenceRepository userPreferenceRepository;

    /**
     * Method under test: {@link UserPreferenceImpl#createUserPreference(UserPreferenceRequest)}
     */
    @Test
    void testCreateUserPreference() {
        UserPreference userPreference = new UserPreference();
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        Optional<UserPreference> ofResult = Optional.of(userPreference);
        when(userPreferenceRepository.findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        assertThrows(ResponseStatusException.class,
                () -> userPreferenceImpl.createUserPreference(new UserPreferenceRequest("Preference Type", 1L)));
        verify(userPreferenceRepository).findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#createUserPreference(UserPreferenceRequest)}
     */
    @Test
    void testCreateUserPreference2() {
        UserPreference userPreference = new UserPreference();
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        when(userPreferenceRepository.save(Mockito.<UserPreference>any())).thenReturn(userPreference);
        Optional<UserPreference> emptyResult = Optional.empty();
        when(userPreferenceRepository.findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualCreateUserPreferenceResult = userPreferenceImpl
                .createUserPreference(new UserPreferenceRequest("Preference Type", 1L));
        assertTrue(actualCreateUserPreferenceResult.hasBody());
        assertTrue(actualCreateUserPreferenceResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateUserPreferenceResult.getStatusCodeValue());
        CommonResponse body = actualCreateUserPreferenceResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        assertSame(userPreference, body.resultData());
        verify(userPreferenceRepository).save(Mockito.<UserPreference>any());
        verify(userPreferenceRepository).findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#updateUserPreference(UserPreferenceUpdateRequest)}
     */
    @Test
    void testUpdateUserPreference() throws DmsException {
        UserPreference userPreference = new UserPreference();
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        Optional<UserPreference> ofResult = Optional.of(userPreference);

        UserPreference userPreference2 = new UserPreference();
        userPreference2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setHospitalId("42");
        userPreference2.setId(1L);
        userPreference2.setPreference(1L);
        userPreference2.setPreferenceType("Preference Type");
        userPreference2.setTenantId("42");
        userPreference2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setUser("User");
        when(userPreferenceRepository.save(Mockito.<UserPreference>any())).thenReturn(userPreference2);
        when(userPreferenceRepository.findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualUpdateUserPreferenceResult = userPreferenceImpl
                .updateUserPreference(new UserPreferenceUpdateRequest(1L, "Preference Type", 1L));
        assertTrue(actualUpdateUserPreferenceResult.hasBody());
        assertTrue(actualUpdateUserPreferenceResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserPreferenceResult.getStatusCodeValue());
        CommonResponse body = actualUpdateUserPreferenceResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(userPreferenceRepository).save(Mockito.<UserPreference>any());
        verify(userPreferenceRepository).findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#updateUserPreference(UserPreferenceUpdateRequest)}
     */
    @Test
    void testUpdateUserPreference2() throws DmsException {
        UserPreference userPreference = new UserPreference();
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        Optional<UserPreference> ofResult = Optional.of(userPreference);
        UserPreference userPreference2 = mock(UserPreference.class);
        when(userPreference2.getId()).thenReturn(1L);
        doNothing().when(userPreference2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference2).setHospitalId(Mockito.<String>any());
        doNothing().when(userPreference2).setId(Mockito.<Long>any());
        doNothing().when(userPreference2).setPreference(Mockito.<Long>any());
        doNothing().when(userPreference2).setPreferenceType(Mockito.<String>any());
        doNothing().when(userPreference2).setTenantId(Mockito.<String>any());
        doNothing().when(userPreference2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference2).setUser(Mockito.<String>any());
        userPreference2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setHospitalId("42");
        userPreference2.setId(1L);
        userPreference2.setPreference(1L);
        userPreference2.setPreferenceType("Preference Type");
        userPreference2.setTenantId("42");
        userPreference2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setUser("User");
        when(userPreferenceRepository.save(Mockito.<UserPreference>any())).thenReturn(userPreference2);
        when(userPreferenceRepository.findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualUpdateUserPreferenceResult = userPreferenceImpl
                .updateUserPreference(new UserPreferenceUpdateRequest(1L, "Preference Type", 1L));
        assertTrue(actualUpdateUserPreferenceResult.hasBody());
        assertTrue(actualUpdateUserPreferenceResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserPreferenceResult.getStatusCodeValue());
        CommonResponse body = actualUpdateUserPreferenceResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(userPreferenceRepository).save(Mockito.<UserPreference>any());
        verify(userPreferenceRepository).findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(userPreference2).getId();
        verify(userPreference2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference2).setHospitalId(Mockito.<String>any());
        verify(userPreference2).setId(Mockito.<Long>any());
        verify(userPreference2).setPreference(Mockito.<Long>any());
        verify(userPreference2).setPreferenceType(Mockito.<String>any());
        verify(userPreference2).setTenantId(Mockito.<String>any());
        verify(userPreference2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference2).setUser(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#updateUserPreference(UserPreferenceUpdateRequest)}
     */
    @Test
    void testUpdateUserPreference3() throws DmsException {
        UserPreference userPreference = mock(UserPreference.class);
        when(userPreference.toBuilder()).thenReturn(UserPreference.builder());
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
        UserPreference userPreference2 = mock(UserPreference.class);
        when(userPreference2.getId()).thenReturn(1L);
        doNothing().when(userPreference2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference2).setHospitalId(Mockito.<String>any());
        doNothing().when(userPreference2).setId(Mockito.<Long>any());
        doNothing().when(userPreference2).setPreference(Mockito.<Long>any());
        doNothing().when(userPreference2).setPreferenceType(Mockito.<String>any());
        doNothing().when(userPreference2).setTenantId(Mockito.<String>any());
        doNothing().when(userPreference2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(userPreference2).setUser(Mockito.<String>any());
        userPreference2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setHospitalId("42");
        userPreference2.setId(1L);
        userPreference2.setPreference(1L);
        userPreference2.setPreferenceType("Preference Type");
        userPreference2.setTenantId("42");
        userPreference2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference2.setUser("User");
        when(userPreferenceRepository.save(Mockito.<UserPreference>any())).thenReturn(userPreference2);
        when(userPreferenceRepository.findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        ResponseEntity<CommonResponse> actualUpdateUserPreferenceResult = userPreferenceImpl
                .updateUserPreference(new UserPreferenceUpdateRequest(1L, "Preference Type", 1L));
        assertTrue(actualUpdateUserPreferenceResult.hasBody());
        assertTrue(actualUpdateUserPreferenceResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserPreferenceResult.getStatusCodeValue());
        CommonResponse body = actualUpdateUserPreferenceResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(userPreferenceRepository).save(Mockito.<UserPreference>any());
        verify(userPreferenceRepository).findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(userPreference2).getId();
        verify(userPreference2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference2).setHospitalId(Mockito.<String>any());
        verify(userPreference2).setId(Mockito.<Long>any());
        verify(userPreference2).setPreference(Mockito.<Long>any());
        verify(userPreference2).setPreferenceType(Mockito.<String>any());
        verify(userPreference2).setTenantId(Mockito.<String>any());
        verify(userPreference2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(userPreference2).setUser(Mockito.<String>any());
        verify(userPreference).toBuilder();
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
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#updateUserPreference(UserPreferenceUpdateRequest)}
     */
    @Test
    void testUpdateUserPreference5() throws DmsException {
        Optional<UserPreference> emptyResult = Optional.empty();
        when(userPreferenceRepository.findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(emptyResult);
        UserPreference userPreference = mock(UserPreference.class);
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
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        assertThrows(DmsException.class,
                () -> userPreferenceImpl.updateUserPreference(new UserPreferenceUpdateRequest(1L, "Preference Type", 1L)));
        verify(userPreferenceRepository).findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
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

    /**
     * Method under test: {@link UserPreferenceImpl#getUserPreferenceType(String)}
     */
    @Test
    void testGetUserPreferenceType() {
        Optional<UserPreference> emptyResult = Optional.empty();
        when(userPreferenceRepository.findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        assertThrows(ResponseStatusException.class,
                () -> userPreferenceImpl.getUserPreferenceType("User Preference Type"));
        verify(userPreferenceRepository).findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link UserPreferenceImpl#isUserPreferenceExist(String)}
     */
    @Test
    void testIsUserPreferenceExist() {
        UserPreference userPreference = new UserPreference();
        userPreference.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setHospitalId("42");
        userPreference.setId(1L);
        userPreference.setPreference(1L);
        userPreference.setPreferenceType("Preference Type");
        userPreference.setTenantId("42");
        userPreference.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        userPreference.setUser("User");
        Optional<UserPreference> ofResult = Optional.of(userPreference);
        when(userPreferenceRepository.findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<UserPreference> actualIsUserPreferenceExistResult = userPreferenceImpl
                .isUserPreferenceExist("User Preference Type");
        assertSame(ofResult, actualIsUserPreferenceExistResult);
        assertTrue(actualIsUserPreferenceExistResult.isPresent());
        verify(userPreferenceRepository).findByUserAndPreferenceTypeAndHospitalIdAndTenantId(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
        verify(headerContext).getUser();
    }
}

