package com.green.jejuplus.controller.contents;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.jejuplus.dto.contents.FavoriteRequestDto;
import com.green.jejuplus.dto.contents.RecommendedDto;
import com.green.jejuplus.dto.contents.ReviewDto;
import com.green.jejuplus.handler.exception.CustomRestfulException;
import com.green.jejuplus.handler.exception.UnAuthorizedException;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.contents.FavoriteService;
import com.green.jejuplus.service.contents.RecommendedService;
import com.green.jejuplus.service.contents.ReviewService;
import com.green.jejuplus.util.Define;



@RestController
@RequestMapping("/api")
public class ContentsApiController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private RecommendedService recommendedService;
	
	@Autowired
	private HttpSession session;
	
	// 찜 등록
	@PostMapping("/contents/{contentsLabel}/{contentsId}/favorite")
	public ResponseEntity<Integer> insertFavorite(@PathVariable String contentsLabel, @PathVariable Integer contentsId, @RequestBody FavoriteRequestDto favoriteRequestDto) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);	
		ResponseEntity<Integer> response = favoriteService.insertFavorite(principal.getUserId(), contentsId);

		return response;
	}
	
	// 찜 삭제
	@DeleteMapping("/contents/{contentsLabel}/{contentsId}/unfavorite")
	public ResponseEntity<Integer> deleteFavorite(@PathVariable String contentsLabel, @PathVariable Integer contentsId) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		ResponseEntity<Integer> response = favoriteService.deleteFavorite(principal.getUserId(), contentsId);

		return response;		
	}
	
	// 추천
	@PostMapping("/contents/{contentsLabel}/{contentsId}/recommended")
	public ResponseEntity<Integer> insertRecommended(@PathVariable String contentsLabel, @PathVariable Integer contentsId, @RequestBody RecommendedDto recommendedDto) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);

		ResponseEntity<Integer> response = recommendedService.insertRecommended(principal.getUserId(), contentsId);
		
		return response;
	}
	
	// 추천 취소
	@DeleteMapping("/contents/{contentsLabel}/{contentsId}/unrecommended")
	public ResponseEntity<Integer> deleteRecommended(@PathVariable String contentsLabel, @PathVariable Integer contentsId) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		ResponseEntity<Integer> response = recommendedService.deleteRecommended(principal.getUserId(), contentsId);

		return response;		
	}
	
	// 리뷰 등록
	@PostMapping("/contents/{contentsLabel}/{contentsId}/review")
	public ResponseEntity<ReviewDto> insertReview(@PathVariable String contentsLabel, @PathVariable Integer contentsId, @RequestBody ReviewDto reviewDto) {	
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		if (principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(reviewDto);
		} else {
			boolean isReview = reviewService.selectReview(principal.getUserId(),contentsId);
			if (isReview == false) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reviewDto);
				
			} else {
				reviewService.insertReview(principal.getUserId(), reviewDto);
				return ResponseEntity.status(HttpStatus.OK).body(reviewDto);	
			}
		}
	}
	
	// 리뷰 수정
	@PatchMapping("/contents/{contentsLabel}/{contentsId}/review")
	public ResponseEntity<ReviewDto> updateReview(@PathVariable String contentsLabel, @PathVariable Integer contentsId, @RequestBody ReviewDto reviewDto) {	
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		reviewService.updateReview(principal.getUserId(), reviewDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
	}
	
	
	// 리뷰 삭제 
	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<Integer> delete(@PathVariable Integer reviewId) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		reviewService.deleteReview(reviewId);
		
		return ResponseEntity.status(HttpStatus.OK).body(reviewId);	
	}
}
