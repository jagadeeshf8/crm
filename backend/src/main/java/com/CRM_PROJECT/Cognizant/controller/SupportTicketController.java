// package com.CRM_PROJECT.Cognizant.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.CRM_PROJECT.Cognizant.ServiceImplementations.SupportTicketServiceImpl;
// import com.CRM_PROJECT.Cognizant.entity.SupportTicket;
// import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
// import com.CRM_PROJECT.Cognizant.exception.SupportTicketNotFoundException;
// import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
// import com.CRM_PROJECT.Cognizant.Request.SupportTicketRequest;

// import java.util.List;

// @RestController
// @RequestMapping("/api/ticket")
// public class SupportTicketController {
//     @Autowired
//     private SupportTicketServiceImpl supportTicketService;

//     @Autowired
//     private CustomerProfileRepository customerProfileRepository;

//     @GetMapping()
//     public List<SupportTicket> getAllTickets() {
//         return supportTicketService.getAllTickets();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<SupportTicket> getTicketById(@PathVariable Long id) {
//         SupportTicket ticket = supportTicketService.getTicketById(id)
//                 .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
//         return ResponseEntity.ok(ticket);
//     }

//     @PostMapping()
//     public SupportTicket createTicket(@RequestBody SupportTicketRequest ticketRequest) {
//         CustomerProfile customerProfile = customerProfileRepository.findById(ticketRequest.getCustomerID())
//                 .orElseThrow(() -> new SupportTicketNotFoundException("Customer not found for this id :: " + ticketRequest.getCustomerID()));
//         SupportTicket ticket = new SupportTicket();
//         ticket.setCustomer(customerProfile);
//         ticket.setIssueDescription(ticketRequest.getIssueDescription());
//         ticket.setAssignedAgent(ticketRequest.getAssignedAgent());
//         ticket.setStatus(ticketRequest.getStatus());
//         return supportTicketService.createOrUpdateTicket(ticket);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<SupportTicket> updateTicket(@PathVariable Long id, @RequestBody SupportTicketRequest ticketRequest) {
//         SupportTicket ticket = supportTicketService.getTicketById(id)
//                 .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
//         CustomerProfile customerProfile = customerProfileRepository.findById(ticketRequest.getCustomerID())
//                 .orElseThrow(() -> new SupportTicketNotFoundException("Customer not found for this id :: " + ticketRequest.getCustomerID()));
//         ticket.setCustomer(customerProfile);
//         ticket.setIssueDescription(ticketRequest.getIssueDescription());
//         ticket.setAssignedAgent(ticketRequest.getAssignedAgent());
//         ticket.setStatus(ticketRequest.getStatus());
//         SupportTicket updatedTicket = supportTicketService.createOrUpdateTicket(ticket);
//         return ResponseEntity.ok(updatedTicket);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
//         @SuppressWarnings("unused")
//         SupportTicket ticket = supportTicketService.getTicketById(id)
//                 .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
//         supportTicketService.deleteTicket(id);
//         return ResponseEntity.ok("SupportTicket with id " + id + " is deleted successfully");
//     }
// }
package com.CRM_PROJECT.Cognizant.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
 
//import com.CRM_PROJECT.Cognizant.ServiceImplementations.SupportTicketServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.SupportTicket;
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.exception.SupportTicketNotFoundException;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
import com.CRM_PROJECT.Cognizant.service.SupportTicketService;
import com.CRM_PROJECT.Cognizant.Request.SupportTicketRequest;
 
import java.util.List;
 
import jakarta.validation.Valid;
 
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/ticket")
@Validated
public class SupportTicketController {
    @Autowired
    private SupportTicketService supportTicketService;
 
    @Autowired
    private CustomerProfileRepository customerProfileRepository;
 
    @GetMapping()
    public List<SupportTicket> getAllTickets() {
        return supportTicketService.getAllTickets();
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<SupportTicket> getTicketById(@PathVariable Long id) {
        SupportTicket ticket = supportTicketService.getTicketById(id)
                .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
        return ResponseEntity.ok(ticket);
    }
 
    @PostMapping()
    public SupportTicket createTicket(@Valid @RequestBody SupportTicketRequest ticketRequest) {
        CustomerProfile customerProfile = customerProfileRepository.findById(ticketRequest.getCustomerId())
                .orElseThrow(() -> new SupportTicketNotFoundException(
                        "Customer not found for this id :: " + ticketRequest.getCustomerId()));
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomer(customerProfile);
        ticket.setIssueDescription(ticketRequest.getIssueDescription());
        ticket.setAssignedAgent(ticketRequest.getAssignedAgent());
        ticket.setStatus(ticketRequest.getStatus());
        return supportTicketService.createOrUpdateTicket(ticket);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<SupportTicket> updateTicket(@PathVariable Long id,
            @Valid @RequestBody SupportTicketRequest ticketRequest) {
        SupportTicket existingTicket = supportTicketService.getTicketById(id)
                .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
        CustomerProfile customerProfile = customerProfileRepository.findById(ticketRequest.getCustomerId())
                .orElseThrow(() -> new SupportTicketNotFoundException(
                        "Customer not found for this id :: " + ticketRequest.getCustomerId()));
        existingTicket.setCustomer(customerProfile);
        existingTicket.setIssueDescription(ticketRequest.getIssueDescription());
        existingTicket.setAssignedAgent(ticketRequest.getAssignedAgent());
        existingTicket.setStatus(ticketRequest.getStatus());
        SupportTicket updatedTicket = supportTicketService.createOrUpdateTicket(existingTicket);
        return ResponseEntity.ok(updatedTicket);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        @SuppressWarnings("unused")
        SupportTicket ticket = supportTicketService.getTicketById(id)
                .orElseThrow(() -> new SupportTicketNotFoundException("Ticket not found for this id :: " + id));
        supportTicketService.deleteTicket(id);
        return ResponseEntity.ok("SupportTicket with id " + id + " is deleted successfully");
    }
 
    @GetMapping("/knowledge-base")
    public List<String> getKnowledgeBase() {
        return supportTicketService.getKnowledgeBase();
    }
 
    @GetMapping("/knowledge-base/suggestions")
    public List<String> getKnowledgeBaseSuggestions(@RequestParam String issueDescription) {
        return supportTicketService.getKnowledgeBaseSuggestions(issueDescription);
    }
}