package com.green.jejuplus.repository.model;



import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
	

	int promotionId;
	String title;
	String introduce;
	String content;
	Timestamp  startDate;
	Timestamp  endDate;
	

	
}
