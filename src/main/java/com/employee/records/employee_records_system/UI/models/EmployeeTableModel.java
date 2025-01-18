package com.employee.records.employee_records_system.UI.models;

import com.employee.records.employee_records_system.model.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {

    private final List<Employee> employees = new ArrayList<>();
    private final String[] columnNames = {"ID", "Full Name", "Job Title", "Department", "Hire Date", "Status", "Contact", "Address"};

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> employee.getEmployeeId();
            case 1 -> employee.getFullName();
            case 2 -> employee.getJobTitle();
            case 3 -> employee.getDepartment();
            case 4 -> employee.getHireDate();
            case 5 -> employee.getEmploymentStatus();
            case 6 -> employee.getContactInformation();
            case 7 -> employee.getAddress();
            default -> null;
        };
    }

    public void setEmployees(List<Employee> employees) {
        this.employees.clear();
        this.employees.addAll(employees);
        fireTableDataChanged();
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employees.get(rowIndex);
    }
}
