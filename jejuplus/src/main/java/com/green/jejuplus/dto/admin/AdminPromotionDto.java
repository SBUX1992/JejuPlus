package com.green.jejuplus.dto.admin;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AdminPromotionDto {
	int promotionId;
    String title;
    String introduce;
    String content;
    Date startDate;
    Date endDate;
    String imageUrl;

}
