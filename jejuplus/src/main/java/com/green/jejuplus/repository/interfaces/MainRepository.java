package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.dto.MainRestaurantDto;
import com.green.jejuplus.dto.admin.AdminPromotionDto;

@Mapper
public interface MainRepository {

	List<MainRestaurantDto> findRestaurantAll();

	List<MainRestaurantDto> findPlaceAll();

	List<AdminPromotionDto> findPromotionAll();

}
