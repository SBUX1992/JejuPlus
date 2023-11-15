package com.green.jejuplus.util;
import java.security.SecureRandom;
import java.util.Random;

public class RandomPasswordGenerator {
    // 사용할 문자셋 (알파벳 대문자, 알파벳 소문자, 숫자)
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 랜덤 비밀번호 길이
    private static final int PASSWORD_LENGTH = 12;

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public static void main(String[] args) {
        String randomPassword = generateRandomPassword();
        System.out.println("임시 비밀번호: " + randomPassword);
    }
}
