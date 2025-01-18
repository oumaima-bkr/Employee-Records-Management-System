package com.employee.records.employee_records_system.services;

import com.employee.records.employee_records_system.model.Employee;
import com.employee.records.employee_records_system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(
                1L,
                "Oumaima bakri",
                "Software Engineer",
                "IT",
                LocalDate.of(2020, 1, 1),
                "Active",
                "oumaima.bakri08@example.com",
                "123 Main Street"
        );
    }

    @Test
    void saveEmployee_ShouldLogAuditAndReturnSavedEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("Oumaima bakri", savedEmployee.getFullName());
        verify(employeeRepository, times(1)).save(employee);
        verify(auditService, times(1)).logChange(eq(1L), eq("CREATE"), eq("System"), anyString());
    }

    @Test
    void updateEmployee_ShouldLogAuditAndReturnUpdatedEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = new Employee(
                1L,
                "Ali Bakri",
                "Software Engineer",
                "IT",
                LocalDate.of(2020, 1, 1),
                "Active",
                "ali.bakri@example.com",
                "456 Elm Street"
        );

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertNotNull(result);
        assertEquals("Ali Bakri", result.getFullName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(auditService, times(1)).logChange(eq(1L), eq("UPDATE"), eq("System"), contains("Before:"));
    }

    @Test
    void deleteEmployee_ShouldLogAuditWhenEmployeeExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
        verify(auditService, times(1)).logChange(eq(1L), eq("DELETE"), eq("System"), anyString());
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertEquals(1, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeIfExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals("Oumaima bakri", foundEmployee.get().getFullName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_ShouldReturnEmptyIfNotExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = employeeService.getEmployeeById(1L);

        assertFalse(foundEmployee.isPresent());
        verify(employeeRepository, times(1)).findById(1L);
    }

}
