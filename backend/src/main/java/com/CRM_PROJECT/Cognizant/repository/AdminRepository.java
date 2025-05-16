package com.CRM_PROJECT.Cognizant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CRM_PROJECT.Cognizant.entity.Admin;
public interface AdminRepository extends JpaRepository<Admin,String> {
    Optional<Admin> findByAdminNameAndAdminPassword(String adminName, String adminPassword);

    Optional<Admin> findByAdminName(String adminName);
     
}
