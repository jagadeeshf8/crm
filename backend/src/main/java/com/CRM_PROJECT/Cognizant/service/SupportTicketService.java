package com.CRM_PROJECT.Cognizant.service;
 
import com.CRM_PROJECT.Cognizant.entity.SupportTicket;
 
import java.util.List;
import java.util.Optional;
 
public interface SupportTicketService {
    List<SupportTicket> getAllTickets();
 
    Optional<SupportTicket> getTicketById(Long id);
 
    SupportTicket createOrUpdateTicket(SupportTicket ticket);
 
    void deleteTicket(Long id);
 
    List<String> getKnowledgeBase();
 
    List<String> getKnowledgeBaseSuggestions(String issueDescription);
}