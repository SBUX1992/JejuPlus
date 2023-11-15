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
public class SignUpFormDto {
	
	private String userId;
	private	int levelId;
	private String username; 
	private String password; 
	private String fullname; 
	private String email;
	private String phoneNumber; 
	private String isKakao;
	
	// TODO - 추후 추가 예정
}
