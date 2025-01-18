package com.employee.records.employee_records_system.services;

import com.employee.records.employee_records_system.model.AppUser;
import com.employee.records.employee_records_system.model.UserRole;
import com.employee.records.employee_records_system.repository.AppUserRepository;
import com.employee.records.employee_records_system.repository.AppRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest{

        @InjectMocks
        private AppUserService appUserService;

        @Mock
        private AppUserRepository appUserRepository;

        @Mock
        private AppRoleRepository userRoleRepository;

        private AppUser appUser;
        private UserRole userRole;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            userRole = new UserRole(1L, "Admin", new ArrayList<>());
            appUser = new AppUser(1L, "John Doe", "john.doe@example.com", "password123", userRole);
        }

        @Test
        void saveAppUser_ShouldSaveAppUser() {
            when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.of(userRole));
            when(appUserRepository.save(appUser)).thenReturn(appUser);

            AppUser savedAppUser = appUserService.saveAppUser(appUser);

            assertNotNull(savedAppUser);
            assertEquals("John Doe", savedAppUser.getFullName());
            verify(userRoleRepository, times(1)).findById(userRole.getId());
            verify(appUserRepository, times(1)).save(appUser);
        }

        @Test
        void saveAppUser_ShouldThrowException_WhenRoleNotFound() {
            when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> appUserService.saveAppUser(appUser));

            assertEquals("Role not found with id: 1", exception.getMessage());
            verify(userRoleRepository, times(1)).findById(userRole.getId());
            verify(appUserRepository, never()).save(any(AppUser.class));
        }

        @Test
        void getAllAppUsers_ShouldReturnListOfAppUsers() {
            List<AppUser> appUsers = List.of(appUser);
            when(appUserRepository.findAll()).thenReturn(appUsers);

            List<AppUser> result = appUserService.getAllAppUsers();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("John Doe", result.get(0).getFullName());
            verify(appUserRepository, times(1)).findAll();
        }

        @Test
        void getAppUserById_ShouldReturnAppUser() {
            when(appUserRepository.findById(appUser.getIdUser())).thenReturn(Optional.of(appUser));

            AppUser result = appUserService.getAppUserById(appUser.getIdUser());

            assertNotNull(result);
            assertEquals("John Doe", result.getFullName());
            verify(appUserRepository, times(1)).findById(appUser.getIdUser());
        }

        @Test
        void getAppUserById_ShouldThrowException_WhenNotFound() {
            when(appUserRepository.findById(appUser.getIdUser())).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> appUserService.getAppUserById(appUser.getIdUser()));

            assertEquals("AppUser not found with id: 1", exception.getMessage());
            verify(appUserRepository, times(1)).findById(appUser.getIdUser());
        }

        @Test
        void updateAppUser_ShouldUpdateExistingAppUser() {
            AppUser updatedAppUser = new AppUser(1L, "Jane Doe", "jane.doe@example.com", "newpassword123", userRole);
            when(appUserRepository.findById(appUser.getIdUser())).thenReturn(Optional.of(appUser));
            when(userRoleRepository.findById(userRole.getId())).thenReturn(Optional.of(userRole));
            when(appUserRepository.save(any(AppUser.class))).thenReturn(updatedAppUser);

            AppUser result = appUserService.updateAppUser(appUser.getIdUser(), updatedAppUser);

            assertNotNull(result);
            assertEquals("Jane Doe", result.getFullName());
            assertEquals("jane.doe@example.com", result.getEmail());
            verify(appUserRepository, times(1)).findById(appUser.getIdUser());
            verify(userRoleRepository, times(1)).findById(userRole.getId());
            verify(appUserRepository, times(1)).save(appUser);
        }

        @Test
        void deleteAppUser_ShouldDeleteAppUser() {
            when(appUserRepository.findById(appUser.getIdUser())).thenReturn(Optional.of(appUser));
            doNothing().when(appUserRepository).delete(appUser);

            appUserService.deleteAppUser(appUser.getIdUser());

            verify(appUserRepository, times(1)).findById(appUser.getIdUser());
            verify(appUserRepository, times(1)).delete(appUser);
        }
}
