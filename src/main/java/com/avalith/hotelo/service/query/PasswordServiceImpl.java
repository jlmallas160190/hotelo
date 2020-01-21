package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.User;
import com.avalith.hotelo.utils.EncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_*.";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int PASSWORD_SIZE = 8;

    public String generatePassword(String passwordTextPlain) {
        return EncryptService.encryptPassword(passwordTextPlain);
    }

    @Override
    public String generatePasswordPlain() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PASSWORD_SIZE; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public boolean checkPassword(User user, String passwordToCheck) {
        return EncryptService.isPasswordCorrect(user.getPassword(), passwordToCheck);
    }

    public boolean checkPassword(String savedPassword, String passwordToCheck) {
        return EncryptService.isPasswordCorrect(savedPassword, passwordToCheck);
    }

}
