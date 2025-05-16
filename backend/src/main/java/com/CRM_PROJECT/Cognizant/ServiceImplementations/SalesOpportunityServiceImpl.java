package com.CRM_PROJECT.Cognizant.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CRM_PROJECT.Cognizant.entity.SalesOpportunity;
import com.CRM_PROJECT.Cognizant.repository.SalesOpportunityRepository;
import com.CRM_PROJECT.Cognizant.service.SalesOpportunityService;
import com.CRM_PROJECT.Cognizant.Request.SalesOpportunityRequest;
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;

import java.util.List;
import java.util.Optional;

// import lombok.extern.slf4j.Slf4j;

@Service
// @Slf4j
public class SalesOpportunityServiceImpl  implements SalesOpportunityService {
    @Autowired
    private SalesOpportunityRepository salesOpportunityRepository;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;
    @Override
    public List<SalesOpportunity> getAllOpportunities() {
       //log.info("Fetching all sales opportunities");
        return salesOpportunityRepository.findAll();
    }
    @Override
    public Optional<SalesOpportunity> getOpportunityById(Long opportunityID) {
        //log.info("Fetching sales opportunity with ID: {}", opportunityID);
        return salesOpportunityRepository.findById(opportunityID);
    }
    @Override
    public SalesOpportunity createOrUpdateOpportunity(SalesOpportunityRequest opportunityRequest) {
        //log.info("Creating or updating sales opportunity for customer ID: {}", opportunityRequest.getCustomerID());
        SalesOpportunity opportunity = new SalesOpportunity();
        CustomerProfile customer = customerProfileRepository.findById(opportunityRequest.getCustomerID())
                .orElseThrow(() -> new RuntimeException("Customer not found for this id :: " + opportunityRequest.getCustomerID()));
        opportunity.setCustomerID(customer);
        opportunity.setSalesStage(opportunityRequest.getSalesStage());
        opportunity.setEstimatedValue(opportunityRequest.getEstimatedValue());
        opportunity.setClosingDate(opportunityRequest.getClosingDate());
        return salesOpportunityRepository.save(opportunity);
    }
    @Override
    public SalesOpportunity createOrUpdateOpportunity(SalesOpportunity opportunity) {
        //log.info("Creating or updating sales opportunity with ID: {}", opportunity.getOpportunityID());
        return salesOpportunityRepository.save(opportunity);
    }
    @Override
    public void deleteOpportunity(Long opportunityID) {
        //log.info("Deleting sales opportunity with ID: {}", opportunityID);
        salesOpportunityRepository.deleteById(opportunityID);
    }
}