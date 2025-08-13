package com.maroc_ouvrage.semployee.service;

import com.maroc_ouvrage.semployee.dto.AuditLogDTO;

import java.util.List;

public interface AuditLogService {
    AuditLogDTO createAuditLog(AuditLogDTO dto);
    List<AuditLogDTO> getAllLogs();
}
