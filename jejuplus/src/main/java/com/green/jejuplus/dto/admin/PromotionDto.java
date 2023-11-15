package com.green.jejuplus.dto.admin;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PromotionDto {
	int promotionId;
	String title;
	String introduce;
	String content;
	String start_date;
	String end_date;
}
