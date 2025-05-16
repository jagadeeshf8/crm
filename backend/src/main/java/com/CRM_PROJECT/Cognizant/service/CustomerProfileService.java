package com.CRM_PROJECT.Cognizant.service;
 
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
 
import java.util.List;
 
public interface CustomerProfileService {
    List<CustomerProfile> getAllCustomers();
    CustomerProfile getCustomerById(Long id);
    CustomerProfile addCustomer(CustomerProfile customer);
    CustomerProfile updateCustomer(Long id, CustomerProfile updatedCustomer);
    void deleteCustomer(Long id);
 
    // Methods for categorization
    List<CustomerProfile> categorizeByRegion(String region);
    List<CustomerProfile> categorizeByInterest(String interest);
    List<CustomerProfile> categorizeByPurchasingHabit(String habit);
}