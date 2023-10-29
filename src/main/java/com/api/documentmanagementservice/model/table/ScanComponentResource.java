package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "SCAN_COMPONENT_RESOURCES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ScanComponentResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMPONENT_SETTING_ID", nullable = false)
    private Long componentSettingId;

    @Column(name = "PROPERTY_ID", nullable = false)
    private Long propertyId;

    @Column(name = "PROPERTY_NAME", nullable = false)
    private String propertyName;

    @Column(name = "ATTRIBUTE_ID")
    private Long attributeId;

    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory;

    @Column(name = "IS_FREE_TEXT", nullable = false)
    private Boolean isFreeText;

    @Column(name = "IS_ADDABLE", nullable = false)
    private Boolean isAddable;

    @Column(name = "FREE_TEXT_TYPE", nullable = false)
    private Long freeTextType;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;
}