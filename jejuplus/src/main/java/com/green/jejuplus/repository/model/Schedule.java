package com.green.jejuplus.repository.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Schedule {
	
	private Integer scheduleId;
	private String title;
	private Integer userId;
	private Date startDate;
	private Date endDate;

}
