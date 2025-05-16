package com.CRM_PROJECT.Cognizant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Entity
public class Admin {
    @Id
    String adminName;
    String adminPassword;   
    private String secretCode;
    private String role;

    public Admin(String adminName, String adminPassword, String role, String secretCode) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.role = role;
        this.secretCode = secretCode;
    }

    public Admin() {
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
