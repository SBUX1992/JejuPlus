package com.green.jejuplus.controller.schedule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.jejuplus.dto.schedule.ScheduleDetailDto;
import com.green.jejuplus.dto.schedule.ScheduleDto;
import com.green.jejuplus.dto.schedule.ScheduleItemDto;
import com.green.jejuplus.dto.schedule.ScheduleListDto;
import com.green.jejuplus.handler.exception.CustomException;
import com.green.jejuplus.handler.exception.UnAuthorizedException;
import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Schedule;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.schedule.ScheduleService;
import com.green.jejuplus.util.Define;
import com.green.jejuplus.util.Pagination;
import com.green.jejuplus.util.PagingDto;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	HttpSession session;

	@GetMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		
		User user = (User) session.getAttribute(Define.PRINCIPAL);
	
		List<ScheduleListDto> list = scheduleService.findScheduleByUserId(user.getUserId());
		model.addAttribute("scheduleList", list);
		
		return "/schedule/list";
	}

	@GetMapping("/detail/edit/{scheduleId}")
	public String edit(@PathVariable("scheduleId") Integer scheduleId, Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		Schedule schedule = scheduleService.findScheduleById(scheduleId);
		
		if(!schedule.getUserId().equals(user.getUserId())) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		PagingDto paging = new PagingDto();
		Pagination pagination = new Pagination();
		pagination.setPaging(paging);
		int total =  scheduleService.countList("all", user);
		pagination.setArticleTotalCount(total);
		
		model.addAttribute(pagination);
		
		List<Contents> list = scheduleService.findAllList(paging);

		model.addAttribute("schedule", schedule);
		model.addAttribute("list", list);
		model.addAttribute("label", "all");

		return "/schedule/editDetail";
	}
	
	@GetMapping("/detail/show/{scheduleId}")
	public String show(@PathVariable("scheduleId")Integer scheduleId, Model model) {
		
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		Schedule schedule = scheduleService.findScheduleById(scheduleId);
		
		if(!schedule.getUserId().equals(user.getUserId())) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.UNAUTHORIZED);
		}
		
		model.addAttribute("schedule", schedule);
		
		
		return "/schedule/showDetail";
	}

	@GetMapping("/show-list/{label}")
	public String contentList(@ModelAttribute("paging")PagingDto paging,@PathVariable("label") String label,
			@RequestParam(value = "page", required = false, defaultValue = "1")int page, Model model, HttpServletRequest request) {
		
		// 주소창에 직접 입력시 오류 발생
		if (request.getHeader("REFERER") == null) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}
		
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		int total =  scheduleService.countList(label, user);
		pagination.setArticleTotalCount(total);
		List<Contents> list = scheduleService.findListByLabel(label,user,paging);
		model.addAttribute("list",list);
		model.addAttribute("pagination", pagination);
		model.addAttribute("label", label);

		return "/schedule/contentsList";
	}
	
	@GetMapping("/call-search")
	public String callSearch(HttpServletRequest request) {
		if (request.getHeader("REFERER") == null) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}
		
		return "/schedule/searchWindow";
	}
	
	@GetMapping("/search-list")
	public String searchList(@ModelAttribute("paging")PagingDto paging, @RequestParam("category") String category, 
			@RequestParam("keyword") String keyword, @RequestParam(value = "page", required = false, defaultValue = "1")int page, 
			HttpServletRequest request, Model model) {
		// 주소창에 직접 입력시 오류 발생
		if (request.getHeader("REFERER") == null) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}
	
		Pagination pagination = new Pagination();
		paging.setPage(page);
		pagination.setPaging(paging);
		int total = scheduleService.countSearchList(category,keyword);
		pagination.setArticleTotalCount(total);
		List<Contents> list = scheduleService.findContentsBySearch(category, keyword, paging);
		model.addAttribute("list",list);
		model.addAttribute("pagination", pagination);
		
		
		return "/schedule/searchList";
	}
	
	@PostMapping("/detail/save")
	@ResponseBody
	public ResponseEntity<String> saveDatail(@RequestBody List<ScheduleDetailDto> list){
		
		scheduleService.deleteScheduleDetailByDay(list.get(0));
		
		String result = scheduleService.insertScheduleDetail(list);
		
		if(result.equals("success")) {
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.badRequest().body(result);
		}
	
	}
	
	@GetMapping("/detail/request")
	@ResponseBody
	public ResponseEntity<List<ScheduleItemDto>> requestDetailList(@RequestParam("scheduleId")Integer scheduleId, @RequestParam("itemDay")Integer itemDay){
		
		List<ScheduleItemDto> list = scheduleService.requestList(scheduleId, itemDay);
		
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/insert")
	@ResponseBody
	@Transactional
	public ResponseEntity<Integer> insertSchedule(@RequestBody ScheduleDto scheduleDto){
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		if(!scheduleDto.getUserId().equals(user.getUserId())) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.UNAUTHORIZED);
		}
		scheduleService.insertSchedule(scheduleDto);
		Integer newScheId = scheduleService.findNewestScheduleIdByUserId(user.getUserId());
		if(newScheId==null) {
			throw new CustomException("일정 추가 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return ResponseEntity.ok().body(newScheId);
	}
	
	@PutMapping("/modify")
	@ResponseBody
	public ResponseEntity<String> modifySchedule(@RequestBody Schedule schedule){
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		if(!schedule.getUserId().equals(user.getUserId())) {
			throw new CustomException("잘못된 접근입니다.", HttpStatus.UNAUTHORIZED);
		}
		scheduleService.updateSchedule(schedule);
		
		return ResponseEntity.ok().body("success");
	}
	
	@DeleteMapping("/delete/{scheduleId}")
	public ResponseEntity<String> deleteSchedule(@PathVariable("scheduleId")Integer scheduleId){
		
		scheduleService.deleteSchedule(scheduleId);
		
		return ResponseEntity.ok().body("success");
	}
	
	@GetMapping("/contentsDetail/{contentsId}")
	@ResponseBody
	public ResponseEntity<Contents> findContentsDetail(@PathVariable("contentsId")Integer contentsId) {
		
		Contents contents = scheduleService.findContentsById(contentsId);
		
		return ResponseEntity.ok().body(contents);	
	}
	

	
}
