package com.employee.records.employee_records_system.services;
import com.employee.records.employee_records_system.model.AppUser;
import com.employee.records.employee_records_system.model.UserRole;
import com.employee.records.employee_records_system.repository.AppRoleRepository;
import com.employee.records.employee_records_system.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository userRoleRepository;


    // Create or Save an AppUser
    public AppUser saveAppUser(AppUser appUser) {
        Optional<UserRole> userRole = userRoleRepository.findById(appUser.getRole().getId());
        if (userRole.isEmpty()) {
            throw new RuntimeException("Role not found with id: " + appUser.getRole().getId());
        }
        appUser.setRole(userRole.get());
        return appUserRepository.save(appUser);
    }

    // Get All AppUsers
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    // Get AppUser by ID
    public AppUser getAppUserById(Long idUser) {
        return appUserRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("AppUser not found with id: " + idUser));
    }

    // Update AppUser
    public AppUser updateAppUser(Long idUser, AppUser updatedAppUser) {
        AppUser existingAppUser = getAppUserById(idUser);
        existingAppUser.setFullName(updatedAppUser.getFullName());
        existingAppUser.setEmail(updatedAppUser.getEmail());
        existingAppUser.setPassword(updatedAppUser.getPassword());

        Optional<UserRole> userRole = userRoleRepository.findById(updatedAppUser.getRole().getId());
        if (userRole.isEmpty()) {
            throw new RuntimeException("Role not found with id: " + updatedAppUser.getRole().getId());
        }
        existingAppUser.setRole(userRole.get());
        return appUserRepository.save(existingAppUser);
    }

    // Delete AppUser
    public void deleteAppUser(Long idUser) {
        AppUser appUser = getAppUserById(idUser);
        appUserRepository.delete(appUser);
    }


    // Login method
    public AppUser login(String email, String password) {
        return appUserRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    // Check role for permissions
    public boolean hasRole(AppUser user, String roleName) {
        return user.getRole().getRoleName().equalsIgnoreCase(roleName);
    }
}
