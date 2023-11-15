package com.green.jejuplus.service.contents;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.jejuplus.dto.contents.ReviewDto;
import com.green.jejuplus.repository.interfaces.ReviewRepository;
import com.green.jejuplus.repository.model.Review;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	
	public List<ReviewDto> showReview(int contentsId) {
		List<ReviewDto> review = reviewRepository.findReviewList(contentsId);
		for (ReviewDto dto : review) {
			dto.setUsername(reviewRepository.findUsernameByUserId(dto.getUserId()));
		}
		return review;
	}
	
	public void insertReview(Integer userId, ReviewDto reviewDto) {
		ReviewDto dto = reviewDto;
		dto.setUserId(userId);
		reviewRepository.insertReview(dto);
	}

	public void updateReview(Integer userId, ReviewDto reviewDto) {
		ReviewDto dto = reviewDto;
		dto.setUserId(userId);
		reviewRepository.updateReview(dto);
		
	}

	public void deleteReview(Integer reviewId) {
		reviewRepository.deleteReview(reviewId);
	}

	public boolean selectReview(int userId, int contentsId) {
		if (reviewRepository.selectReview(Review.builder().userId(userId).contentsId(contentsId).build()) == null) {
			return true;
		}
		return false;
	}

}
