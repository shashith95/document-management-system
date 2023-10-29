package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DIRECTORY_DETAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PATH", nullable = false)
    private String path;

    @Column(name = "BUCKET", nullable = false)
    private String bucket;

    @Column(name = "TENANT", nullable = false)
    private String tenant;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;
}
