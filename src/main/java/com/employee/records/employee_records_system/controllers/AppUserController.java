package com.employee.records.employee_records_system.controllers;


import com.employee.records.employee_records_system.model.AppUser;
import com.employee.records.employee_records_system.services.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "application Users API", description = "Endpoints for managing application users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;



    @Operation(summary = "Get all users", description = "Fetch all application users")
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllAppUsers() {
        List<AppUser> users = appUserService.getAllAppUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Fetch a single application user by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUserById(@PathVariable Long id) {
        AppUser user = appUserService.getAppUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Create a user", description = "Add a new application user")
    @PostMapping
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser appUser) {
        AppUser savedUser = appUserService.saveAppUser(appUser);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Update a user", description = "Update the details of an existing application user")
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateAppUser(@PathVariable Long id, @RequestBody AppUser updatedAppUser) {
        AppUser updatedUser = appUserService.updateAppUser(id, updatedAppUser);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user", description = "Delete an application user by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Long id) {
        appUserService.deleteAppUser(id);
        return ResponseEntity.noContent().build();
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        AppUser user = appUserService.login(email, password);
        return ResponseEntity.ok(user);
    }

    // Check role
    @GetMapping("/{id}/role")
    public ResponseEntity<String> checkRole(@PathVariable Long id) {
        AppUser user = appUserService.getAppUserById(id);
        return ResponseEntity.ok(user.getRole().getRoleName());
    }

}
