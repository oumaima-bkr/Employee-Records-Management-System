package com.employee.records.employee_records_system.controllers;


import com.employee.records.employee_records_system.model.UserRole;
import com.employee.records.employee_records_system.services.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Roles")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @Operation(summary = "Get all user roles", description = "Fetch all user roles")
    @GetMapping
    public ResponseEntity<List<UserRole>> getAllUserRoles() {
        List<UserRole> roles = userRoleService.getAllUserRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Create a user role", description = "Add a new user role")
    @PostMapping
    public ResponseEntity<UserRole> createUserRole(@RequestBody UserRole userRole) {
        UserRole savedRole = userRoleService.saveUserRole(userRole);
        return ResponseEntity.ok(savedRole);
    }

    @Operation(summary = "Update a user role", description = "Update an existing user role by ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable Long id, @RequestBody UserRole updatedUserRole) {
        UserRole role = userRoleService.updateUserRole(id, updatedUserRole);
        return ResponseEntity.ok(role);
    }

    @Operation(summary = "Delete a user role", description = "Delete an existing user role by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        userRoleService.deleteUserRole(id);
        return ResponseEntity.noContent().build();
    }

}
