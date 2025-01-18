package com.employee.records.employee_records_system.UI.models;

import com.employee.records.employee_records_system.model.AuditLog;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditLogTableModel extends AbstractTableModel {

    private final List<AuditLog> auditLogs = new ArrayList<>();
    private final String[] columnNames = {"ID", "Employee ID", "Action", "Changed By", "Details"};


    @Override
    public int getRowCount() {
        return auditLogs.size();
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
        AuditLog auditLog = auditLogs.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> auditLog.getIdAudit();
            case 1 -> auditLog.getEmployeeId();
            case 2 -> auditLog.getAction();
            case 3 -> auditLog.getChangedBy();
            case 5 -> auditLog.getDetails();
            default -> null;
        };
    }


    public void setAuditLogs(List<AuditLog> auditLogs) {
        this.auditLogs.clear();
        this.auditLogs.addAll(auditLogs);
        fireTableDataChanged();
    }

    public AuditLog getAuditLogAt(int rowIndex) {
        return auditLogs.get(rowIndex);
    }
}
