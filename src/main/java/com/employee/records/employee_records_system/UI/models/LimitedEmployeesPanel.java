package com.employee.records.employee_records_system.UI.models;

import com.employee.records.employee_records_system.UI.ApiClient;
import com.employee.records.employee_records_system.model.AppUser;
import com.employee.records.employee_records_system.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class LimitedEmployeesPanel extends JPanel {
    private JTable employeeTable;
    private EmployeeTableModel tableModel;
    private AppUser manager;

    public LimitedEmployeesPanel(AppUser manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        reloadEmployees();
    }

    private void reloadEmployees() {
        try {
            String response = ApiClient.sendRequest("/employees", "GET", null);
            List<Employee> employees = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Employee.class));

            employees.removeIf(e -> !e.getDepartment().equals(manager.getRole().getRoleName()));
            tableModel.setEmployees(employees);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}