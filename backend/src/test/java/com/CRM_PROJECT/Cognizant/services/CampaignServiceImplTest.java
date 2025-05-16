package com.CRM_PROJECT.Cognizant.services;

import com.CRM_PROJECT.Cognizant.entity.Campaign;
import com.CRM_PROJECT.Cognizant.repository.CampaignRepository;
//import com.CRM_PROJECT.Cognizant.service.impl.CampaignServiceImpl;
import com.CRM_PROJECT.Cognizant.ServiceImplementations.CampaignServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Test
    public void testGetAllCampaigns() {
        Campaign campaign1 = new Campaign();
        Campaign campaign2 = new Campaign();
        List<Campaign> campaigns = Arrays.asList(campaign1, campaign2);

        when(campaignRepository.findAll()).thenReturn(campaigns);

        List<Campaign> result = campaignService.getAllCampaigns();

        assertEquals(2, result.size());
        verify(campaignRepository, times(1)).findAll();
    }

    @Test
    public void testGetCampaignById() {
        Campaign campaign = new Campaign();
        when(campaignRepository.findById(1)).thenReturn(Optional.of(campaign));

        Optional<Campaign> result = campaignService.getCampaignById(1);

        assertTrue(result.isPresent());
        assertEquals(campaign, result.get());
        verify(campaignRepository, times(1)).findById(1);
    }

    @Test
    public void testAddCampaign() {
        Campaign campaign = new Campaign();
        when(campaignRepository.save(campaign)).thenReturn(campaign);

        Campaign result = campaignService.addCampaign(campaign);

        assertEquals(campaign, result);
        verify(campaignRepository, times(1)).save(campaign);
    }

    @Test
    public void testUpdateCampaign() {
        Campaign campaign = new Campaign();
        Campaign updatedCampaign = new Campaign();

        when(campaignRepository.findById(1)).thenReturn(Optional.of(campaign));
        when(campaignRepository.save(campaign)).thenReturn(updatedCampaign);

        Campaign result = campaignService.updateCampaign(1, updatedCampaign);

        assertEquals(updatedCampaign, result);
        verify(campaignRepository, times(1)).findById(1);
        verify(campaignRepository, times(1)).save(campaign);
    }

    @Test
    public void testDeleteCampaign() {
        Campaign campaign = new Campaign();

        when(campaignRepository.findById(1)).thenReturn(Optional.of(campaign));
        doNothing().when(campaignRepository).delete(campaign);

        boolean result = campaignService.deleteCampaign(1);

        assertTrue(result);
        verify(campaignRepository, times(1)).findById(1);
        verify(campaignRepository, times(1)).delete(campaign);
    }
}