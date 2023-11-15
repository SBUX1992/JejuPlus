package com.green.jejuplus.service.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.jejuplus.dto.user.SignInFormDto;
import com.green.jejuplus.dto.user.SignUpFormDto;
import com.green.jejuplus.dto.user.UserDeleteDto;
import com.green.jejuplus.dto.user.UserUpdateDto;
import com.green.jejuplus.handler.exception.CustomException;
import com.green.jejuplus.repository.interfaces.PromotionRepository;
import com.green.jejuplus.repository.interfaces.UserRepository;
import com.green.jejuplus.repository.model.Promotion;
import com.green.jejuplus.repository.model.PromotionImg;
import com.green.jejuplus.repository.model.User;




@Service
@Transactional
public class UserService {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	PromotionRepository promotionRepository;



	public String getRandomCodeForUser(String userEmail) {
      User user = userRepository.findByEmail(userEmail);
      if (user != null) {
	         return user.getRandomCode();
      }
	     return null; // 사용자를 찾을 수 없으면 null 또는 다른 처리를 수행
	 }
	
	@Transactional
	public void registerUser(SignUpFormDto signUpFormDto) {		
		
			String rawPwd = signUpFormDto.getPassword();
			String hashPwd = passwordEncoder.encode(rawPwd);
			System.out.println("rawPwd : " + rawPwd);
			System.out.println("hashPwd : " + hashPwd);		
			signUpFormDto.setPassword(hashPwd);	
		 System.out.println("세번째 : " + signUpFormDto.getUsername());
		int result = userRepository.insertUser(signUpFormDto);
		if(result != 1) {
					throw new CustomException("회원가입실패",
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}					

	
	public boolean isUsernameUnique(String username) {
	    // 아이디가 중복되지 않았는지 확인
	    User existingUser = userRepository.findByUsername(username);

	    // 정규 표현식을 사용하여 아이디에 한글이 포함되는지 검사
	    boolean isKorean = username.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");

	    return existingUser == null && !isKorean; // 중복되지 않고, 한글이 포함되지 않으면 true 반환
	}


    public boolean isEmailUnique(String email) {
        // 이메일이 중복되지 않았는지 확인
        User existingUser = userRepository.findByEmail(email);
        return existingUser == null; // 중복되지 않으면 true, 중복되면 false 반환
    }

	public User signIn(SignInFormDto signInFormDto) {
		// 계정 이름만 확인으로 변경 처리
				User userEntity = userRepository.findByUsername(signInFormDto.getUsername());
				
				
				// 계정 확인
				// 계정이 존재 하지 않습니다.
				if(userEntity == null || 
						userEntity
						.getUsername().equals(signInFormDto.getUsername()) == false) {
					throw new CustomException("존재하지 않는 계정입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			
				// 비번 확인
				// 비밀번호가 틀렸습니다.
				boolean isPwdMatched = passwordEncoder
						.matches(signInFormDto.getPassword(), userEntity.getPassword());
				
				if(isPwdMatched == false) {
					throw new CustomException("비밀번호가 틀렸습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
								
			
				return userEntity;	
	}

	public User searchUsername(String username) {		
		 return userRepository.findByUsername(username);
	}

	public void registerUserKakao(SignUpFormDto signUpFormDto) {
		String rawPwd = signUpFormDto.getPassword();
		String hashPwd = passwordEncoder.encode(rawPwd);
		System.out.println("rawPwd : " + rawPwd);
		System.out.println("hashPwd : " + hashPwd);		
		signUpFormDto.setPassword(hashPwd);	
	 System.out.println("세번째 : " + signUpFormDto.getUsername());
	int result = userRepository.insertKakaoUser(signUpFormDto);
	if(result != 1) {
				throw new CustomException("회원가입실패",
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
	}

	public UserUpdateDto findUser(int userId) {
		UserUpdateDto userDetailDto = userRepository.findByUser(userId);
		return userDetailDto;
	}

	public int userDetailUpdate(int userId,UserUpdateDto userUpdateDto) {
		int result = userRepository.updateUser(userUpdateDto);
		return result;
	}

	public boolean updateEmail(String email,int userId) {
        try {
            User user = userRepository.findByUpdateEmail(userId); 
            System.out.println(" 서비스 : " + user);
            if (user != null) {
                user.setEmail(email);
                userRepository.updateEmail(user); 
                return true; 
            }
        } catch (Exception e) {
        }
        return false; 
    }

	public User getUserPassword(int userId) {
		return userRepository.findByPassword(userId);
	}

	public void updateUserPassword(User user) {
		String rawPwd = user.getPassword();
		String hashPwd = passwordEncoder.encode(rawPwd);
		user.setPassword(hashPwd);	
		 userRepository.updateUserPassword(user);		
	}

	public User findUserByEmail(String email) {
		
		System.out.println("이메일 찾기 서비스란 before:" + email);
		User findUsername = userRepository.findByEmail(email);
		System.out.println("이메일 찾기 서비스란 after:" + findUsername);
		return findUsername;
	}

	public User findByUsernameEmail(String username, String email) {
		User findUsernameEamil = userRepository.findByUsernameEmail(username,email);
		System.out.println("서비스 아이디: " + username);
	    System.out.println("서비스 이메일: " + email);
		return findUsernameEamil;
	}

	public UserDeleteDto findUserDelete(String username) {
		
		UserDeleteDto userDeleteDto = userRepository.findByUserDelete(username);
		return userDeleteDto;
	}

	

	public void userDelete(String username, String password) {
		System.out.println("유저탈퇴 서비스 확인 : " + username + "그리고 " + password);
		 userRepository.userDelete(username);

	}

	public UserDeleteDto findUserDeleteCheck(String username, String password) {
		UserDeleteDto userDeleteDtocheck = userRepository.findByUserDeleteCheck(username, password);
		return userDeleteDtocheck;
	}

	public Promotion findByPromotionDetail(int promotionId) {
		Promotion promotion = promotionRepository.findByPromotionDetail(promotionId);
		return promotion;
	}

	public List<PromotionImg> findImagesByPromotionId(int promotionId) {
		List<PromotionImg> images = promotionRepository.findByPromotionImg(promotionId);
		return images;
	}
	
	

}
