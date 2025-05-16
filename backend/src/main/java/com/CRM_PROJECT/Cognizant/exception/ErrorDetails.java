package com.CRM_PROJECT.Cognizant.exception;

import java.time.LocalDateTime;
//import java.util.Date;
public class ErrorDetails {
	private LocalDateTime localDateTime;
	private String message;
	private String details;
	public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
		//super();
		this.localDateTime = localDateTime;
		this.message = message;
		this.details = details;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
	
}
	
