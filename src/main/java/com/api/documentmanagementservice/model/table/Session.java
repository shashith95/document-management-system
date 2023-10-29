package com.api.documentmanagementservice.model.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ACTIVE_SESSIONS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @Column(name = "ID")
    private String Id;

    @Column(name = "DOCUMENT_ID", nullable = false)
    private String documentId;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;
}
