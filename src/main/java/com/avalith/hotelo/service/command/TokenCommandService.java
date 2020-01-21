package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Token;
import com.avalith.hotelo.domain.User;
import com.avalith.hotelo.dto.TokenDto;

import java.util.Date;

public interface TokenCommandService {
    TokenDto createAndSave(User user);

    Token generateToken(Date expirationDate, Token.TokenType tokenType, String data);
}
