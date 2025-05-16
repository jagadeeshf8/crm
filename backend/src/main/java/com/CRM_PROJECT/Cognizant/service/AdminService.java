package com.CRM_PROJECT.Cognizant.service;

import com.CRM_PROJECT.Cognizant.entity.Admin;

public interface AdminService {
    Admin register(Admin admin);
    Admin login(String adminName, String adminPassword);
    Admin updatePassword(String adminName, String newPassword, String secretCode);
}