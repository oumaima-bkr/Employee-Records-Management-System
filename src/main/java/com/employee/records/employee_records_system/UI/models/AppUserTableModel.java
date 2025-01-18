package com.employee.records.employee_records_system.UI.models;

import com.employee.records.employee_records_system.model.AppUser;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AppUserTableModel extends AbstractTableModel {

    private final List<AppUser> appUsers = new ArrayList<>();
    private final String[] columnNames = {"ID", "Full Name", "Email", "Role"};

    @Override
    public int getRowCount() {
        return appUsers.size();
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
        AppUser appUser = appUsers.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> appUser.getIdUser();
            case 1 -> appUser.getFullName();
            case 2 -> appUser.getEmail();
            case 3 -> appUser.getRole().getRoleName();
            default -> null;
        };
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers.clear();
        this.appUsers.addAll(appUsers);
        fireTableDataChanged();
    }

    public AppUser getAppUserAt(int rowIndex) {
        return appUsers.get(rowIndex);
    }
}
