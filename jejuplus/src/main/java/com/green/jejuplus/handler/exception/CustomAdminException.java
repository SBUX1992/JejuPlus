package com.green.jejuplus.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomAdminException extends RuntimeException {

	private HttpStatus status;

	public CustomAdminException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
