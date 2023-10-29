package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {

}
