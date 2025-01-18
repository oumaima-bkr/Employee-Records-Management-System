package com.employee.records.employee_records_system.services;
import com.employee.records.employee_records_system.model.UserRole;
import com.employee.records.employee_records_system.repository.AppRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    @Mock
    private AppRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService;
    private UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole = new UserRole(1L, "Admin");
    }

    @Test
    void saveUserRole_ShouldSaveUserRole() {
        when(userRoleRepository.save(userRole)).thenReturn(userRole);

        UserRole savedUserRole = userRoleService.saveUserRole(userRole);

        assertNotNull(savedUserRole);
        assertEquals("Admin", savedUserRole.getRoleName());
        verify(userRoleRepository, times(1)).save(userRole);
    }


    @Test
    void getAllUserRoles_ShouldReturnListOfUserRoles() {
        List<UserRole> userRoles = List.of(userRole);
        when(userRoleRepository.findAll()).thenReturn(userRoles);

        List<UserRole> result = userRoleService.getAllUserRoles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getRoleName());
        verify(userRoleRepository, times(1)).findAll();
    }

    @Test
    void getUserRoleById_ShouldReturnUserRole() {
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRole));

        UserRole result = userRoleService.getUserRoleById(1L);

        assertNotNull(result);
        assertEquals("Admin", result.getRoleName());
        verify(userRoleRepository, times(1)).findById(1L);
    }

    @Test
    void getUserRoleById_ShouldThrowException_WhenNotFound() {
        when(userRoleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userRoleService.getUserRoleById(1L));

        assertEquals("UserRole not found with id: 1", exception.getMessage());
        verify(userRoleRepository, times(1)).findById(1L);
    }

    @Test
    void updateUserRole_ShouldUpdateExistingUserRole() {
        UserRole updatedUserRole = new UserRole(1L, "Manager", new ArrayList<>());
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRole));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(updatedUserRole);

        UserRole result = userRoleService.updateUserRole(1L, updatedUserRole);

        assertNotNull(result);
        assertEquals("Manager", result.getRoleName());
        verify(userRoleRepository, times(1)).findById(1L);
        verify(userRoleRepository, times(1)).save(userRole);
    }

    @Test
    void deleteUserRole_ShouldDeleteUserRole() {
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRole));
        doNothing().when(userRoleRepository).delete(userRole);

        userRoleService.deleteUserRole(1L);

        verify(userRoleRepository, times(1)).findById(1L);
        verify(userRoleRepository, times(1)).delete(userRole);
    }








}
