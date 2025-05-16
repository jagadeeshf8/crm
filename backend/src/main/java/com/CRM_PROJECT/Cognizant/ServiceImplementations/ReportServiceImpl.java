package com.CRM_PROJECT.Cognizant.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CRM_PROJECT.Cognizant.entity.Report;
import com.CRM_PROJECT.Cognizant.exception.ReportNotFoundException;
import com.CRM_PROJECT.Cognizant.repository.ReportRepository;
import com.CRM_PROJECT.Cognizant.service.ReportService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import lombok.extern.slf4j.Slf4j;
@Service
// @Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
   
    @Override
    public Report createReport(Report report) {
        // log.info("Creating report of type: {}", report.getReportType());
        return reportRepository.save(report);
    }
    @Override
    public List<Report> getAllReports() {
        // log.info("Fetching all reports");
        return reportRepository.findAll();
    }
    
    @Override
    public Report getReportById(Long id) {
        // log.info("Fetching report with ID: {}", id);
        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + id));
    }
    @Override
    public Optional<Report> updateReport(Long id, Report reportDetails) {
        // log.info("Updating report with ID: {}", id);
        return reportRepository.findById(id).map(report -> {
            report.setReportType(reportDetails.getReportType());
            report.setGeneratedDate(reportDetails.getGeneratedDate());
            report.setDataPoints(reportDetails.getDataPoints());
            return reportRepository.save(report);
        });
    }
    @Override
    public void deleteReport(Long id) {
        // log.info("Deleting report with ID: {}", id);
        if (!reportRepository.existsById(id)) {
            throw new ReportNotFoundException("Report not found with id: " + id);
        }
        reportRepository.deleteById(id);
    }
    
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    @Override
    public String generateLastYearSalesReport() {
        // log.info("Generating last year sales report");
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(1);
        LocalDate endDate = now;

        String queryStr = "SELECT SUM(so.estimatedValue) FROM SalesOpportunity so WHERE so.closingDate BETWEEN :startDate AND :endDate";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("startDate", formatDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        query.setParameter("endDate", formatDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        Double totalSales = (Double) query.getSingleResult();
        return "Total Sales for Last Year: " + (totalSales != null ? totalSales : "0");
    }
    @Override
    public String generateLastMonthSalesReport() {
        // log.info("Generating last month sales report");
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(1);
        LocalDate endDate = now;

        String queryStr = "SELECT SUM(so.estimatedValue) FROM SalesOpportunity so WHERE so.closingDate BETWEEN :startDate AND :endDate";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("startDate", formatDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        query.setParameter("endDate", formatDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        Double totalSales = (Double) query.getSingleResult();
        return "Total Sales for Last Month: " + (totalSales != null ? totalSales : "0");
    }
    
    @Override
    public String generateLastWeekSalesReport() {
        // log.info("Generating last week sales report");
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusWeeks(1);
        LocalDate endDate = now;

        String queryStr = "SELECT SUM(so.estimatedValue) FROM SalesOpportunity so WHERE so.closingDate BETWEEN :startDate AND :endDate";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("startDate", formatDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        query.setParameter("endDate", formatDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        Double totalSales = (Double) query.getSingleResult();
        return "Total Sales for Last Week: " + (totalSales != null ? totalSales : "0");
    }
    @Override
    public String generateLastYearAverageSalesPerMonth() {
        // log.info("Generating last year's average sales per month");
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(1);
        LocalDate endDate = now;
    
        String queryStr = "SELECT SUM(so.estimatedValue) / 12 FROM SalesOpportunity so WHERE so.closingDate BETWEEN :startDate AND :endDate";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("startDate", formatDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        query.setParameter("endDate", formatDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        Double averageSales = (Double) query.getSingleResult();
        return "Average Sales Per Month for Last Year: " + String.format("%.2f", (averageSales != null ? averageSales : 0.0));
    }
    @Override
    public String generateLastYearAverageSalesPerWeek() {
        // log.info("Generating last year's average sales per week");
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(1);
        LocalDate endDate = now;
    
        String queryStr = "SELECT SUM(so.estimatedValue) / 52 FROM SalesOpportunity so WHERE so.closingDate BETWEEN :startDate AND :endDate";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("startDate", formatDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        query.setParameter("endDate", formatDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));
        Double averageSales = (Double) query.getSingleResult();
        return "Average Sales Per Week for Last Year: " + String.format("%.2f", (averageSales != null ? averageSales : 0.0));
    }
    @SuppressWarnings("unchecked")
    @Override
    public String generateCustomerBehaviorReport() {
        // log.info("Generating customer behavior report");
        String queryStr = "SELECT cp.name, COUNT(so), AVG(so.estimatedValue) FROM CustomerProfile cp JOIN SalesOpportunity so ON cp.customerID = so.customerID.customerID GROUP BY cp.name";
        Query query = entityManager.createQuery(queryStr);
        List<Object[]> results = query.getResultList();
        StringBuilder report = new StringBuilder("Customer Behavior Report:\n");
        for (Object[] result : results) {
            String customerName = (String) result[0];
            Long salesCount = (Long) result[1];
            Double averageSalesValue = (Double) result[2];
            
            // Format the average sales value to 2 decimal places
            String formattedAverageSalesValue = String.format("%.2f", averageSalesValue);
    
            report.append("Customer: ").append(customerName)
                  .append(", Sales Count: ").append(salesCount)
                  .append(", Average Sales Value: ").append(formattedAverageSalesValue)
                  .append("\n");
        }
        return report.toString();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String generateMarketingCampaignOutcomesReport() {
        // log.info("Generating marketing campaign outcomes report");
        String queryStr = "SELECT c.type, COUNT(c) FROM Campaign c GROUP BY c.type";
        Query query = entityManager.createQuery(queryStr);
        List<Object[]> results = query.getResultList();
        StringBuilder report = new StringBuilder();
        for (Object[] result : results) {
            report.append(result[0]).append(": ").append(result[1]).append("\n");
        }
        return report.toString();
    }
    @Override
    public List<Report> refreshReports() {
        // log.info("Refreshing all reports");
        String lastYearSales = generateLastYearSalesReport();
        String lastMonthSales = generateLastMonthSalesReport();
        String lastWeekSales = generateLastWeekSalesReport();
        String lastYearAverageSalesPerMonth = generateLastYearAverageSalesPerMonth();
        String lastYearAverageSalesPerWeek = generateLastYearAverageSalesPerWeek();
        String customerBehavior = generateCustomerBehaviorReport();
        String marketingOutcomes = generateMarketingCampaignOutcomesReport();
    
        // Create Sales Report
        String salesDataPoints = String.format(
            "{\"lastYearSales\": \"%s\", \"lastMonthSales\": \"%s\", \"lastWeekSales\": \"%s\", \"lastYearAverageSalesPerMonth\": \"%s\", \"lastYearAverageSalesPerWeek\": \"%s\"}",
            lastYearSales, lastMonthSales, lastWeekSales, lastYearAverageSalesPerMonth, lastYearAverageSalesPerWeek
        );
        Report salesReport = new Report();
        salesReport.setReportType("Sales");
        salesReport.setGeneratedDate(new Date());
        salesReport.setDataPoints(salesDataPoints);
        reportRepository.save(salesReport);
    
        // Create Customer Behavior Report
        String customerBehaviorDataPoints = String.format(
            "{\"customerBehavior\": \"%s\"}",
            customerBehavior
        );
        Report customerBehaviorReport = new Report();
        customerBehaviorReport.setReportType("CustomerBehavior");
        customerBehaviorReport.setGeneratedDate(new Date());
        customerBehaviorReport.setDataPoints(customerBehaviorDataPoints);
        reportRepository.save(customerBehaviorReport);
    
        // Create Marketing Outcomes Report
        String marketingOutcomesDataPoints = String.format(
            "{\"marketingOutcomes\": \"%s\"}",
            marketingOutcomes
        );
        Report marketingOutcomesReport = new Report();
        marketingOutcomesReport.setReportType("MarketingOutcomes");
        marketingOutcomesReport.setGeneratedDate(new Date());
        marketingOutcomesReport.setDataPoints(marketingOutcomesDataPoints);
        reportRepository.save(marketingOutcomesReport);
    
        return List.of(salesReport, customerBehaviorReport, marketingOutcomesReport);
    }
    @Override
    public List<Report> categorizeByReportType(String reportType) {
    // log.info("Categorizing reports by report type: {}", reportType);
    return reportRepository.findAll()
                           .stream()
                           .filter(report -> report.getReportType().equalsIgnoreCase(reportType))
                           .collect(Collectors.toList());
    }
}
