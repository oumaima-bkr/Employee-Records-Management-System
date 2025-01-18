package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.UI.models.RoleTableModel;
import com.employee.records.employee_records_system.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RolesPanel extends JPanel {
    private JTable roleTable;
    private RoleTableModel tableModel;

    public RolesPanel() {
        setLayout(new BorderLayout());

        // Table to display roles
        tableModel = new RoleTableModel();
        roleTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(roleTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addRoleButton = new JButton("Add Role");
        JButton editRoleButton = new JButton("Edit Role");
        JButton deleteRoleButton = new JButton("Delete Role");

        buttonPanel.add(addRoleButton);
        buttonPanel.add(editRoleButton);
        buttonPanel.add(deleteRoleButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addRoleButton.addActionListener(this::onAddRole);
        editRoleButton.addActionListener(this::onEditRole);
        deleteRoleButton.addActionListener(this::onDeleteRole);

        // Fetch roles when panel is opened
        reloadRoles();
    }

    private void reloadRoles() {
        try {
            // Fetch roles from the backend
            String response = ApiClient.sendRequest("/Roles", "GET", null);
            List<UserRole> roles = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, UserRole.class));

            // Update the table model
            tableModel.setRoles(roles);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load roles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAddRole(ActionEvent event) {
        // Open an input dialog to get the role name
        String roleName = JOptionPane.showInputDialog(this, "Enter Role Name:", "Add Role", JOptionPane.PLAIN_MESSAGE);

        // If the user cancels or doesn't input anything, exit the method
        if (roleName == null || roleName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Role name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create a new role object
            UserRole newRole = new UserRole(null, roleName);

            // Send the role to the backend
            String response = ApiClient.sendRequest("/Roles", "POST", new ObjectMapper().writeValueAsString(newRole));
            JOptionPane.showMessageDialog(this, "Role added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload roles after adding a new one
            reloadRoles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to add role: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEditRole(ActionEvent event) {
        int selectedRow = roleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No role selected!");
            return;
        }
        UserRole selectedRole = tableModel.getRoleAt(selectedRow);

        // Open an input dialog to edit the role name
        String newRoleName = JOptionPane.showInputDialog(this, "Enter New Role Name:", selectedRole.getRoleName());

        if (newRoleName == null || newRoleName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Role name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Update the role object
            selectedRole.setRoleName(newRoleName);

            // Send the updated role to the backend
            String response = ApiClient.sendRequest("/Roles/" + selectedRole.getId(), "PUT", new ObjectMapper().writeValueAsString(selectedRole));
            JOptionPane.showMessageDialog(this, "Role updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload roles after editing
            reloadRoles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to update role: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDeleteRole(ActionEvent event) {
        int selectedRow = roleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No role selected!");
            return;
        }
        UserRole selectedRole = tableModel.getRoleAt(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the role: " + selectedRole.getRoleName() + "?", "Delete Role", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Send the delete request to the backend
            ApiClient.sendRequest("/Roles/" + selectedRole.getId(), "DELETE", null);
            JOptionPane.showMessageDialog(this, "Role deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload roles after deletion
            reloadRoles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete role: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
