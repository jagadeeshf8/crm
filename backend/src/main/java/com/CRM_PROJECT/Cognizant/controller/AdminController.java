package com.CRM_PROJECT.Cognizant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.CRM_PROJECT.Cognizant.entity.Admin;
import com.CRM_PROJECT.Cognizant.Request.AdminLoginRequest;
import com.CRM_PROJECT.Cognizant.Request.AdminUpdateRequest;
//import com.CRM_PROJECT.Cognizant.ServiceImplementations.AdminServiceImpl;
import com.CRM_PROJECT.Cognizant.security.JwtUtil;
import com.CRM_PROJECT.Cognizant.service.AdminService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        String password = admin.getAdminPassword();
    if (!isValidPassword(password)) {
        return ResponseEntity.status(400).body(Map.of("error", "Password must be at least 8 characters long, contain at least one special character, and at least two numbers."));
    }
        Admin registeredAdmin = adminService.register(admin);
        return ResponseEntity.ok(registeredAdmin);
    }

    @PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody AdminLoginRequest loginRequest) {
    String password = loginRequest.getAdminPassword();
    if (!isValidPassword(password)) {
        return ResponseEntity.status(400).body(Map.of("error", "Password must be at least 8 characters long, contain at least one special character, and at least two numbers."));
    }

    Admin admin = adminService.login(loginRequest.getAdminName(), password);
    if (admin != null) {
        String token = jwtUtil.generateToken(admin.getAdminName(), admin.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", admin.getRole());
        return ResponseEntity.ok(response);
    } else {
        return ResponseEntity.status(401).body(Map.of("error", "Login failed. Please retry with correct details."));
    }
}

private boolean isValidPassword(String password) {
    if (password.length() < 8) {
        return false;
    }

    int charCount = 0, specialCharCount = 0, numberCount = 0;
    for (char ch : password.toCharArray()) {
        if (Character.isLetter(ch)) {
            charCount++;
        } else if (Character.isDigit(ch)) {
            numberCount++;
        } else {
            specialCharCount++;
        }
    }

    return charCount >= 1 && specialCharCount >= 1 && numberCount >= 2;
}


    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody AdminUpdateRequest passwordUpdateRequest) {
        
        Admin updatedAdmin = adminService.updatePassword(passwordUpdateRequest.getAdminName(), passwordUpdateRequest.getAdminPassword(), passwordUpdateRequest.getSecretCode());
        if (updatedAdmin != null) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(401).body("Password update failed. Please retry with correct details.");
        }
    }
}