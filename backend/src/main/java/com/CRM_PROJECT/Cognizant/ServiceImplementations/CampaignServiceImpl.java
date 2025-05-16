package com.CRM_PROJECT.Cognizant.ServiceImplementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CRM_PROJECT.Cognizant.entity.Campaign;
import com.CRM_PROJECT.Cognizant.repository.CampaignRepository;

import com.CRM_PROJECT.Cognizant.service.CampaignService;

//import lombok.extern.slf4j.Slf4j;

@Service
//@Slf4j
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Override
    public List<Campaign> getAllCampaigns() {
     //   log.info("Fetching all campaigns");
        return campaignRepository.findAll();
    }
    @Override
    public Optional<Campaign> getCampaignById(int id) {
       // log.info("Fetching campaign with ID: {}", id);
        return campaignRepository.findById(id);
    }
    @Override
    public Campaign addCampaign(Campaign campaign) {
      //  log.info("Adding new campaign with name: {}", campaign.getName());
        return campaignRepository.save(campaign);
    }
    @Override
    public Campaign updateCampaign(int id, Campaign updatedCampaign) {
      //  log.info("Updating campaign with ID: {}", id);
        Campaign campaign = campaignRepository.findById(id).orElse(null);

        if (campaign != null) {
            campaign.setName(updatedCampaign.getName());
            campaign.setStartDate(updatedCampaign.getStartDate());
            campaign.setEndDate(updatedCampaign.getEndDate());
            campaign.setType(updatedCampaign.getType());
            return campaignRepository.save(campaign);
        }

        return null;
    }
    @Override
    public boolean deleteCampaign(int id) {
      //  log.info("Deleting campaign with ID: {}", id);
        Campaign campaign = campaignRepository.findById(id).orElse(null);

        if (campaign != null) {
            campaignRepository.delete(campaign);
            return true;
        }

        return false;
    }
}