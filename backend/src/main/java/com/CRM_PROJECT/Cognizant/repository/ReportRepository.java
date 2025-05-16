package com.CRM_PROJECT.Cognizant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.CRM_PROJECT.Cognizant.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
