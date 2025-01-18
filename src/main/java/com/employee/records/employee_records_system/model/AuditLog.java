package com.employee.records.employee_records_system.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAudit;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String changedBy;



    @Column(columnDefinition = "TEXT")
    private String details;

    public AuditLog(Long employeeId, String action, String changedBy, String details) {
        this.employeeId = employeeId;
        this.action = action;
        this.changedBy = changedBy;
        this.details = details;
    }
}
