package com.employee.records.employee_records_system.services;


import com.employee.records.employee_records_system.model.AuditLog;
import com.employee.records.employee_records_system.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;


    public void logChange(Long employeeId, String action, String changedBy, String details) {
        AuditLog auditLog = new AuditLog(
                employeeId,
                action,
                changedBy,
                details
        );
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllAudits() {
        return auditLogRepository.findAll();
    }


}
