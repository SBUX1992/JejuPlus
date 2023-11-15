package com.green.jejuplus.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
	 private String to;
	    private String subject;
	    private String text;

	    // Getter 및 Setter 메서드 추가

	    // 생성자도 추가할 수 있습니다.

}
