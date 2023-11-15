package com.green.jejuplus.service.contents;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.green.jejuplus.handler.exception.CustomRestfulException;
import com.green.jejuplus.repository.interfaces.FavoriteRepository;
import com.green.jejuplus.repository.model.Favorite;



@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteRepository favoriteRepository;

	// 찜 확인
	@Transactional
	public boolean selectFavorite(Integer userId, Integer contentsId) {
		if (favoriteRepository.selectFavorite(Favorite.builder().userId(userId).contentsId(contentsId).build()) != null) {
			return true;
		}
		return false;
	}
	
	// 찜 등록
	@Transactional
	public ResponseEntity<Integer> insertFavorite(Integer userId, Integer contentsId) {
		int result = favoriteRepository.insertFavorite(Favorite.builder().userId(userId).contentsId(contentsId).build());
		if (result != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 찜 삭제
	public ResponseEntity<Integer> deleteFavorite(Integer userId, Integer contentsId) {
		int result = favoriteRepository.deleteFavorite(Favorite.builder().userId(userId).contentsId(contentsId).build());
		if (result != 1) {
			throw new CustomRestfulException("정상 처리되지 않았습니다", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
