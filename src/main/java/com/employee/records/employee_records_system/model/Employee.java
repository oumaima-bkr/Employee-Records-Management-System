package com.employee.records.employee_records_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String jobTitle;
    @Column(nullable = false)
    private String department;
    @Column(nullable = false)
    private String hireDate;
    @Column(nullable = false)
    private String employmentStatus;
    @Column(nullable = false)
    private String contactInformation;
    @Column(nullable = false)
    private String address;
}

