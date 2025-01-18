package com.employee.records.employee_records_system.UI.models;

import com.employee.records.employee_records_system.model.UserRole;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoleTableModel extends AbstractTableModel {

    private final List<UserRole> roles = new ArrayList<>();
    private final String[] columnNames = {"ID", "Role Name"};

    @Override
    public int getRowCount() {
        return roles.size();
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
        UserRole role = roles.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> role.getId();
            case 1 -> role.getRoleName();
            default -> null;
        };
    }

    public void setRoles(List<UserRole> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
        fireTableDataChanged();
    }
    public List<UserRole> getRolesList() {
        return roles;
    }

    public UserRole getRoleAt(int rowIndex) {
        return roles.get(rowIndex);
    }
}
