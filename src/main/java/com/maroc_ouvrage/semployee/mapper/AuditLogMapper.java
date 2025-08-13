package com.maroc_ouvrage.semployee.mapper;

import com.maroc_ouvrage.semployee.dto.AuditLogDTO;
import com.maroc_ouvrage.semployee.model.AuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    AuditLogDTO toDto(AuditLog entity);
    AuditLog toEntity(AuditLogDTO dto);
}

