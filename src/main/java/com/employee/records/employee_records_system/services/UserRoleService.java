package com.employee.records.employee_records_system.services;


import com.employee.records.employee_records_system.model.UserRole;
import com.employee.records.employee_records_system.repository.AppRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    final private AppRoleRepository appRoleRepository;


    // Create or Save a UserRole
    public UserRole saveUserRole(UserRole userRole) {
        return appRoleRepository.save(userRole);
    }

    // Get All UserRoles
    public List<UserRole> getAllUserRoles() {
        return appRoleRepository.findAll();
    }

    // Get UserRole by ID
    public UserRole getUserRoleById(Long id) {
        return appRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found with id: " + id));
    }

    // Update UserRole
    public UserRole updateUserRole(Long id, UserRole updatedUserRole) {
        UserRole existingUserRole = getUserRoleById(id);
        existingUserRole.setRoleName(updatedUserRole.getRoleName());
        return appRoleRepository.save(existingUserRole);
    }

    // Delete UserRole
    public void deleteUserRole(Long id) {
        UserRole userRole = getUserRoleById(id);
        appRoleRepository.delete(userRole);
    }
}
