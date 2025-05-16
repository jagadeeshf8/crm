package com.CRM_PROJECT.Cognizant.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
 
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SupportTicketNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
 
    public SupportTicketNotFoundException(String message) {
        super(message);
    }
}