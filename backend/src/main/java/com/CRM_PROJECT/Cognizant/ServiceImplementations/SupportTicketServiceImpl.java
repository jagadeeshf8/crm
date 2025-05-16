package com.CRM_PROJECT.Cognizant.ServiceImplementations;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.CRM_PROJECT.Cognizant.entity.SupportTicket;
import com.CRM_PROJECT.Cognizant.repository.SupportTicketRepository;
import com.CRM_PROJECT.Cognizant.service.SupportTicketService;
import com.CRM_PROJECT.Cognizant.Request.SupportTicketRequest;
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
// import lombok.extern.slf4j.Slf4j;
 
@Service
// @Slf4j
public class SupportTicketServiceImpl implements SupportTicketService {
    @Autowired
    private SupportTicketRepository supportTicketRepository;
 
    @Autowired
    private CustomerProfileRepository customerProfileRepository;
 
    private static final List<String> KNOWLEDGE_BASE = Arrays.asList(
            "How to reset your password",
            "How to update your profile information",
            "How to troubleshoot login issues",
            "How to contact customer support");
    @Override
    public List<SupportTicket> getAllTickets() {
        //log.info("Fetching all support tickets");
        return supportTicketRepository.findAll();
    }
    @Override
    public Optional<SupportTicket> getTicketById(Long ticketID) {
       // log.info("Fetching support ticket with ID: {}", ticketID);
        return supportTicketRepository.findById(ticketID);
    }
    //@Override
    public SupportTicket createOrUpdateTicket(SupportTicketRequest ticketRequest) {
       // log.info("Creating or updating support ticket for customer ID: {}", ticketRequest.getCustomerId());
        CustomerProfile customerProfile = customerProfileRepository.findById(ticketRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found for this id :: " + ticketRequest.getCustomerId()));
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomer(customerProfile);
        ticket.setIssueDescription(ticketRequest.getIssueDescription());
        ticket.setAssignedAgent(ticketRequest.getAssignedAgent());
        ticket.setStatus(ticketRequest.getStatus());
        // ticket.setKnowledgeBaseReference(ticketRequest.getKnowledgeBaseReference());
        return supportTicketRepository.save(ticket);
    }
    @Override
    public SupportTicket createOrUpdateTicket(SupportTicket ticket) {
       // log.info("Creating or updating support ticket with ID: {}", ticket.getTicketID());
        return supportTicketRepository.save(ticket);
    }
    @Override
    public void deleteTicket(Long ticketID) {
       // log.info("Deleting support ticket with ID: {}", ticketID);
        supportTicketRepository.deleteById(ticketID);
    }
    @Override
    public List<String> getKnowledgeBase() {
        return KNOWLEDGE_BASE;
    }
    @Override
    public List<String> getKnowledgeBaseSuggestions(String issueDescription) {
        if (issueDescription.toLowerCase().contains("password")) {
            return Arrays.asList("How to reset your password", "How to troubleshoot login issues");
        } else if (issueDescription.toLowerCase().contains("profile")) {
            return Arrays.asList("How to update your profile information");
        } else {
            return Arrays.asList("How to contact customer support");
        }
    }
}