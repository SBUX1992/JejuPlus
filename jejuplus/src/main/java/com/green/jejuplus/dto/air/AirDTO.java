package com.green.jejuplus.dto.air;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AirDTO {
	
	private int userId;
	private int paymentId;
	
	private String airlineNm;	// 항공사 명
	
	private String depPlandTime; // 출발 일자 및 시간
    private String arrPlandTime; // 도착 일자 및 시간
    private String depAirportNm; // 출발 공항 이름
    private String arrAirportNm; // 도착 공항 이름
    
    private String depPlandTime2; // 출발 일자 및 시간
    private String arrPlandTime2; // 도착 일자 및 시간
    private String depAirportNm2; // 출발 공항 이름
    private String arrAirportNm2; // 도착 공항 이름
    
    private int depPrice;
    private int arrPrice;
}
