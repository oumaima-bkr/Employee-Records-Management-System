package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.UI.models.AppUserTableModel;
import com.employee.records.employee_records_system.model.AppUser;
import com.employee.records.employee_records_system.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AppUserPanel extends JPanel {
    private JTable appUserTable;
    private AppUserTableModel tableModel;

    public AppUserPanel() {
        setLayout(new BorderLayout());

        // Table to display users
        tableModel = new AppUserTableModel();
        appUserTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(appUserTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for actions
        JPanel buttonPanel = new JPanel();
        JButton addUserButton = new JButton("Add User");
        JButton editUserButton = new JButton("Edit User");
        JButton deleteUserButton = new JButton("Delete User");

        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addUserButton.addActionListener(this::onAddUser);
        editUserButton.addActionListener(this::onEditUser);
        deleteUserButton.addActionListener(this::onDeleteUser);

        // Fetch users when panel is opened
        reloadAppUsers();
    }

    private void reloadAppUsers() {
        try {
            // Fetch users from the backend
            String response = ApiClient.sendRequest("/users", "GET", null);
            List<AppUser> appUsers = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, AppUser.class));

            // Update the table model
            tableModel.setAppUsers(appUsers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAddUser(ActionEvent event) {
        AppUser appUser = openUserDialog(null);
        if (appUser != null) {
            try {
                // Send the user to the backend
                String response = ApiClient.sendRequest("/users", "POST", new ObjectMapper().writeValueAsString(appUser));
                JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reload users after adding a new one
                reloadAppUsers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to add user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEditUser(ActionEvent event) {
        int selectedRow = appUserTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No user selected!");
            return;
        }
        AppUser selectedUser = tableModel.getAppUserAt(selectedRow);
        AppUser updatedUser = openUserDialog(selectedUser);
        if (updatedUser != null) {
            try {
                // Send the updated user to the backend
                String response = ApiClient.sendRequest("/users/" + updatedUser.getIdUser(), "PUT", new ObjectMapper().writeValueAsString(updatedUser));
                JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reload users after editing
                reloadAppUsers();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Failed to update user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onDeleteUser(ActionEvent event) {
        int selectedRow = appUserTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No user selected!");
            return;
        }
        AppUser selectedUser = tableModel.getAppUserAt(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the user: " + selectedUser.getFullName() + "?", "Delete User", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Send the delete request to the backend
            ApiClient.sendRequest("/users/" + selectedUser.getIdUser(), "DELETE", null);
            JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reload users after deletion
            reloadAppUsers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to delete user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private AppUser openUserDialog(AppUser appUser) {
        JTextField fullNameField = new JTextField(appUser != null ? appUser.getFullName() : "");
        JTextField emailField = new JTextField(appUser != null ? appUser.getEmail() : "");
        JTextField passwordField = new JTextField(appUser != null ? appUser.getPassword() : "");

        JComboBox<UserRole> roleComboBox = new JComboBox<>();
        loadRoles(roleComboBox, appUser != null ? appUser.getRole() : null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, appUser == null ? "Add User" : "Edit User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                UserRole selectedRole = (UserRole) roleComboBox.getSelectedItem();
                return new AppUser(
                        appUser != null ? appUser.getIdUser() : null,
                        fullNameField.getText().trim(),
                        emailField.getText().trim(),
                        passwordField.getText().trim(),
                        selectedRole
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private void loadRoles(JComboBox<UserRole> roleComboBox, UserRole selectedRole) {
        try {
            String response = ApiClient.sendRequest("/Roles", "GET", null);
            List<UserRole> roles = new ObjectMapper().readValue(response,
                    new ObjectMapper().getTypeFactory().constructCollectionType(List.class, UserRole.class));

            for (UserRole role : roles) {
                roleComboBox.addItem(role);
            }

            if (selectedRole != null) {
                roleComboBox.setSelectedItem(selectedRole);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load roles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
