package com.CRM_PROJECT.Cognizant.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;



public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
}
