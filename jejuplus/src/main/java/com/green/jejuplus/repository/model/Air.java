package com.green.jejuplus.repository.model;

import lombok.Data;

@Data
public class Air {
	private int airId;
	private int userId;
	private int paymentId;
	
	private String airlineNm;
	private String depPlandTime;
	private String arrPlandTime;
	private String arrAirportNm;
	private String depAirportNm;
	
	private String depPlandTime2;
	private String arrPlandTime2;
	private String arrAirportNm2;
	private String depAirportNm2;
}
