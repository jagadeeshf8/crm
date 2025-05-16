package com.CRM_PROJECT.Cognizant.Request;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// import lombok.Data;
 
// @Data
public class SupportTicketRequest {
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
 
    @NotBlank(message = "Issue description cannot be blank")
    private String issueDescription;
 
    @NotBlank(message = "Assigned agent cannot be blank")
    private String assignedAgent;
 
    @NotBlank(message = "Status cannot be blank")
    private String status; // Open/Closed
 
    // private String knowledgeBaseReference; // Optional field for knowledge base
    // reference

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getAssignedAgent() {
        return assignedAgent;
    }

    public void setAssignedAgent(String assignedAgent) {
        this.assignedAgent = assignedAgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}