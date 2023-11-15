package com.green.jejuplus.dto.schedule;

import java.util.Date;

import lombok.Data;

@Data
public class ScheduleDto {
	
	public Integer userId;
	public String title;
	public Date startDate;
	public Date endDate;
}
