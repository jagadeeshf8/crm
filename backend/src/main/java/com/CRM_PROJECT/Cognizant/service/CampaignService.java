package com.CRM_PROJECT.Cognizant.service;

import com.CRM_PROJECT.Cognizant.entity.Campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignService {
    List<Campaign> getAllCampaigns();
    Optional<Campaign> getCampaignById(int id);
    Campaign addCampaign(Campaign campaign);
    Campaign updateCampaign(int id, Campaign updatedCampaign);
    boolean deleteCampaign(int id);
}