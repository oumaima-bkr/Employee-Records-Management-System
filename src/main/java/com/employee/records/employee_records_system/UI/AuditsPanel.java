package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.UI.models.AuditLogTableModel;
import com.employee.records.employee_records_system.model.AuditLog;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AuditsPanel extends JPanel {
    private JTable auditTable;
    private AuditLogTableModel tableModel;

    public AuditsPanel() {
        setLayout(new BorderLayout());

        // Table to display audit logs
        tableModel = new AuditLogTableModel();
        auditTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(auditTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch audit logs when the panel is opened
        reloadAuditLogs();
    }

    private void reloadAuditLogs() {
        try {
            // Fetch audit logs from the backend
            String response = ApiClient.sendRequest("/audits", "GET", null);
            List<AuditLog> auditLogs = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, AuditLog.class));

            // Update the table model
            tableModel.setAuditLogs(auditLogs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load audit logs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
