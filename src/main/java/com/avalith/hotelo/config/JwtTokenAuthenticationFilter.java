package com.avalith.hotelo.config;


import com.avalith.hotelo.dto.jwt.UserJwt;
import com.avalith.hotelo.service.command.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            log.error("{}", response);
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {

            UserJwt userJwt = jwtService.getUserDetails(token, jwtService.getJwtSecret());
            if (userJwt.getSubject() != null) {
                log.info("User {} authenticated", userJwt.getSubject());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userJwt.getSubject(), null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("error when the user is authenticate: {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }

}
