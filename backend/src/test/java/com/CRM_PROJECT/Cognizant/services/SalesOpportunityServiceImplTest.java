package com.CRM_PROJECT.Cognizant.services;

import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.entity.SalesOpportunity;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
import com.CRM_PROJECT.Cognizant.repository.SalesOpportunityRepository;
import com.CRM_PROJECT.Cognizant.Request.SalesOpportunityRequest;
import com.CRM_PROJECT.Cognizant.ServiceImplementations.SalesOpportunityServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalesOpportunityServiceImplTest {

    @Mock
    private SalesOpportunityRepository salesOpportunityRepository;

    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @InjectMocks
    private SalesOpportunityServiceImpl salesOpportunityService;

    @Test
    public void testGetAllOpportunities() {
        SalesOpportunity opportunity1 = new SalesOpportunity();
        SalesOpportunity opportunity2 = new SalesOpportunity();
        List<SalesOpportunity> opportunities = List.of(opportunity1, opportunity2);

        when(salesOpportunityRepository.findAll()).thenReturn(opportunities);

        List<SalesOpportunity> result = salesOpportunityService.getAllOpportunities();

        assertEquals(2, result.size());
        verify(salesOpportunityRepository, times(1)).findAll();
    }

    @Test
    public void testGetOpportunityById() {
        SalesOpportunity opportunity = new SalesOpportunity();
        when(salesOpportunityRepository.findById(1L)).thenReturn(Optional.of(opportunity));

        Optional<SalesOpportunity> result = salesOpportunityService.getOpportunityById(1L);

        assertTrue(result.isPresent());
        assertEquals(opportunity, result.get());
        verify(salesOpportunityRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateOrUpdateOpportunity() {
        SalesOpportunityRequest request = new SalesOpportunityRequest();
        request.setCustomerID(1L);
        request.setSalesStage("Stage");
        request.setEstimatedValue(1000.0);
        request.setClosingDate("2023-12-31");

        CustomerProfile customer = new CustomerProfile();
        SalesOpportunity opportunity = new SalesOpportunity();
        opportunity.setCustomerID(customer);
        opportunity.setSalesStage(request.getSalesStage());
        opportunity.setEstimatedValue(request.getEstimatedValue());
        opportunity.setClosingDate(request.getClosingDate());

        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(salesOpportunityRepository.save(any(SalesOpportunity.class))).thenReturn(opportunity);

        SalesOpportunity result = salesOpportunityService.createOrUpdateOpportunity(request);

        assertEquals(customer, result.getCustomerID());
        assertEquals(request.getSalesStage(), result.getSalesStage());
        assertEquals(request.getEstimatedValue(), result.getEstimatedValue());
        assertEquals(request.getClosingDate(), result.getClosingDate());
        verify(customerProfileRepository, times(1)).findById(1L);
        verify(salesOpportunityRepository, times(1)).save(any(SalesOpportunity.class));
    }

    @Test
    public void testDeleteOpportunity() {
        doNothing().when(salesOpportunityRepository).deleteById(1L);

        salesOpportunityService.deleteOpportunity(1L);

        verify(salesOpportunityRepository, times(1)).deleteById(1L);
    }
}