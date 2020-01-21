package com.avalith.hotelo.service.command;

import com.avalith.hotelo.dto.jwt.UserJwt;


public interface JwtService {
    String createJwt(UserJwt userJwt);

    UserJwt getUserDetails(String token, String key);

    String getJwtSecret();
}
