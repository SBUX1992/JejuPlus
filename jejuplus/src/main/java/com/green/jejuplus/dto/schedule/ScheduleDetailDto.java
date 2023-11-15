package com.green.jejuplus.dto.schedule;

import lombok.Data;

@Data
public class ScheduleDetailDto {

	Integer contentsId;
	Integer scheduleId;
	String itemMemo;
	Integer itemDay;
	Integer itemSequence;
}
