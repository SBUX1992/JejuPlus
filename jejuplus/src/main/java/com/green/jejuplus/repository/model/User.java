package com.green.jejuplus.repository.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	int userId;
	int levelId;
	String username; 
	String password; 
	String fullname;
	String email;
	String phoneNumber; 
	String isKakao;
	
	private String randomCode;
	
	 private boolean verified; // 이메일 인증 상태

	    // Getter와 Setter 메서드
	    public boolean isVerified() {
	        return verified;
	    }

	    public void setVerified(boolean verified) {
	        this.verified = verified;
	    }
}
