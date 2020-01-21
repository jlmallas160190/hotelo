package com.avalith.hotelo.service.command;

import com.avalith.hotelo.dto.jwt.UserJwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Data
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public String createJwt(UserJwt userJwt) {
        if (userJwt.getSubject() == null) {
            log.error("the user not exists {}", userJwt.getSubject());
            return null;
        }

        return Jwts
                .builder()
                .setId(userJwt.getJti())
                .setSubject(userJwt.getSubject())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(userJwt.getExpiration())
                .signWith(SignatureAlgorithm.HS512,
                        jwtSecret.getBytes()).compact();
    }

    public UserJwt getUserDetails(String token, String key) {
        Claims claims = Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(token)
                .getBody();

        UserJwt userJwt = new UserJwt();
        userJwt.setExpiration(claims.getExpiration());
        userJwt.setJti(claims.getId());
        userJwt.setSubject(claims.getSubject());
        return userJwt;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }
}
