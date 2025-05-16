package com.CRM_PROJECT.Cognizant.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Global_Exception_handling {

	@ExceptionHandler(ReportNotFoundException.class)
	public ResponseEntity<?> handler(ReportNotFoundException p)
	{
		ErrorDetails product=new ErrorDetails(LocalDateTime.now(),p.getMessage(),"data is not present");
		return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handler(CustomerNotFoundException p)
	{
		ErrorDetails product=new ErrorDetails(LocalDateTime.now(),p.getMessage(),"data is not present");
		return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
	}	
	@ExceptionHandler(SupportTicketNotFoundException.class)
	public ResponseEntity<?> handler(SupportTicketNotFoundException p)
	{
		ErrorDetails product=new ErrorDetails(LocalDateTime.now(),p.getMessage(),"data is not present");
		return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CampaignNotFoundException.class)
	public ResponseEntity<?> handler(CampaignNotFoundException p)
	{
		ErrorDetails product=new ErrorDetails(LocalDateTime.now(),p.getMessage(),"data is not present");
		return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(SalesOpportunityNotFound.class)
	public ResponseEntity<?> handler(SalesOpportunityNotFound p)
	{
		ErrorDetails product=new ErrorDetails(LocalDateTime.now(),p.getMessage(),"data is not present");
		return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
