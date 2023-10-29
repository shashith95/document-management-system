package com.api.documentmanagementservice.model.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "DOCUMENT_DETAILS")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentDetails {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "DIRECTORY_DETAILS_ID", nullable = false)
    private Long directoryDetailsId;

    @Column(name = "DOCUMENT_NAME", nullable = false)
    private String documentName;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;
}
