package com.maroc_ouvrage.semployee.service.Imp;

import com.maroc_ouvrage.semployee.dto.AuditLogDTO;
import com.maroc_ouvrage.semployee.mapper.AuditLogMapper;
import com.maroc_ouvrage.semployee.model.AuditLog;
import com.maroc_ouvrage.semployee.repo.AuditLogRepository;
import com.maroc_ouvrage.semployee.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    public AuditLogDTO createAuditLog(AuditLogDTO dto) {
        AuditLog entity = auditLogMapper.toEntity(dto);
        entity.setTimestamp(LocalDateTime.now());
        return auditLogMapper.toDto(auditLogRepository.save(entity));
    }

    @Override
    public List<AuditLogDTO> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(auditLogMapper::toDto)
                .collect(Collectors.toList());
    }
}

