package com.CRM_PROJECT.Cognizant.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
 
//import com.CRM_PROJECT.Cognizant.ServiceImplementations.CampaignServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.Campaign;
import com.CRM_PROJECT.Cognizant.exception.CampaignNotFoundException;
import com.CRM_PROJECT.Cognizant.service.CampaignService;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/campaign")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class CampaignController {
 
    @Autowired
    private CampaignService campaignService;
 
    @GetMapping("/all")
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        try {
            return new ResponseEntity<>(campaignService.getAllCampaigns(), HttpStatus.OK);
        } catch (Exception e) {
            throw new CampaignNotFoundException("Error fetching campaigns: " + e.getMessage());
        }
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable int id) {
        try {
            Campaign campaign = campaignService.getCampaignById(id)
                    .orElseThrow(() -> new CampaignNotFoundException("Campaign not found for this id :: " + id));
            return new ResponseEntity<>(campaign, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CampaignNotFoundException("Error fetching campaign: " + e.getMessage());
        }
    }
 
    @PostMapping("/add")
    public ResponseEntity<Campaign> addCampaign(@Valid @RequestBody Campaign campaign) {
        try {
            return new ResponseEntity<>(campaignService.addCampaign(campaign), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CampaignNotFoundException("Error adding campaign: " + e.getMessage());
        }
    }
 
    @PutMapping("/update/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable int id,@Valid @RequestBody Campaign updatedCampaign) {
        try {
            return new ResponseEntity<>(campaignService.updateCampaign(id, updatedCampaign), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new CampaignNotFoundException("Error updating campaign: " + e.getMessage());
        }
    }
 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable int id) {
        try {
            boolean isDeleted = campaignService.deleteCampaign(id);
            if (isDeleted) {
                return new ResponseEntity<>("Campaign deleted successfully", HttpStatus.OK);
            } else {
                throw new CampaignNotFoundException("Campaign not found for this id :: " + id);
            }
        } catch (CampaignNotFoundException e) {
            throw new CampaignNotFoundException("Error deleting campaign: " + e.getMessage());
        }
    }
}
 