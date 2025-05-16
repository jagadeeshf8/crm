// package com.CRM_PROJECT.Cognizant.entity;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "support_ticket")
// public class SupportTicket {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long ticketID;

//     @ManyToOne
//     @JoinColumn(name = "customerId")
//     private CustomerProfile customer;

//     @Column(nullable = false)
//     private String issueDescription;

//     @Column(nullable = false)
//     private String assignedAgent;

//     @Column(nullable = false)
//     private String status; // Open/Closed

//     // Getters and setters
//     public Long getTicketID() {
//         return ticketID;
//     }

//     public void setTicketID(Long ticketID) {
//         this.ticketID = ticketID;
//     }

//     public CustomerProfile getCustomer() {
//         return customer;
//     }

//     public void setCustomer(CustomerProfile customer) {
//         this.customer = customer;
//     }

//     public String getIssueDescription() {
//         return issueDescription;
//     }

//     public void setIssueDescription(String issueDescription) {
//         this.issueDescription = issueDescription;
//     }

//     public String getAssignedAgent() {
//         return assignedAgent;
//     }

//     public void setAssignedAgent(String assignedAgent) {
//         this.assignedAgent = assignedAgent;
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }
// }
package com.CRM_PROJECT.Cognizant.entity;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// import lombok.Data;
 
// import java.time.LocalDateTime;
 
@Entity
@Table(name = "support_ticket")
// @Data
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketID;
 
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @NotNull(message = "Customer cannot be null")
    private CustomerProfile customer;
 
    @NotBlank(message = "Issue description cannot be blank")
    @Column(nullable = false)
    private String issueDescription;
 
    @NotBlank(message = "Assigned agent cannot be blank")
    @Column(nullable = false)
    private String assignedAgent;
 
    @NotBlank(message = "Status cannot be blank")
    @Column(nullable = false)
    private String status; // Open/Closed

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public CustomerProfile getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerProfile customer) {
        this.customer = customer;
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