package com.CRM_PROJECT.Cognizant.entity;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;
 
@Entity
@Table(name = "CustomerProfile")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class CustomerProfile {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Long customerID;
 
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(name = "Name", nullable = false)
    private String name;
 
    @NotNull(message = "Contact info cannot be null")
    @Email(message = "Contact info must be a valid email address")
    @Column(name = "ContactInfo", nullable = false)
    private String contactInfo;
 
    @Column(name = "PurchaseHistory")
    private String purchaseHistory;
 
    @Column(name = "SegmentationData")
    private String segmentationData;

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(String purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public String getSegmentationData() {
        return segmentationData;
    }

    public void setSegmentationData(String segmentationData) {
        this.segmentationData = segmentationData;
    }
}