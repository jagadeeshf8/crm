package com.CRM_PROJECT.Cognizant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
 
 
@Entity
public class SalesOpportunity {
   
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long OpportunityID;
	
	@ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerProfile customerID;
	
    private String salesStage;
	
    private double estimatedValue;
	
    private String closingDate;

    public SalesOpportunity() {
    	
    }
   
 
	public long getOpportunityID() {
    	return OpportunityID;
    }
    
    public void setOpportunityID(long OpportunityID) {
    	this.OpportunityID = OpportunityID;
    }
 
	public CustomerProfile getCustomerID() {
        return customerID;
    }
 
 
	public void setCustomerID(CustomerProfile customerID) {
        this.customerID = customerID;
    }
 
    public String getSalesStage() {
        return salesStage;
    }
 
    public void setSalesStage(String salesStage) {
        this.salesStage = salesStage;
    }
 
    public double getEstimatedValue() {
        return estimatedValue;
    }
 
    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }
 
    public String getClosingDate() {
        return closingDate;
    }
 
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }
}
