package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.PropertyAttributeService;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttributeSettingsControllerTest {

    @Mock
    private PropertyAttributeService propertyAttributeService;

    @InjectMocks
    private AttributeSettingsController attributeSettingsController;

    @Test
    public void testDeleteAttributeByIdWhenAttributeIdIsValidThenServiceMethodIsCalledWithSameAttributeId() throws DmsException {
        // Arrange
        Long attributeId = 1L;
        CommonResponse commonResponse = new CommonResponse("message", "messageCode", true, null, null);
        when(propertyAttributeService.deleteAttributeById(attributeId)).thenReturn(ResponseEntity.ok(commonResponse));

        // Act
        ResponseEntity<CommonResponse> response = attributeSettingsController.deleteAttributeById(attributeId);

        // Assert
        verify(propertyAttributeService, times(1)).deleteAttributeById(attributeId);
        assertThat(response.getBody()).isEqualTo(commonResponse);
    }

    @Test
    public void testDeleteAttributeByIdWhenServiceThrowsServerSideExceptionThenControllerThrowsSameException() throws DmsException {
        // Arrange
        Long attributeId = 1L;
        when(propertyAttributeService.deleteAttributeById(attributeId)).thenThrow(DmsException.class);

        // Act & Assert
        assertThatThrownBy(() -> attributeSettingsController.deleteAttributeById(attributeId))
                .isInstanceOf(DmsException.class);
        verify(propertyAttributeService, times(1)).deleteAttributeById(attributeId);
    }

    @Test
    public void testDeleteAttributeByIdWhenCalledThenServiceMethodIsCalledWithCorrectId() throws DmsException {
        // Arrange
        Long attributeId = 1L;
        CommonResponse commonResponse = new CommonResponse("message", "messageCode", true, null, null);
        when(propertyAttributeService.deleteAttributeById(attributeId)).thenReturn(ResponseEntity.ok(commonResponse));

        // Act
        ResponseEntity<CommonResponse> response = attributeSettingsController.deleteAttributeById(attributeId);

        // Assert
        verify(propertyAttributeService, times(1)).deleteAttributeById(attributeId);
        assertThat(response.getBody()).isEqualTo(commonResponse);
    }

    @Test
    public void testDeleteAttributeByIdWhenServiceThrowsExceptionThenControllerThrowsException() throws DmsException {
        // Arrange
        Long attributeId = 1L;
        when(propertyAttributeService.deleteAttributeById(attributeId)).thenThrow(DmsException.class);

        // Act & Assert
        assertThatThrownBy(() -> attributeSettingsController.deleteAttributeById(attributeId))
                .isInstanceOf(DmsException.class);
        verify(propertyAttributeService, times(1)).deleteAttributeById(attributeId);
    }
}