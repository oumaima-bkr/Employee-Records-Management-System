package com.employee.records.employee_records_system.services;

import com.employee.records.employee_records_system.model.AuditLog;
import com.employee.records.employee_records_system.repository.AuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuditServiceTest {
    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditService auditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logChange_ShouldSaveAuditLog() {
        // Arrange
        AuditLog auditLog = new AuditLog(1L, "CREATE", "System", "Details");
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(auditLog);

        // Act
        auditService.logChange(1L, "CREATE", "System", "Details");

        // Assert
        verify(auditLogRepository, times(1)).save(any(AuditLog.class));
    }
}
