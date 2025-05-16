package com.CRM_PROJECT.Cognizant.service;

import com.CRM_PROJECT.Cognizant.entity.SalesOpportunity;
import com.CRM_PROJECT.Cognizant.Request.SalesOpportunityRequest;

import java.util.List;
import java.util.Optional;

public interface SalesOpportunityService {
    List<SalesOpportunity> getAllOpportunities();
    Optional<SalesOpportunity> getOpportunityById(Long opportunityID);
    SalesOpportunity createOrUpdateOpportunity(SalesOpportunityRequest opportunityRequest);
    SalesOpportunity createOrUpdateOpportunity(SalesOpportunity opportunity);
    void deleteOpportunity(Long opportunityID);
}