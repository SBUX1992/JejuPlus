package com.green.jejuplus.controller.admin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.jejuplus.dto.admin.AdminPromotionDto;
import com.green.jejuplus.dto.admin.AdminUserDto;
import com.green.jejuplus.handler.exception.CustomException;
import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Promotion;
import com.green.jejuplus.repository.model.PromotionImg;
import com.green.jejuplus.repository.model.Schedule;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.admin.AdminService;
import com.green.jejuplus.service.schedule.ScheduleService;
import com.green.jejuplus.service.user.UserService;
import com.green.jejuplus.util.Define;
import com.green.jejuplus.util.Pagination;
import com.green.jejuplus.util.PagingDto;




@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	HttpSession session;

	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	UserService userService;

	@GetMapping("/adminUserManagement")
	public String adminUserManagement(
			@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, 
			@RequestParam(value = "category", required = false) String category, 
			@RequestParam(value = "search", required = false) String search, 
			Model model) {
		// 페이지 및 페이지 크기 관련 코드 (기존 코드와 동일)
		// ...

		List<AdminUserDto> users;
		int totalUsers;

		if (category != null && search != null) {
			// 사용자가 검색 조건과 검색어를 지정한 경우
			users = adminService.searchUsers(category, search, page, pageSize);
			totalUsers = adminService.getTotalUsersWithSearch(category, search); // 검색된 사용자 수 가져오기
		} else {
			// 사용자가 검색하지 않은 경우
			users = adminService.getUsers(page, pageSize);
			totalUsers = adminService.getTotalUsers(); // 모든 사용자 수 가져오기
		}

		int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

		model.addAttribute("users", users);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);

		return "/admin/adminUserManagement";
	}

	// 유저삭제
	@PostMapping("/adminUserDelete/{username}")
	@ResponseBody
	public Map<String, String> adminUserDelete(@PathVariable("username") String username) {
		System.out.println("관리자 유저네임 컨트롤러 : " + username);
		Map<String, String> response = new HashMap<>();
		try {
			adminService.userDelete(username);
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "error");
		}
		return response;
	}
	// 권한 변경
	@PostMapping("/updateUserLevel")
	public  ResponseEntity<Map<String, String>> updateUserLevel(@RequestParam("username") String username, @RequestParam("newLevelId") int newLevelId) {


		System.out.println("컨트롤러 레벨 아이디 확인 :" + newLevelId);
		try {
			System.out.println(username);
			// Perform the necessary logic to update the user's level
			adminService.updateUserLevel(username, newLevelId);

			// Return a success response
			Map<String, String> response = new HashMap<>();
			response.put("result", "success");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// Handle any exceptions or errors
			Map<String, String> response = new HashMap<>();
			response.put("result", "error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}


	}
	// 광고 올리기
	@GetMapping("/insertPromotion")
	public String adminPlaceManagement() {
		return "/admin/insertPromotion";
	}

	@PostMapping("/insertPromotion")
	public String insertPromotion(@RequestParam("title") String title,
			@RequestParam("introduce") String introduce,
			@RequestParam("content") String content,
			@RequestParam("images") MultipartFile[] images) {
		adminService.insertPromotion(title, introduce, content, images);
		
		return "redirect:/admin/adminPromotionManagement"; // 성공 페이지로 리다이렉트
	}
	
	
	// 올린 광고 리스트
	// 올린 광고 리스트
	@GetMapping("/adminPromotionManagement")
	public String adminPromotionManagement(
	        @RequestParam(name = "page", defaultValue = "1") int page,
	        @RequestParam(value = "search", required = false) String search,
	        Model model) {
		System.out.println("옴?");
	    int promotionsPerPage = 10; // Number of promotions per page
	    int totalPromotions;
	    List<AdminPromotionDto> promotions;

	    if (search != null) {
	        // If a search query is provided, fetch search results
	        promotions = adminService.searchPromotions(search, page, promotionsPerPage);
	        totalPromotions = adminService.getTotalPromotionsWithSearch(search);
	    } else {
	        // If no search query, fetch all promotions
	        promotions = adminService.getPromotionsByPage(page, promotionsPerPage);
	        totalPromotions = adminService.getTotalPromotions();
	    }

	    int totalPages = (int) Math.ceil((double) totalPromotions / promotionsPerPage);

	    for (AdminPromotionDto promotion : promotions) {
	        int promotionId = promotion.getPromotionId();
	        String imageUrl = adminService.getImageUrlsByPromotionId(promotionId);
	        promotion.setImageUrl(imageUrl);
	    }

	    model.addAttribute("promotions", promotions);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("search", search); // Pass the search query to the view

	    return "/admin/adminPromotionManagement";
	}
	
	// 업체 삭제
	@PostMapping("/adminPromotionDelete/{promotionId}")
	@ResponseBody
	public Map<String, String> adminPromotionDelete(@PathVariable("promotionId") int promotionId) {
		Map<String, String> response = new HashMap<>();
		System.out.println("컨트롤러 프로모션 아이디 확인 : " + promotionId);
		try {
			adminService.promotionDelete(promotionId);
			response.put("result", "success");
		} catch (Exception e) {
			response.put("result", "error");
		}
		return response;
	}
	
	@GetMapping("/updatePromotion/{promotionId}")
	public String updatePromotion(@PathVariable("promotionId") int promotionId, Model model) {
		AdminPromotionDto promotion = adminService.findPromotionDetail(promotionId);
		model.addAttribute("promotion",promotion);
		List<PromotionImg> images = userService.findImagesByPromotionId(promotionId);
		model.addAttribute("images",images);
		return "/admin/updatePromotion";
	}
	
	@PostMapping("/updatePromotion/{promotionId}")
	public String updatePromotionProc(@PathVariable("promotionId") int promotionId,
									 @RequestParam("title") String title,
									 @RequestParam("introduce") String introduce,
									 @RequestParam("content") String content,
									 @RequestParam("images") MultipartFile[] images,
									 Model model) {
		
		adminService.promotionDetailUpdate(promotionId,title,introduce,content,images);
		
		model.addAttribute("successMessage", "광고가 수정되었습니다.");

		    
		    // JavaScript 함수를 호출하여 메시지 표시
		    String script = "<script>showSuccessMessage('${successMessage}');</script>";
		    model.addAttribute("javascript", script);

		return "redirect:/admin/adminUserManagement" ;
	}
	
	@PostMapping("/updatePromotionEndDate/{promotionId}")
	public  ResponseEntity<Map<String, String>> updatePromotionEndDate(@PathVariable("promotionId") int promotionId) {
		Map<String, String> response = new HashMap<>();
		
		Promotion promotion = userService.findByPromotionDetail(promotionId);
		Timestamp  totalEndDate = promotion.getEndDate();
		// 포맷터 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 포맷팅하여 문자열로 변환
		String endDate = sdf.format(totalEndDate);
		System.out.println("컨트롤러 엔드데이트" + endDate);
		System.out.println("이번엔 프로모션아이디확인해보자" + promotionId);
		
		try {
			adminService.promotionEndDateUpdate(promotionId,endDate);
			response.put("result", "success");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("result", "error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
		
	}


}





