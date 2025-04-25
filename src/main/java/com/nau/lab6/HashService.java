package com.nau.lab6;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    public String generateSHA1Hash(String input) {
        try {
            // Отримуємо екземпляр об'єкта MessageDigest для SHA-1
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            // Перетворюємо вхідний рядок в масив байтів
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Перетворюємо байти в шістнадцяткове представлення
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Помилка при створенні SHA-1 геш-значення", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean verifyIntegrity(String input, String expectedHash) {
        String calculatedHash = generateSHA1Hash(input);
        return calculatedHash.equalsIgnoreCase(expectedHash);
    }
}
