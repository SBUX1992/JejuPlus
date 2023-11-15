package com.green.jejuplus.repository.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
	private Integer reviewStar;
	private Integer reviewId;
	private Integer contentsId;
	private Integer userId;
	private String reviewContent;
	private Timestamp createdAt;
	private Integer reviewRecommend;
}
