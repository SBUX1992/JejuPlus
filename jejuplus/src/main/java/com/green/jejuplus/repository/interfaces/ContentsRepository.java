package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.green.jejuplus.dto.contents.FavoriteDto;
import com.green.jejuplus.dto.contents.LodgingDetailDto;
import com.green.jejuplus.dto.contents.LodgingListDto;
import com.green.jejuplus.dto.contents.RestaurantDetailDto;
import com.green.jejuplus.dto.contents.RestaurantListDto;
import com.green.jejuplus.dto.contents.ShoppingDetailDto;
import com.green.jejuplus.dto.contents.ShoppingListDto;
import com.green.jejuplus.dto.contents.TouristAreaDetailDto;
import com.green.jejuplus.dto.contents.TouristAreaListDto;
import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Favorite;
import com.green.jejuplus.util.PagingDto;

@Mapper
public interface ContentsRepository {
	
	public List<TouristAreaListDto> findTouristAreaList(PagingDto paging);
	public List<RestaurantListDto> findRestaurantList(PagingDto paging);
	public List<LodgingListDto> findLodgingList(PagingDto paging);
	public List<ShoppingListDto> findShoppingList(PagingDto paging);
	public TouristAreaDetailDto showTouristAreaDetail(int contentsId);
	public RestaurantDetailDto showRestaurantDetail(int contentsId);
	public LodgingDetailDto showLodgingDetail(int contentsId);
	public ShoppingDetailDto showShoppingDetail(int contentsId);
	public List<FavoriteDto> selectFavoriteList(@Param("userId") Integer userId);
	public Contents selectRecommend(Contents contents);
	public int countTouristArea();
	public int countRestaurant();
	public int countLodging();
	public int countShopping();
}