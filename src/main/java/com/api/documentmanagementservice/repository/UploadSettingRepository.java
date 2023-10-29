package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.UploadSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadSettingRepository extends JpaRepository<UploadSetting, Long> {

    List<UploadSetting> findAllByHospitalIdAndTenantId(String hospitalId, String tenantId);
}
