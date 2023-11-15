package com.green.jejuplus.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDeleteDto {
	 String userId;
	 int levelId;
	 String username; 
	 String password; 
	 String fullname; 
	 String email;
	 String phoneNumber; 
	 String isKakao;
}
