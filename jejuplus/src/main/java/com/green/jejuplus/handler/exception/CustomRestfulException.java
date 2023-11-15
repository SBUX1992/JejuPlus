package com.green.jejuplus.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomRestfulException extends RuntimeException {
	private HttpStatus staus;
	
	public CustomRestfulException(String message, HttpStatus httpStatus) {
		super(message);
		this.staus = httpStatus;
	}
}