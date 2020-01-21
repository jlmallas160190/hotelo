package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.User;

public interface PasswordService {
    String generatePassword(String passwordTextPlain);

    String generatePasswordPlain();

    boolean checkPassword(User user, String passwordToCheck);

    boolean checkPassword(String savedPassword, String passwordToCheck);
}
