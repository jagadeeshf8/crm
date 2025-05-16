package com.CRM_PROJECT.Cognizant.ServiceImplementations;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.exception.CustomerNotFoundException;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
import com.CRM_PROJECT.Cognizant.service.CustomerProfileService;
 
import java.util.List;
import java.util.stream.Collectors;
 
// import lombok.extern.slf4j.Slf4j;
 
@Service
// @Slf4j
public class CustomerProfileServiceImpl implements CustomerProfileService {
 
    @Autowired
    private CustomerProfileRepository repository;
 
    @Override
    public List<CustomerProfile> getAllCustomers() {
       // log.info("Fetching all customer profiles");
        return repository.findAll()
                         .stream()
                         .collect(Collectors.toList());
    }
 
    @Override
    public CustomerProfile getCustomerById(Long id) {
        // log.info("Fetching customer profile with ID: {}", id);
        return repository.findById(id)
                         .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }
 
    @Override
    public CustomerProfile addCustomer(CustomerProfile customer) {
        // log.info("Adding new customer profile with name: {}", customer.getName());
        return repository.save(customer);
    }
 
    @Override
    public CustomerProfile updateCustomer(Long id, CustomerProfile updatedCustomer) {
        // log.info("Updating customer profile with ID: {}", id);
        return repository.findById(id)
                         .map(existingCustomer -> {
                             existingCustomer.setName(updatedCustomer.getName());
                             existingCustomer.setContactInfo(updatedCustomer.getContactInfo());
                             existingCustomer.setPurchaseHistory(updatedCustomer.getPurchaseHistory());
                             existingCustomer.setSegmentationData(updatedCustomer.getSegmentationData());
                             return repository.save(existingCustomer);
                         })
                         .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
    }
 
    @Override
    public void deleteCustomer(Long id) {
        // log.info("Deleting customer profile with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        repository.deleteById(id);
    }
 
    @Override
    public List<CustomerProfile> categorizeByRegion(String region) {
        // log.info("Categorizing customers by region: {}", region);
        return repository.findAll()
                         .stream()
                         .filter(customer -> customer.getSegmentationData().contains(region))
                         .collect(Collectors.toList());
    }
 
    @Override
    public List<CustomerProfile> categorizeByInterest(String interest) {
        // log.info("Categorizing customers by interest: {}", interest);
        return repository.findAll()
                         .stream()
                         .filter(customer -> customer.getSegmentationData().contains(interest))
                         .collect(Collectors.toList());
    }
 
    @Override
    public List<CustomerProfile> categorizeByPurchasingHabit(String habit) {
        // log.info("Categorizing customers by purchasing habit: {}", habit);
        return repository.findAll()
                         .stream()
                         .filter(customer -> customer.getPurchaseHistory().contains(habit))
                         .collect(Collectors.toList());
    }
}