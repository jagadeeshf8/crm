package com.CRM_PROJECT.Cognizant.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.CRM_PROJECT.Cognizant.entity.Admin;
import com.CRM_PROJECT.Cognizant.repository.AdminRepository;
import com.CRM_PROJECT.Cognizant.service.AdminService;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Admin register(Admin admin) {
        admin.setAdminPassword(passwordEncoder.encode(admin.getAdminPassword()));
        return adminRepository.save(admin);
    }
    @Override
    public Admin login(String adminName, String adminPassword) {
        Optional<Admin> admin = adminRepository.findById(adminName);
        if (admin.isPresent() && passwordEncoder.matches(adminPassword, admin.get().getAdminPassword())) {
            return admin.get();
        }
        return null;
    }
    @Override
    public Admin updatePassword(String adminName, String newPassword, String secretCode) {
        Optional<Admin> adminOptional = adminRepository.findByAdminName(adminName);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.getSecretCode().equals(secretCode)) {
                admin.setAdminPassword(passwordEncoder.encode(newPassword));
                return adminRepository.save(admin);
            }
        }
        return null;
    }
}