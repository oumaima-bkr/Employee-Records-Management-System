package com.employee.records.employee_records_system.repository;

import com.employee.records.employee_records_system.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
