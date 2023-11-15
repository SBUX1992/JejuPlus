package com.green.jejuplus.dto.admin;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AdminUserDto {
	int userId;
	int levelId;
	String username; 
	String password; 
	String fullname;
	String email;
	String phoneNumber; 
	String isKakao;
	String pageSize;
	String offset;
	String category;
	
}
