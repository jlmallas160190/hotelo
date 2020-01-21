package com.avalith.hotelo.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class EncryptService {
    public static String ALGORITHM = "SHA-512";
    public static int ITERATIONS = 64000;
    public static int SALT_SIZE = 64;

    public static String encryptPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = calculateHash(password, salt);
        return Base64.getEncoder().encodeToString(salt).concat(":").concat(Base64.getEncoder().encodeToString(hash));
    }

    public static byte[] calculateHash(String password, byte[] salt) {
        byte[] hash = new byte[64];
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.reset();
            md.update(salt);
            hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            for (int i = 0; i < ITERATIONS; i++) {
                md.reset();
                hash = md.digest(hash);
            }

        } catch (NoSuchAlgorithmException ex) {
            log.info(ex.getMessage());
        }
        return hash;
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean verifyPassword(String password, byte[] originalHash, byte[] salt) {
        byte[] comparisonHash = calculateHash(password, salt);
        return comparePasswords(originalHash, comparisonHash);
    }

    private static boolean comparePasswords(byte[] originalHash, byte[] comparisonHash) {
        int diff = originalHash.length ^ comparisonHash.length;
        for (int i = 0; i < originalHash.length && i < comparisonHash.length; i++) {
            diff |= originalHash[i] ^ comparisonHash[i];
        }
        return diff == 0;
    }

    public static boolean isPasswordCorrect(String oldPassword, String passwordUpdate) {
        String saltPart = oldPassword.split(":")[0];
        String hashPart = oldPassword.split(":")[1];
        byte[] salt = Base64.getDecoder().decode(saltPart);
        byte[] hash = Base64.getDecoder().decode(hashPart);
        return verifyPassword(passwordUpdate, hash, salt);
    }
}
