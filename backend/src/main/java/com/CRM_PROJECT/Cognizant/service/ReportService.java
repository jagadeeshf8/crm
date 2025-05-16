package com.CRM_PROJECT.Cognizant.service;

import com.CRM_PROJECT.Cognizant.entity.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport(Report report);
    List<Report> getAllReports();
    Report getReportById(Long id);
    Optional<Report> updateReport(Long id, Report reportDetails);
    void deleteReport(Long id);
    String generateLastYearSalesReport();
    String generateLastMonthSalesReport();
    String generateLastWeekSalesReport();
    String generateCustomerBehaviorReport();
    String generateMarketingCampaignOutcomesReport();
    String generateLastYearAverageSalesPerMonth();
    String generateLastYearAverageSalesPerWeek();
    List<Report> refreshReports();
    List<Report> categorizeByReportType(String reportType);
}