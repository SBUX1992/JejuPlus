package com.green.jejuplus.dto.schedule;

import java.sql.Date;

import lombok.Data;

@Data
public class ScheduleListDto {
	
	private Integer scheduleId;
	private String title;
	private Date startDate;
	private Date endDate;
	private Integer status; // 0 = 여행 중, 1 =여행 전, 2 = 여행 종료 

}
