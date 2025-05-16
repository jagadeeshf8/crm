package com.CRM_PROJECT.Cognizant.controller;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.service.CustomerProfileService;

import jakarta.validation.Valid;
 
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customers")
@Validated
public class CustomerProfileController {
 
    @Autowired
    private CustomerProfileService service;
 
    @GetMapping
    public List<CustomerProfile> getAllCustomers() {
        return service.getAllCustomers()
                      .stream()
                      .collect(Collectors.toList());
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfile> getCustomerById(@PathVariable Long id) {
        Optional<CustomerProfile> customer = Optional.ofNullable(service.getCustomerById(id));
        return customer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @PostMapping
    public ResponseEntity<CustomerProfile> addCustomer(@Valid @RequestBody CustomerProfile customer) {
        return Optional.ofNullable(service.addCustomer(customer))
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.badRequest().build());
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<CustomerProfile> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerProfile updatedCustomer) {
        return Optional.ofNullable(service.updateCustomer(id, updatedCustomer))
                       .map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        Optional<CustomerProfile> customer = Optional.ofNullable(service.getCustomerById(id));
        if (customer.isPresent()) {
            service.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @GetMapping("/categorize/region/{region}")
    public List<CustomerProfile> categorizeByRegion(@PathVariable String region) {
        return service.categorizeByRegion(region);
    }
 
    @GetMapping("/categorize/interest/{interest}")
    public List<CustomerProfile> categorizeByInterest(@PathVariable String interest) {
        return service.categorizeByInterest(interest);
    }
 
    @GetMapping("/categorize/habit/{habit}")
    public List<CustomerProfile> categorizeByPurchasingHabit(@PathVariable String habit) {
        return service.categorizeByPurchasingHabit(habit);
    }
}