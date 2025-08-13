package com.maroc_ouvrage.semployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    private String action;
    private String performedBy;
    private String details;
    private LocalDateTime timestamp;
}

