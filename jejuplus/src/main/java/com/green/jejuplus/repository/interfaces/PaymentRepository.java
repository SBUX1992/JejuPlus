package com.green.jejuplus.repository.interfaces;

import org.apache.ibatis.annotations.Mapper;

import com.green.jejuplus.repository.model.Payment;

@Mapper
public interface PaymentRepository {
	
	public int insert(Payment payment);
	
	public Payment findById(int paymentId);
}
