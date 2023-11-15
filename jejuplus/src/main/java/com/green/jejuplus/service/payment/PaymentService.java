package com.green.jejuplus.service.payment;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.jejuplus.dto.payment.PaymentDTO;
import com.green.jejuplus.repository.interfaces.PaymentRepository;
import com.green.jejuplus.repository.model.Payment;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Transactional
	public void insertPayment(PaymentDTO paymentDTO, int userId) {
	    System.out.println("[service] paymentDTO start : " + paymentDTO);

	    Payment payment = new Payment();
	    // PaymentDTO에서 필요한 정보를 Payment 객체에 복사
	    payment.setUserId(userId);
	    payment.setPgTid(paymentDTO.getPgTid());
	    
	    paymentDTO.setPaymentId(payment.getPaymentId());
	    System.out.println("[service] paymentId : " + paymentDTO.getPaymentId());

	    System.out.println("[service] paymentDTO end : " + paymentDTO);

	    // paymentRepository를 사용하여 Payment 객체를 저장
	    int result = paymentRepository.insert(payment);
	    
	    // db에 있는 정보 끌어옴
	    Payment savedPayment = paymentRepository.findById(payment.getPaymentId());
        System.out.println("[service] Saved Payment Info: " + savedPayment);
	}
	
	// 결제 번호
	public Payment payNumber(int paymentId) {
		Payment result = paymentRepository.findById(paymentId);
		return result;
	}
}
