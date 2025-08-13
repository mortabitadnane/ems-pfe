package com.maroc_ouvrage.semployee.audit;
import com.maroc_ouvrage.semployee.model.AuditLog;
import com.maroc_ouvrage.semployee.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLoggingAspect {

    private final AuditLogRepository auditLogRepository;

    @Before("@annotation(auditable)")
    public void logAction(JoinPoint joinPoint, Auditable auditable) {
        // Get authenticated username
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated()) ? auth.getName() : "Anonymous";

        // Create audit log entry
        AuditLog log = new AuditLog();
        log.setPerformedBy(username);
        log.setAction(auditable.action());

        // If details from annotation are empty, log method + params
        if (!auditable.details().isBlank()) {
            log.setDetails(auditable.details());
        } else {
            String params = Arrays.toString(joinPoint.getArgs());
            log.setDetails(joinPoint.getSignature().toShortString() + " called with args " + params);
        }

        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}
