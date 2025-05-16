package com.CRM_PROJECT.Cognizant.services;

import com.CRM_PROJECT.Cognizant.ServiceImplementations.AdminServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.Admin;
import com.CRM_PROJECT.Cognizant.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    @Test
    public void testRegister() {
        Admin admin = new Admin();
        admin.setAdminPassword("password");

        when(passwordEncoder.encode(admin.getAdminPassword())).thenReturn("encodedPassword");
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminService.register(admin);

        assertEquals("encodedPassword", result.getAdminPassword());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testLogin_Success() {
        Admin admin = new Admin();
        admin.setAdminPassword("encodedPassword");

        when(adminRepository.findById("adminName")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("password", admin.getAdminPassword())).thenReturn(true);

        Admin result = adminService.login("adminName", "password");

        assertNotNull(result);
        verify(adminRepository, times(1)).findById("adminName");
    }

    @Test
    public void testLogin_Failure() {
        when(adminRepository.findById("adminName")).thenReturn(Optional.empty());

        Admin result = adminService.login("adminName", "password");

        assertNull(result);
        verify(adminRepository, times(1)).findById("adminName");
    }

    @Test
    public void testUpdatePassword_Success() {
        Admin admin = new Admin();
        admin.setSecretCode("secretCode");

        when(adminRepository.findByAdminName("adminName")).thenReturn(Optional.of(admin));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminService.updatePassword("adminName", "newPassword", "secretCode");

        assertEquals("encodedNewPassword", result.getAdminPassword());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    public void testUpdatePassword_Failure() {
        when(adminRepository.findByAdminName("adminName")).thenReturn(Optional.empty());

        Admin result = adminService.updatePassword("adminName", "newPassword", "secretCode");

        assertNull(result);
        verify(adminRepository, times(1)).findByAdminName("adminName");
    }
}