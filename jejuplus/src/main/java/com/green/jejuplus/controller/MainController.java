package com.green.jejuplus.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.jejuplus.dto.MainRestaurantDto;
import com.green.jejuplus.dto.admin.AdminPromotionDto;
import com.green.jejuplus.service.MainService;
import com.green.jejuplus.service.admin.AdminService;

@Controller
public class MainController {

	@Autowired
	HttpSession session;
	
	@Autowired
	MainService mainservice;
	
	@Autowired
	AdminService adminService;
	
	@GetMapping(value= {"main","/"})
	public String main(Model model) {
		List<MainRestaurantDto> restaurantDto = mainservice.findRestaurant();
		model.addAttribute("restaurantDto",restaurantDto);
		
		List<MainRestaurantDto> palceDto = mainservice.findPlace();
		model.addAttribute("palceDto",palceDto);
		
		List<AdminPromotionDto> promotions = adminService.getPromotions(); // Initialize the list here
	    
	    for (AdminPromotionDto promotion : promotions) {
	        int promotionId = promotion.getPromotionId();
	        String imageUrl = adminService.getImageUrlsByPromotionId(promotionId);
	        promotion.setImageUrl(imageUrl);
	    }
	    
	    model.addAttribute("promotions", promotions);
	    
	    String projectRoot = System.getProperty("user.dir");
	    System.out.println(projectRoot);
		
		
		return "/main";
	}
	
	
}
