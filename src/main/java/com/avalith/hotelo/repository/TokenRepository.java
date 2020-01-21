package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser_IdAndTokenStatusAndTokenType(Long userId, Token.TokenStatus tokenStatus, Token.TokenType tokenType);
}
