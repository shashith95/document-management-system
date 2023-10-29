package com.api.documentmanagementservice.model.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FileDetails {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "DOCUMENT_ID", nullable = false)
    private String documentId;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "SEQ", nullable = false)
    private Long seq;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;

    @Column(name = "EXTENSION", nullable = false)
    private String extension;
}
