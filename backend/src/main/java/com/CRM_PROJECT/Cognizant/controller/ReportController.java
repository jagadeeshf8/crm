package com.CRM_PROJECT.Cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CRM_PROJECT.Cognizant.entity.Report;
import com.CRM_PROJECT.Cognizant.exception.ReportNotFoundException;
import com.CRM_PROJECT.Cognizant.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    @Autowired
    private ReportService reportService;
    
    @PostMapping
    public Report createReport(@Valid @RequestBody Report report) 
    {
        return reportService.createReport(report);
    }

    @GetMapping
    public List<Report> getAllReports() 
    {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) 
    {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReportById(@PathVariable Long id, @Valid @RequestBody Report reportDetails)
    {
        Report updatedReport = reportService.updateReport(id, reportDetails)
        .orElseThrow(() -> new ReportNotFoundException("Report not present with id: " + id));
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report with id " + id + " is deleted successfully");
    }


    @GetMapping("/sales-performance/last-year")
    public ResponseEntity<String> getLastYearSalesReport()
    {
        String report = reportService.generateLastYearSalesReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/sales-performance/last-month")
    public ResponseEntity<String> getLastMonthSalesReport()
     {
        String report = reportService.generateLastMonthSalesReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/sales-performance/last-week")
    public ResponseEntity<String> getLastWeekSalesReport() {
        String report = reportService.generateLastWeekSalesReport();
        return ResponseEntity.ok(report);
    }
    @GetMapping("/sales-performance/last-year-average-per-month")
public ResponseEntity<String> getLastYearAverageSalesPerMonth() {
    String report = reportService.generateLastYearAverageSalesPerMonth();
    return ResponseEntity.ok(report);
}

@GetMapping("/sales-performance/last-year-average-per-week")
public ResponseEntity<String> getLastYearAverageSalesPerWeek() {
    String report = reportService.generateLastYearAverageSalesPerWeek();
    return ResponseEntity.ok(report);
}

    @GetMapping("/customer-behavior")
    public ResponseEntity<String> getCustomerBehaviorReport() {
        String report = reportService.generateCustomerBehaviorReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/marketing-campaign-outcomes")
    public ResponseEntity<String> getMarketingCampaignOutcomesReport() {
        String report = reportService.generateMarketingCampaignOutcomesReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/refresh")
    public ResponseEntity<List<Report>> refreshReports() {
        List<Report> reports = reportService.refreshReports();
        return ResponseEntity.ok(reports);
    }
    @GetMapping("/categorize/{reportType}")
    public ResponseEntity<List<Report>> categorizeByReportType(@PathVariable String reportType) {
    List<Report> categorizedReports = reportService.categorizeByReportType(reportType);
    return ResponseEntity.ok(categorizedReports);
}
}


