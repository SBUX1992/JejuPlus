package com.green.jejuplus.dto.contents;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReviewDto {
	private Integer reviewStar;
	private Integer reviewId;
	private String username;
	private Integer contentsId;
	private Integer userId;
	private String reviewContent;
	private Timestamp createdAt;
	private Integer reviewRecommend;
}
