package com.green.jejuplus.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.jejuplus.dto.admin.AdminUserDto;
import com.green.jejuplus.dto.user.SignUpFormDto;
import com.green.jejuplus.dto.user.UserDeleteDto;
import com.green.jejuplus.dto.user.UserUpdateDto;
import com.green.jejuplus.repository.model.User;

@Mapper
public interface UserRepository {
	

	User findByVerificationToken(String token);
	User findByEmail(@Param("email") String email);
	int insertUser(SignUpFormDto signUpFormDto);
	User findByUsername(@Param("username") String username);
	int insertKakaoUser(SignUpFormDto signUpFormDto);
	User findById(int userId);
	UserUpdateDto findByUser(int userId);
	int updateUser(UserUpdateDto userUpdateDto);
	void updateEmail(User user);
	User findByUpdateEmail(int userId);
	User findByPassword(int userId);
	void updateUserPassword(User user);
	User findByUserPassword(@Param("username") String username);
	User findByUsernameEmail(@Param("username")String username, @Param("email") String email);
	UserDeleteDto findByUserDelete(@Param("username")String username);
	void userDelete(@Param("username")String username);
	UserDeleteDto findByUserDeleteCheck(@Param("username")String username, @Param("password") String password);
	List<AdminUserDto> findUsers(@Param("pageSize") int pageSize, @Param("offset") int offset);
	AdminUserDto totalUsers();
	int count();
	void adminUserDelete(@Param("username")String username);
	void userLevelUpdate(User user);
	List<AdminUserDto> searchUsers(@Param("category") String category, @Param("search") String search, @Param("pageSize") int pageSize, @Param("offset") int offset);
	int countWithSearch(@Param("category") String category, @Param("search") String search);
	}
	

