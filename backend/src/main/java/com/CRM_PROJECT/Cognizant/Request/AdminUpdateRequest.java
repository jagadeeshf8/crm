

package com.CRM_PROJECT.Cognizant.Request;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Setter
// @Getter
// @NoArgsConstructor
// @AllArgsConstructor
public class AdminUpdateRequest {
    private String adminName;
    private String adminPassword;
    private String secretCode; // Corrected field name

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
}