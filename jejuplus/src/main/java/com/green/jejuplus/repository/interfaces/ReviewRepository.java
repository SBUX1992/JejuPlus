package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.dto.contents.ReviewDto;
import com.green.jejuplus.repository.model.Review;

@Mapper
public interface ReviewRepository {

	List<ReviewDto> findReviewList(int contentsId);

	int insertReview(ReviewDto reviewDto);
	
	int updateReview(ReviewDto reviewDto);

	void deleteReview(Integer reviewId);

	String findUsernameByUserId(Integer userId);

	Review selectReview(Review review);

}
