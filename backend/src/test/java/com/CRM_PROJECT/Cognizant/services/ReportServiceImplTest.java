package com.CRM_PROJECT.Cognizant.services;

import com.CRM_PROJECT.Cognizant.ServiceImplementations.ReportServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.Report;
import com.CRM_PROJECT.Cognizant.repository.ReportRepository;

import com.CRM_PROJECT.Cognizant.exception.ReportNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    public void testCreateReport() {
        Report report = new Report();
        when(reportRepository.save(report)).thenReturn(report);

        Report result = reportService.createReport(report);

        assertEquals(report, result);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testGetAllReports() {
        Report report1 = new Report();
        Report report2 = new Report();
        List<Report> reports = Arrays.asList(report1, report2);

        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> result = reportService.getAllReports();

        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    public void testGetReportById() {
        Report report = new Report();
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Report result = reportService.getReportById(1L);

        assertEquals(report, result);
        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetReportById_NotFound() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReportNotFoundException.class, () -> {
            reportService.getReportById(1L);
        });

        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateReport() {
        Report report = new Report();
        Report updatedReport = new Report();

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));
        when(reportRepository.save(report)).thenReturn(updatedReport);

        Optional<Report> result = reportService.updateReport(1L, updatedReport);

        assertTrue(result.isPresent());
        assertEquals(updatedReport, result.get());
        verify(reportRepository, times(1)).findById(1L);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    public void testDeleteReport() {
        when(reportRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reportRepository).deleteById(1L);

        reportService.deleteReport(1L);

        verify(reportRepository, times(1)).existsById(1L);
        verify(reportRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteReport_NotFound() {
        when(reportRepository.existsById(1L)).thenReturn(false);

        assertThrows(ReportNotFoundException.class, () -> {
            reportService.deleteReport(1L);
        });

        verify(reportRepository, times(1)).existsById(1L);
    }

    @Test
    public void testGenerateLastYearSalesReport() {
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1000.0);

        String result = reportService.generateLastYearSalesReport();

        assertEquals("Total Sales for Last Year: 1000.0", result);
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    public void testGenerateLastMonthSalesReport() {
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(500.0);

        String result = reportService.generateLastMonthSalesReport();

        assertEquals("Total Sales for Last Month: 500.0", result);
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    public void testGenerateLastWeekSalesReport() {
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(200.0);

        String result = reportService.generateLastWeekSalesReport();

        assertEquals("Total Sales for Last Week: 200.0", result);
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    public void testGenerateCustomerBehaviorReport() {
        Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(new Object[]{new Object[]{"Customer1", 5L, 100.0}}));
    
        String result = reportService.generateCustomerBehaviorReport();
    
        assertTrue(result.contains("Customer: Customer1, Sales Count: 5, Average Sales Value: 100.0"));
        verify(entityManager, times(1)).createQuery(anyString());
        verify(query, times(1)).getResultList();
    } 


@Test
public void testGenerateMarketingCampaignOutcomesReport() {
    Query query = mock(Query.class);
    when(entityManager.createQuery(anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(Arrays.asList(new Object[]{new Object[]{"CampaignType1", 10}}));

    String result = reportService.generateMarketingCampaignOutcomesReport();

    assertTrue(result.contains("CampaignType1: 10"));
    verify(entityManager, times(1)).createQuery(anyString());
    verify(query, times(1)).getResultList();
}
}