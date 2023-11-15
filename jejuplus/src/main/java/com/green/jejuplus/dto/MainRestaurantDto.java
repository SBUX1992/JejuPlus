package com.green.jejuplus.dto;

import org.springframework.stereotype.Component;

import com.green.jejuplus.dto.admin.AdminUserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MainRestaurantDto {
	String contentsId;
	String imgPath;
	String title;
	String tag;	
}
