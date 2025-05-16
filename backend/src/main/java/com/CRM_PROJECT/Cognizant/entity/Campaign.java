package com.CRM_PROJECT.Cognizant.entity;
 
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
// import lombok.*;
 
@Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
@Table(name = "campaign")
public class Campaign {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campaignID;
 
    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Campaign name cannot be blank")
    @Size(min = 3, max = 100, message = "Campaign name must be between 3 and 100 characters")
    private String name;
 
    @Temporal(TemporalType.DATE)
    @Column(name = "StartDate", nullable = false)
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
 
    @Temporal(TemporalType.DATE)
    @Column(name = "EndDate", nullable = false)
    @NotNull(message = "End date cannot be null")
    @FutureOrPresent(message = "End date must be in the present or future")
    private Date endDate;
 
    @Column(name = "Type", nullable = false)
    @NotBlank(message = "Campaign type cannot be blank")
    @Size(max = 50, message = "Campaign type must not exceed 50 characters")
    private String type;

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
 