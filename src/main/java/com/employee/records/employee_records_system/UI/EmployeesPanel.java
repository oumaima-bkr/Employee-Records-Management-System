package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.UI.models.EmployeeTableModel;
import com.employee.records.employee_records_system.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class EmployeesPanel extends JPanel {
    private JTable employeeTable;
    private EmployeeTableModel tableModel;

    public EmployeesPanel() {
        setLayout(new BorderLayout());

        // Table to display employees
        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton editEmployeeButton = new JButton("Edit Employee");
        JButton deleteEmployeeButton = new JButton("Delete Employee");

        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(editEmployeeButton);
        buttonPanel.add(deleteEmployeeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addEmployeeButton.addActionListener(this::onAddEmployee);
        editEmployeeButton.addActionListener(this::onEditEmployee);
        deleteEmployeeButton.addActionListener(this::onDeleteEmployee);

        // Fetch employees when panel is opened
        reloadEmployees();
    }

    private void reloadEmployees() {
        try {
            // Fetch employees from the backend
            String response = ApiClient.sendRequest("/employees", "GET", null);
            List<Employee> employees = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, Employee.class));

            // Update the table model
            tableModel.setEmployees(employees);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAddEmployee(ActionEvent event) {
        Employee employee = openEmployeeDialog(null);
        if (employee != null) {
            try {
                // Send the employee to the backend
                String response = ApiClient.sendRequest("/employees", "POST", new ObjectMapper().writeValueAsString(employee));
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reload employees after adding a new one
                reloadEmployees();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to add employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEditEmployee(ActionEvent event) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No employee selected!");
            return;
        }
        Employee selectedEmployee = tableModel.getEmployeeAt(selectedRow);
        Employee updatedEmployee = openEmployeeDialog(selectedEmployee);
        if (updatedEmployee != null) {
            try {
                // Send the updated employee to the backend
                String response = ApiClient.sendRequest("/employees/" + updatedEmployee.getEmployeeId(), "PUT", new ObjectMapper().writeValueAsString(updatedEmployee));
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reload employees after editing
                reloadEmployees();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to update employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDeleteEmployee(ActionEvent event) {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No employee selected!");
            return;
        }
        Employee selectedEmployee = tableModel.getEmployeeAt(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the employee: " + selectedEmployee.getFullName() + "?", "Delete Employee", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Send the delete request to the backend
            ApiClient.sendRequest("/employees/" + selectedEmployee.getEmployeeId(), "DELETE", null);
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload employees after deletion
            reloadEmployees();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Employee openEmployeeDialog(Employee employee) {
        JTextField fullNameField = new JTextField(employee != null ? employee.getFullName() : "");
        JTextField jobTitleField = new JTextField(employee != null ? employee.getJobTitle() : "");
        JTextField departmentField = new JTextField(employee != null ? employee.getDepartment() : "");
        JTextField hireDateField = new JTextField(employee != null ? employee.getHireDate().toString() : "");
        JTextField employmentStatusField = new JTextField(employee != null ? employee.getEmploymentStatus() : "");
        JTextField contactInfoField = new JTextField(employee != null ? employee.getContactInformation() : "");
        JTextField addressField = new JTextField(employee != null ? employee.getAddress() : "");

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Job Title:"));
        panel.add(jobTitleField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Hire Date (YYYY-MM-DD):"));
        panel.add(hireDateField);
        panel.add(new JLabel("Employment Status:"));
        panel.add(employmentStatusField);
        panel.add(new JLabel("Contact Information:"));
        panel.add(contactInfoField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(this, panel, employee == null ? "Add Employee" : "Edit Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Employee(
                        employee != null ? employee.getEmployeeId() : null,
                        fullNameField.getText().trim(),
                        jobTitleField.getText().trim(),
                        departmentField.getText().trim(),
                        hireDateField.getText().trim(),
                        employmentStatusField.getText().trim(),
                        contactInfoField.getText().trim(),
                        addressField.getText().trim()
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
