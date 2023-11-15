package com.green.jejuplus.dto.payment;

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
public class PaymentDTO {

	private int paymentId;
	private int userId;
	private String pgTid;
	private String created_at;
}
