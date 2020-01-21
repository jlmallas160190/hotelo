package com.avalith.hotelo.dto.jwt;

import lombok.Data;

import java.util.Date;

@Data
public class UserJwt {
    private String jti;
    private String subject;
    private Date expiration;
}
