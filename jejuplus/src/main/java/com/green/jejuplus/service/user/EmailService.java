package com.green.jejuplus.service.user;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jejuplus@google.com");
        message.setTo(to);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + code);

        javaMailSender.send(message);
    }
    
    public void sendUsername(String email, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jejuplus@google.com");
        message.setTo(email);
        message.setSubject("고객 아이디");
        message.setText("고객님의 아이디는 " + username + "입니다.");

        javaMailSender.send(message);
    }

	
}