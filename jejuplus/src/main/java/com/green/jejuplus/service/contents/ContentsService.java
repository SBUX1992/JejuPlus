package com.green.jejuplus.service.contents;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.green.jejuplus.dto.contents.FavoriteDto;
import com.green.jejuplus.dto.contents.LodgingDetailDto;
import com.green.jejuplus.dto.contents.LodgingListDto;
import com.green.jejuplus.dto.contents.RestaurantDetailDto;
import com.green.jejuplus.dto.contents.RestaurantListDto;
import com.green.jejuplus.dto.contents.ShoppingDetailDto;
import com.green.jejuplus.dto.contents.ShoppingListDto;
import com.green.jejuplus.dto.contents.TouristAreaDetailDto;
import com.green.jejuplus.dto.contents.TouristAreaListDto;
import com.green.jejuplus.handler.exception.CustomRestfulException;
import com.green.jejuplus.repository.interfaces.ContentsRepository;
import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Favorite;
import com.green.jejuplus.util.PagingDto;

@Service
public class ContentsService {
	
	@Autowired
	private ContentsRepository contentsRepository;

	// 관광지 리스트
	@Transactional
	public List<TouristAreaListDto> findTouristArea(PagingDto paging) {
	List<TouristAreaListDto> list = contentsRepository.findTouristAreaList(paging);
        StringBuilder sb = new StringBuilder();
        for (TouristAreaListDto contents : list) {
            String tag = contents.getTag();
            String[] tags = tag.split(",");
            for (String tagElement : tags) {
                sb.append("#").append(tagElement).append(" ");
            }
            String result = sb.substring(0, sb.length() - 1);
            contents.setTag(result);
            sb.setLength(0);
        }
		return list;
	}
	
	// 관광지 상세보기
	@Transactional
	public TouristAreaDetailDto touristAreaDetail(int contentsId) {
		TouristAreaDetailDto touristAreaDetailDto = contentsRepository.showTouristAreaDetail(contentsId);
        StringBuilder sb = new StringBuilder();
        String tag = touristAreaDetailDto.getTag();
        String[] tags = tag.split(",");
        for (String tagElement : tags) {
            sb.append("#").append(tagElement).append(" ");
        }
        String result = sb.substring(0, sb.length() - 1);
        touristAreaDetailDto.setTag(result);
        sb.setLength(0);
		return touristAreaDetailDto;
	}

	// 음식점 리스트
	@Transactional
	public List<RestaurantListDto> findRestaurant(PagingDto paging) {
		List<RestaurantListDto> list = contentsRepository.findRestaurantList(paging);
        StringBuilder sb = new StringBuilder();
        for (RestaurantListDto contents : list) {
            String tag = contents.getTag();
            String[] tags = tag.split(",");
            for (String tagElement : tags) {
                sb.append("#").append(tagElement).append(" ");
            }
            String result = sb.substring(0, sb.length() - 1);
            contents.setTag(result);
            sb.setLength(0);
        }
		return list;
	}
	
	// 음식점 상세보기
	@Transactional
	public RestaurantDetailDto restaurantDetail(int contentsId) {
		RestaurantDetailDto restaurantDetailDto = contentsRepository.showRestaurantDetail(contentsId);
        StringBuilder sb = new StringBuilder();
        String tag = restaurantDetailDto.getTag();
        String[] tags = tag.split(",");
        for (String tagElement : tags) {
            sb.append("#").append(tagElement).append(" ");
        }
        String result = sb.substring(0, sb.length() - 1);
        restaurantDetailDto.setTag(result);
        sb.setLength(0);
		return restaurantDetailDto;
	}
	
	// 숙박 리스트
	@Transactional
	public List<LodgingListDto> findLodging(PagingDto paging) {
		List<LodgingListDto> list = contentsRepository.findLodgingList(paging);
        StringBuilder sb = new StringBuilder();
        for (LodgingListDto contents : list) {
            String tag = contents.getTag();
            String[] tags = tag.split(",");
            for (String tagElement : tags) {
                sb.append("#").append(tagElement).append(" ");
            }
            String result = sb.substring(0, sb.length() - 1);
            contents.setTag(result);
            sb.setLength(0);
        }
		return list;
	}

	// 숙박 상세보기
	@Transactional
	public LodgingDetailDto lodgingDetail(int contentsId) {
		LodgingDetailDto lodgingDetailDto = contentsRepository.showLodgingDetail(contentsId);
        StringBuilder sb = new StringBuilder();
        String tag = lodgingDetailDto.getTag();
        String[] tags = tag.split(",");
        for (String tagElement : tags) {
            sb.append("#").append(tagElement).append(" ");
        }
        String result = sb.substring(0, sb.length() - 1);
        lodgingDetailDto.setTag(result);
        sb.setLength(0);
		return lodgingDetailDto;
	}
	
	// 쇼핑 리스트
	@Transactional
	public List<ShoppingListDto> findShopping(PagingDto paging) {
		List<ShoppingListDto> list = contentsRepository.findShoppingList(paging);
        StringBuilder sb = new StringBuilder();
        for (ShoppingListDto contents : list) {
            String tag = contents.getTag();
            String[] tags = tag.split(",");
            for (String tagElement : tags) {
                sb.append("#").append(tagElement).append(" ");
            }
            String result = sb.substring(0, sb.length() - 1);
            contents.setTag(result);
            sb.setLength(0);
        }
		return list;
	}
	
	// 쇼핑 상세보기
	@Transactional
	public ShoppingDetailDto shoppingDetail(int contentsId) {
		ShoppingDetailDto shoppingDetailDto = contentsRepository.showShoppingDetail(contentsId);
        StringBuilder sb = new StringBuilder();
        String tag = shoppingDetailDto.getTag();
        String[] tags = tag.split(",");
        for (String tagElement : tags) {
            sb.append("#").append(tagElement).append(" ");
        }
        String result = sb.substring(0, sb.length() - 1);
        shoppingDetailDto.setTag(result);
        sb.setLength(0);
		return shoppingDetailDto;
	}

	// 찜 리스트
	@Transactional
	public List<FavoriteDto> selectFavotiteList(Integer userId) {
		return contentsRepository.selectFavoriteList(userId);
	}

	public int countTouristArea() {
		return contentsRepository.countTouristArea();
	}
	public int countRestaurant() {
		return contentsRepository.countRestaurant();
	}
	public int countLodging() {
		return contentsRepository.countLodging();
	}
	public int countShopping() {
		return contentsRepository.countShopping();
	}

}
