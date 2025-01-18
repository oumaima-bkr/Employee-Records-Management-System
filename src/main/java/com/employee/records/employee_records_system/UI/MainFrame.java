package com.employee.records.employee_records_system.UI;

import com.employee.records.employee_records_system.UI.models.LimitedEmployeesPanel;
import com.employee.records.employee_records_system.model.AppUser;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static AppUser loggedInUser;

    public MainFrame() {
        setTitle("Employee Records System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setContentPane(new LoginPanel());
    }

    public static void openDashboard(AppUser user) {
        loggedInUser = user;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dashboard - " + loggedInUser.getRole().getRoleName());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JTabbedPane tabbedPane = new JTabbedPane();

            if (loggedInUser.getRole().getRoleName().equalsIgnoreCase("HR Personnel")) {
                tabbedPane.addTab("Employees", new EmployeesPanel());
            } else if (loggedInUser.getRole().getRoleName().equalsIgnoreCase("Manager")) {
                tabbedPane.addTab("Employees (Limited)", new LimitedEmployeesPanel(loggedInUser));
            } else if (loggedInUser.getRole().getRoleName().equalsIgnoreCase("Administrator")) {
                tabbedPane.addTab("Employees", new EmployeesPanel());
                tabbedPane.addTab("Roles", new RolesPanel());
                tabbedPane.addTab("Audit Logs", new AuditsPanel());
            }

            frame.setContentPane(tabbedPane);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
