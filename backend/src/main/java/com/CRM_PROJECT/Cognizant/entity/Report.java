package com.CRM_PROJECT.Cognizant.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @NotBlank(message = "Report type cannot be null or blank")
    private String reportType;

    @NotNull(message = "Generated date cannot be null")
    private Date generatedDate;

    @Lob
    @NotBlank(message = "Data points cannot be null or blank")
    private String dataPoints;

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(Date generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(String dataPoints) {
        this.dataPoints = dataPoints;
    }
}