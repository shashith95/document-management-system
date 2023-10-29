package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "BUCKET_DETAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "HOSPITAL_ID", nullable = false)
    private String hospitalId;

    @Column(name = "TENANT_ID", nullable = false)
    private String tenantId;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME", nullable = false)
    private LocalDateTime modifiedDateTime;
}
