package com.green.jejuplus.controller.contents;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.jejuplus.dto.contents.FavoriteDto;
import com.green.jejuplus.dto.contents.FavoriteRequestDto;
import com.green.jejuplus.dto.contents.LodgingDetailDto;
import com.green.jejuplus.dto.contents.LodgingListDto;
import com.green.jejuplus.dto.contents.RestaurantDetailDto;
import com.green.jejuplus.dto.contents.RestaurantListDto;
import com.green.jejuplus.dto.contents.ReviewDto;
import com.green.jejuplus.dto.contents.ShoppingDetailDto;
import com.green.jejuplus.dto.contents.ShoppingListDto;
import com.green.jejuplus.dto.contents.TouristAreaDetailDto;
import com.green.jejuplus.dto.contents.TouristAreaListDto;
import com.green.jejuplus.repository.model.Favorite;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.contents.ContentsService;
import com.green.jejuplus.service.contents.FavoriteService;
import com.green.jejuplus.service.contents.RecommendedService;
import com.green.jejuplus.service.contents.ReviewService;
import com.green.jejuplus.util.Define;
import com.green.jejuplus.util.Pagination;
import com.green.jejuplus.util.PagingDto;


@Controller
@RequestMapping("/contents")
public class ContentsController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private ContentsService contentsService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private RecommendedService recommendedService;

	// 찜 리스트
	@GetMapping("/favoriteList")
	public String favoriteList(Model model) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		List<FavoriteDto> favoriteList = contentsService.selectFavotiteList(principal.getUserId());
		model.addAttribute("favoriteList", favoriteList);
		return "contents/favoriteList";
	}
	
	// 관광지 리스트	
	@GetMapping("/touristAreaList")
	public String touristAreaList(@ModelAttribute("paging")PagingDto paging,
			@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model, HttpServletRequest request) {
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		List<TouristAreaListDto> touristAreaList = contentsService.findTouristArea(paging);
		int total = contentsService.countTouristArea();
		pagination.setArticleTotalCount(total);
		model.addAttribute("touristAreaList", touristAreaList);
		model.addAttribute("pagination", pagination);
		return "contents/touristAreaList";
	}
	
	// 관광지 상세보기
	@GetMapping("/touristAreaDetail/{contentsId}")
	public String touristAreaDetail(@PathVariable("contentsId") int contentsId, Model model) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		TouristAreaDetailDto touristAreaDetail = contentsService.touristAreaDetail(contentsId);
		List<ReviewDto> review = reviewService.showReview(contentsId);
		
		if (principal != null) {
			boolean isFavorite = favoriteService.selectFavorite(principal.getUserId(), contentsId);
			boolean isRecommended = recommendedService.selectRecommended(principal.getUserId(), contentsId);
			model.addAttribute("isFavorite", isFavorite);
			model.addAttribute("isRecommended", isRecommended);
		}

		model.addAttribute("touristAreaDetail", touristAreaDetail);
		model.addAttribute("review",review);
		
		return "contents/touristAreaDetail";
	}
	
	// 맛집 리스트
	@GetMapping("/restaurantList")
	public String restaurantList(@ModelAttribute("paging")PagingDto paging,
			@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model, HttpServletRequest request) {
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		List<RestaurantListDto> restaurantList = contentsService.findRestaurant(paging);
		int total = contentsService.countRestaurant();
		pagination.setArticleTotalCount(total);
		model.addAttribute("restaurantList", restaurantList);
		model.addAttribute("pagination", pagination);
		return "contents/restaurantList";
	}
	
	// 맛집 상세보기
	@GetMapping("/restaurantDetail/{contentsId}")
	public String restaurantDetail(@PathVariable("contentsId") int contentsId, Model model) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		RestaurantDetailDto restaurantDetail = contentsService.restaurantDetail(contentsId);
		List<ReviewDto> review = reviewService.showReview(contentsId);
		
		if (principal != null) {
			boolean isFavorite = favoriteService.selectFavorite(principal.getUserId(), contentsId);
			boolean isRecommended = recommendedService.selectRecommended(principal.getUserId(), contentsId);
			model.addAttribute("isFavorite", isFavorite);
			model.addAttribute("isRecommended", isRecommended);
		}
		model.addAttribute("restaurantDetail", restaurantDetail);
		model.addAttribute("review",review);
		return "contents/restaurantDetail";
	}
	
	// 숙소 리스트
	@GetMapping("/lodgingList")
	public String lodgingList(@ModelAttribute("paging")PagingDto paging,
			@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model, HttpServletRequest request) {
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		List<LodgingListDto> lodgingList = contentsService.findLodging(paging);
		int total = contentsService.countLodging();
		pagination.setArticleTotalCount(total);
		model.addAttribute("lodgingList", lodgingList);
		model.addAttribute("pagination", pagination);
		return "contents/lodgingList";
	}
	
	// 숙소 상세보기
	@GetMapping("/lodgingDetail/{contentsId}")
	public String lodgingDetail(@PathVariable("contentsId") int contentsId, Model model) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		LodgingDetailDto lodgingDetail = contentsService.lodgingDetail(contentsId);
		List<ReviewDto> review = reviewService.showReview(contentsId);
		
		if (principal != null) {
			boolean isFavorite = favoriteService.selectFavorite(principal.getUserId(), contentsId);
			boolean isRecommended = recommendedService.selectRecommended(principal.getUserId(), contentsId);
			model.addAttribute("isFavorite", isFavorite);
			model.addAttribute("isRecommended", isRecommended);
		}	
		
		model.addAttribute("lodgingDetail", lodgingDetail);
		model.addAttribute("review",review);
		return "contents/lodgingDetail";
	}
	
	
	// 쇼핑 리스트
	@GetMapping("/shoppingList")
	public String shoppingList(@ModelAttribute("paging")PagingDto paging,
			@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model, HttpServletRequest request) {
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		List<ShoppingListDto> shoppingList = contentsService.findShopping(paging);
		int total = contentsService.countShopping();
		pagination.setArticleTotalCount(total);
		model.addAttribute("shoppingList", shoppingList);
		model.addAttribute("pagination", pagination);
		return "contents/shoppingList";
	}
	
	
	// 쇼핑 상세보기
	@GetMapping("/shoppingDetail/{contentsId}")
	public String shoppingDetail(@PathVariable("contentsId") int contentsId, Model model) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		ShoppingDetailDto shoppingDetail = contentsService.shoppingDetail(contentsId);
		List<ReviewDto> review = reviewService.showReview(contentsId);
		
		if (principal != null) {
			boolean isFavorite = favoriteService.selectFavorite(principal.getUserId(), contentsId);
			boolean isRecommended = recommendedService.selectRecommended(principal.getUserId(), contentsId);
			model.addAttribute("isFavorite", isFavorite);
			model.addAttribute("isRecommended", isRecommended);
		}

		model.addAttribute("shoppingDetail", shoppingDetail);
		model.addAttribute("review",review);
		
		return "contents/shoppingDetail";
	}
	
}
