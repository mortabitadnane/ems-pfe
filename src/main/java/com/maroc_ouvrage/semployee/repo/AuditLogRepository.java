package com.maroc_ouvrage.semployee.repo;


import com.maroc_ouvrage.semployee.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}

