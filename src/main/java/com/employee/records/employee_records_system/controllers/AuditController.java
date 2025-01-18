package com.employee.records.employee_records_system.controllers;


import com.employee.records.employee_records_system.model.AuditLog;
import com.employee.records.employee_records_system.services.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @Operation(summary = "Get all audits", description = "Fetch all audit logs")
    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAudits() {
        List<AuditLog> audits = auditService.getAllAudits();
        return ResponseEntity.ok(audits);
    }
}
