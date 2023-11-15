package com.green.jejuplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.jejuplus.dto.MainRestaurantDto;
import com.green.jejuplus.dto.admin.AdminPromotionDto;
import com.green.jejuplus.dto.admin.AdminUserDto;
import com.green.jejuplus.repository.interfaces.MainRepository;

@Service
public class MainService {

	@Autowired
	MainRepository mainRepository;
	
	public List<MainRestaurantDto> findRestaurant() {
		 List<MainRestaurantDto> restaurants = mainRepository.findRestaurantAll();
		return restaurants;
	}

	public List<MainRestaurantDto> findPlace() {
		List<MainRestaurantDto> place = mainRepository.findPlaceAll();
		return place;
	}

	public List<AdminPromotionDto> findPromotion() {
		List<AdminPromotionDto> promotion = mainRepository.findPromotionAll();
		return null;
	}

}
