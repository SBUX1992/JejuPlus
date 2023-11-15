package com.green.jejuplus.repository.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Payment {
	
	private int paymentId;
	private int userId;
	private Timestamp createdAt;
	private String pgTid;
}
