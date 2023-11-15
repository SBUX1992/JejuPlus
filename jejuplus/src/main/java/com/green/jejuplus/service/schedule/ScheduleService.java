package com.green.jejuplus.service.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.green.jejuplus.dto.schedule.ScheduleDetailDto;
import com.green.jejuplus.dto.schedule.ScheduleDto;
import com.green.jejuplus.dto.schedule.ScheduleItemDto;
import com.green.jejuplus.dto.schedule.ScheduleListDto;
import com.green.jejuplus.handler.exception.CustomException;
import com.green.jejuplus.repository.interfaces.ScheduleRepository;
import com.green.jejuplus.repository.model.Contents;
import com.green.jejuplus.repository.model.Schedule;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.util.Define;
import com.green.jejuplus.util.PagingDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	public List<Contents> findAllList(PagingDto paging) {

		List<Contents> list = scheduleRepository.findAllList(paging);
		addHashTag(list);

		return list;

	}
	
	public List<Contents> findListByLabel(String label, User user, PagingDto paging) {
		
		List<Contents> list = null;
		
		log.info(label);
		switch (label) {

		case "attraction":
			list = scheduleRepository.findListByLabel(Define.ATTRACTION, paging);
			break;
		case "accomodation":
			list = scheduleRepository.findListByLabel(Define.ACCOMODATION, paging);
			break;
		case "shopping":
			list = scheduleRepository.findListByLabel(Define.SHOPPING, paging);
			break;
		case "restaurant":
			list = scheduleRepository.findListByLabel(Define.RESTAURANT, paging);
			break;
		case "favorite":
			list = scheduleRepository.findMyFavoriteList(user.getUserId(), paging);
			break;
		default:
			list = scheduleRepository.findAllList(paging);
			break;
		}	
		
		addHashTag(list);
		log.info(Integer.toString(list.size()));
		log.info(Integer.toString(user.getUserId()));
	
		
		return list;

	}

	public int countList(String label, User user) {

		switch (label) {

		case "attraction":
			return scheduleRepository.findCountByLabel(Define.ATTRACTION);
		case "accomodation":
			return scheduleRepository.findCountByLabel(Define.ACCOMODATION);
		case "shopping":
			return scheduleRepository.findCountByLabel(Define.SHOPPING);
		case "restaurant":
			return scheduleRepository.findCountByLabel(Define.RESTAURANT);
		case "favorite":
			return scheduleRepository.findCountByMyFavorite(user.getUserId());
		default:
			return scheduleRepository.findCountAll();
		}
	}

	public int countSearchList(String category, String keyword) {

		int count = 0;
		if (category.equals("title")) {
			count = scheduleRepository.findCountBySearchTitle(keyword);
		} else if (category.equals("tag")) {
			count = scheduleRepository.findCountBySearchTag(keyword);
		} else {
			throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		return count;
	}

	public List<Contents> findContentsBySearch(String category, String search, PagingDto paging) {
		
		List<Contents> list = null;
		
		if (category.equals("title")) {
			list = scheduleRepository.findContentsBySearchTitle(search, paging);
		} else if (category.equals("tag")) {
			list =  scheduleRepository.findContentsBySearchTag(search, paging);
		} else {
			throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		addHashTag(list);
		return list;

	}

	public Schedule findScheduleById(Integer scheduleId) {
		
		Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
		if(schedule==null) {
			throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		return schedule;
	}
	
	public List<ScheduleListDto> findScheduleByUserId(Integer UserId){
		
		return scheduleRepository.findScheduleByUserId(UserId);
	}
	
	public String insertScheduleDetail(List<ScheduleDetailDto> scheduleDetailDto) {
		
		for (ScheduleDetailDto dto : scheduleDetailDto) {
			int result = scheduleRepository.insertScheduleDetail(dto);
			if(result!= 1) {
				return "fail";
			}
		}
		
		return "success";
	}
	
	public void deleteScheduleDetailByDay(ScheduleDetailDto scheduleDetailDto) {
		
		scheduleRepository.deleteScheduleDetailByDay(scheduleDetailDto);
	}
	
	public List<ScheduleItemDto> requestList(Integer scheduleId, Integer itemDay){
		
		return scheduleRepository.findScheduleDetailByDay(scheduleId, itemDay);
	}
	
	public void insertSchedule(ScheduleDto scheduleDto) {
		
		int result = scheduleRepository.insertSchedule(scheduleDto);
		if(result!=1) {
			throw new CustomException("일정 추가에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public Integer findNewestScheduleIdByUserId(Integer userId) {
		
		return scheduleRepository.findNewestScheduleIdByUserId(userId);
	}
	
	public void updateSchedule(Schedule schedule) {
		
		int result = scheduleRepository.updateSchedule(schedule);
		if(result!=1) {
			throw new CustomException("일정 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public void deleteSchedule(Integer scheduleId) {
		scheduleRepository.deleteSchedule(scheduleId);
	}
	
	public Contents findContentsById(Integer contentsId) {
		Contents contents = scheduleRepository.findContentsById(contentsId);
		if(contents==null) {
			throw new CustomException("컨텐츠를 찾아오는 데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return contents;
	}
	
	//해쉬태그(#)를 붙여줌
	public void addHashTag(List<Contents> list) {

		StringBuilder sb = new StringBuilder();
		for (Contents contents : list) {
			String tag = contents.getTag();
			String[] tags = tag.split(",");
			for (String tagElement : tags) {
				sb.append("#").append(tagElement).append(" ");
			}
			String result = sb.substring(0, sb.length() - 1);
			contents.setTag(result);
			sb.setLength(0);
		}
	}

}
