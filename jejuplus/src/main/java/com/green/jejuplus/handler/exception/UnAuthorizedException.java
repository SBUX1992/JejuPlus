package com.green.jejuplus.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {
	
	private HttpStatus status;
	
	public UnAuthorizedException(String message, HttpStatus status) {
		super(message);
		this.status = status;
		System.out.println("이셉션");
	}

}
