package com.CRM_PROJECT.Cognizant.services;
 
import com.CRM_PROJECT.Cognizant.ServiceImplementations.SupportTicketServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.SupportTicket;
import com.CRM_PROJECT.Cognizant.repository.SupportTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class)
public class SupportTicketServiceImplTest {
 
    @Mock
    private SupportTicketRepository supportTicketRepository;
 
    @InjectMocks
    private SupportTicketServiceImpl supportTicketService;
 
    @Test
    public void testGetAllTickets() {
        SupportTicket ticket1 = new SupportTicket();
        SupportTicket ticket2 = new SupportTicket();
        List<SupportTicket> tickets = List.of(ticket1, ticket2);
 
        when(supportTicketRepository.findAll()).thenReturn(tickets);
 
        List<SupportTicket> result = supportTicketService.getAllTickets();
 
        assertEquals(2, result.size());
        verify(supportTicketRepository, times(1)).findAll();
    }
 
    @Test
    public void testGetTicketById() {
        SupportTicket ticket = new SupportTicket();
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.of(ticket));
 
        Optional<SupportTicket> result = supportTicketService.getTicketById(1L);
 
        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
        verify(supportTicketRepository, times(1)).findById(1L);
    }
 
    @Test
    public void testCreateOrUpdateTicket() {
        SupportTicket ticket = new SupportTicket();
        ticket.setTicketID(1L);
 
        when(supportTicketRepository.save(ticket)).thenReturn(ticket);
 
        SupportTicket result = supportTicketService.createOrUpdateTicket(ticket);
 
        assertEquals(ticket, result);
        verify(supportTicketRepository, times(1)).save(ticket);
    }
 
    @Test
    public void testDeleteTicket() {
        doNothing().when(supportTicketRepository).deleteById(1L);
 
        supportTicketService.deleteTicket(1L);
 
        verify(supportTicketRepository, times(1)).deleteById(1L);
    }
}