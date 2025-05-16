package com.CRM_PROJECT.Cognizant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.entity.SalesOpportunity;
import com.CRM_PROJECT.Cognizant.exception.SalesOpportunityNotFound;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
import com.CRM_PROJECT.Cognizant.service.SalesOpportunityService;
import com.CRM_PROJECT.Cognizant.Request.SalesOpportunityRequest;
//import com.CRM_PROJECT.Cognizant.ServiceImplementations.SalesOpportunityServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity")
@CrossOrigin(origins = "http://localhost:3000")
public class SalesOpportunityController {
    @Autowired
    private SalesOpportunityService salesOpportunityService;
    @Autowired
    private CustomerProfileRepository customerProfileRepository;
    @GetMapping()
    public List<SalesOpportunity> getAllOpportunities() {
        return salesOpportunityService.getAllOpportunities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOpportunity> getOpportunityById(@PathVariable Long id) {
        SalesOpportunity opportunity = salesOpportunityService.getOpportunityById(id)
                .orElseThrow(() -> new SalesOpportunityNotFound("Opportunity not found for this id :: " + id));
        return ResponseEntity.ok(opportunity);
    }

    @PostMapping()
    public SalesOpportunity createOpportunity(@RequestBody SalesOpportunityRequest opportunityRequest) {
        return salesOpportunityService.createOrUpdateOpportunity(opportunityRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOpportunity> updateOpportunity(@PathVariable Long id, @RequestBody SalesOpportunityRequest opportunityRequest) {
        SalesOpportunity opportunity = salesOpportunityService.getOpportunityById(id)
                .orElseThrow(() -> new SalesOpportunityNotFound("Opportunity not found for this id :: " + id));
        CustomerProfile customer = customerProfileRepository.findById(opportunityRequest.getCustomerID())
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + opportunityRequest.getCustomerID()));
        opportunity.setCustomerID(customer);
        opportunity.setSalesStage(opportunityRequest.getSalesStage());
        opportunity.setEstimatedValue(opportunityRequest.getEstimatedValue());
        opportunity.setClosingDate(opportunityRequest.getClosingDate());
        SalesOpportunity updatedOpportunity = salesOpportunityService.createOrUpdateOpportunity(opportunity);
        return ResponseEntity.ok(updatedOpportunity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable Long id) {
        @SuppressWarnings("unused")
        SalesOpportunity opportunity = salesOpportunityService.getOpportunityById(id)
                .orElseThrow(() -> new SalesOpportunityNotFound("Opportunity not found for this id :: " + id));
        salesOpportunityService.deleteOpportunity(id);
        return ResponseEntity.ok("SalesOpportunity with id " + id + " is deleted successfully");
    }
}