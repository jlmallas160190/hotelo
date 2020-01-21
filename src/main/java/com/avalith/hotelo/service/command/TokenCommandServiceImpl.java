package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Token;
import com.avalith.hotelo.domain.User;
import com.avalith.hotelo.dto.TokenDto;
import com.avalith.hotelo.dto.jwt.UserJwt;
import com.avalith.hotelo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCommandServiceImpl implements TokenCommandService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public TokenDto createAndSave(User user) {
        List<Token> tokenList = tokenRepository.findAllByUser_IdAndTokenStatusAndTokenType(user.getId(), Token.TokenStatus.ACTIVE, Token.TokenType.SIGN_IN);
        if (!tokenList.isEmpty()) {
            Token token = tokenList.get(0);
            if (token.getExpired().after(new Date())) {
                return dozerBeanMapper.map(token, TokenDto.class);
            } else {
                token.setTokenStatus(Token.TokenStatus.EXPIRED);
                token.setActive(false);
                tokenRepository.save(token);
            }
        }

        Date expirationDate = new Date(System.currentTimeMillis() + 86400000);
        UserJwt userJwt = new UserJwt();
        userJwt.setSubject(user.getUsername());
        userJwt.setJti(user.getId().toString());
        userJwt.setExpiration(expirationDate);
        String data = jwtService.createJwt(userJwt);
        Token token = generateToken(expirationDate, Token.TokenType.SIGN_IN, "Bearer ".concat(data));
        token.setUser(user);
        Token tokenSaved = tokenRepository.save(token);
        return dozerBeanMapper.map(tokenSaved, TokenDto.class);
    }

    @Override
    public Token generateToken(Date expirationDate, Token.TokenType tokenType, String data) {
        Token token = new Token();
        token.setTokenType(tokenType);
        token.setData(data);
        token.setExpired(expirationDate);
        token.setActive(true);
        token.setTokenStatus(Token.TokenStatus.ACTIVE);
        return token;
    }
}
