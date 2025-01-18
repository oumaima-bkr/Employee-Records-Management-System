package com.employee.records.employee_records_system.services;


import com.employee.records.employee_records_system.model.Employee;
import com.employee.records.employee_records_system.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuditService auditService;



    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    public Employee updateEmployee(Long id, Employee employee) {
        return employeeRepository.findById(id).map(existingEmployee -> {
            String beforeUpdate = existingEmployee.toString();
            existingEmployee.setFullName(employee.getFullName());
            existingEmployee.setJobTitle(employee.getJobTitle());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setEmploymentStatus(employee.getEmploymentStatus());
            existingEmployee.setContactInformation(employee.getContactInformation());
            existingEmployee.setAddress(employee.getAddress());
            Employee updatedEmployee = employeeRepository.save(existingEmployee);

            auditService.logChange(
                    id,
                    "UPDATE",
                    "System", // Replace with the actual user
                    "Before: " + beforeUpdate + ", After: " + updatedEmployee.toString()
            );
            return updatedEmployee;
        }).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).ifPresent(employee -> {
            employeeRepository.deleteById(id);
            auditService.logChange(
                    id,
                    "DELETE",
                    "System", // Replace with the actual user
                    "Employee deleted: " + employee.toString()
            );
        });
    }

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        auditService.logChange(
                savedEmployee.getEmployeeId(),
                "CREATE",
                "System",
                "Employee created: " + savedEmployee.toString()
        );
        return savedEmployee;
    }
}

