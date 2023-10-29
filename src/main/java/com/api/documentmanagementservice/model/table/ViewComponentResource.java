package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "VIEW_COMPONENT_RESOURCES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ViewComponentResource implements Serializable {

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

    @Column(name = "ATTRIBUTE_ID", nullable = false)
    private Long attributeId;

    @Column(name = "ATTRIBUTE_NAME", nullable = false)
    private String attributeName;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;
}
