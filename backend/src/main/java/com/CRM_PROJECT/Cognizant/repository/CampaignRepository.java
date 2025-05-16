package com.CRM_PROJECT.Cognizant.repository;

import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.CRM_PROJECT.Cognizant.entity.Campaign;

 
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Optional<Campaign> findById(int id);
}
