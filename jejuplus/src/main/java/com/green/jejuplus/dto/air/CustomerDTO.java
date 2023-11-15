package com.green.jejuplus.dto.air;

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
public class CustomerDTO {
	
	private String username;
	private String phone;
	private String email;

}
