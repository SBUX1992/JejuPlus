package com.green.jejuplus.dto.schedule;

import lombok.Data;

@Data
public class ScheduleItemDto {
	
	Integer contentsId;
	String title;
	String region1;
	String region2;
	String contentsLabel;
	String itemMemo;
	String latitude;
	String longitude;
}
