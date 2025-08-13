package com.maroc_ouvrage.semployee.controller;

import com.maroc_ouvrage.semployee.dto.AuditLogDTO;
import com.maroc_ouvrage.semployee.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;


    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
}

