package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPanel() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(e -> onLogin());
    }

    private void onLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            Map<String, String> credentials = new HashMap<>();
            credentials.put("email", email);
            credentials.put("password", password);

            String response = ApiClient.sendRequest("/users/login", "POST", new ObjectMapper().writeValueAsString(credentials));
            AppUser user = new ObjectMapper().readValue(response, AppUser.class);

            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFullName());

            MainFrame.openDashboard(user);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Login failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
