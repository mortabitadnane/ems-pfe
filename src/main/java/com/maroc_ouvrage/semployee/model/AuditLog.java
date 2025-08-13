package com.maroc_ouvrage.semployee.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g., "USER_LOGIN", "EMPLOYEE_ADDED"
    private String performedBy; // username or ID
    private String details; // additional description
    private LocalDateTime timestamp;



    // Getters & Setters
}
