package com.green.jejuplus.service.contents;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.green.jejuplus.handler.exception.CustomRestfulException;
import com.green.jejuplus.repository.interfaces.RecommendedRepository;
import com.green.jejuplus.repository.model.Recommended;

@Service
public class RecommendedService {
	
	@Autowired
	private RecommendedRepository recommendedRepository;
	
	// 추천 확인
	@Transactional
	public boolean selectRecommended(Integer userId, Integer contentsId) {
		if (recommendedRepository.selectRecommended(Recommended.builder().userId(userId).contentsId(contentsId).build()) != null) {
			return true;
		}
		return false;
	}
	
	// 추천
	@Transactional
	public ResponseEntity<Integer> insertRecommended(Integer userId, Integer contentsId) {
		int result = recommendedRepository.insertRecommended(Recommended.builder().userId(userId).contentsId(contentsId).build());
		if (result != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 추천 취소
	public ResponseEntity<Integer> deleteRecommended(Integer userId, Integer contentsId) {
		int result = recommendedRepository.deleteRecommended(Recommended.builder().userId(userId).contentsId(contentsId).build());
		if (result != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
