package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.AuditLogDTO;
import com.maroc_ouvrage.semployee.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogDTO> createLog(@RequestBody AuditLogDTO dto) {
        return ResponseEntity.ok(auditLogService.createAuditLog(dto));
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
}

