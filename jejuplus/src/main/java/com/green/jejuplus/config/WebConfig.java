package com.green.jejuplus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;





@Configuration
public class WebConfig implements WebMvcConfigurer{

	// DI 처리 (의존성 주입)
		@Autowired
		private AuthInterceptor authInterceptor;
		
		@Autowired
		private AdminInterceptor amdinInterceptor;
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(authInterceptor)
			.addPathPatterns("/schedule/**")
			.addPathPatterns("/user/userDelete")
			.excludePathPatterns("/error/**"); 
			
			registry.addInterceptor(amdinInterceptor).addPathPatterns("/admin/**");
			
			
		}
		
	@Bean // IoC 관리 대상 -> 싱글톤 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
