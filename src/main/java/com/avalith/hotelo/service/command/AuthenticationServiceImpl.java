package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.User;
import com.avalith.hotelo.dto.LoginDto;
import com.avalith.hotelo.dto.UserLoggedDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.UserRepository;
import com.avalith.hotelo.service.query.PasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final TokenCommandService tokenCommandService;

    @Override
    public UserLoggedDto authenticate(LoginDto loginDto) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());
            if (!userOptional.isPresent()) {
                throw new NotFoundException("User not found!");
            }
            User user = userOptional.get();

            if (!passwordService.checkPassword(user, loginDto.getPassword())) {
                throw new ConflictException("Password incorrect!");
            }
            UserLoggedDto userLoggedDto = new UserLoggedDto();
            userLoggedDto.setSessionId(tokenCommandService.createAndSave(user).getData());
            userLoggedDto.setUsername(user.getUsername());
            log.info("user {} logged!", userLoggedDto.getUsername());
            return userLoggedDto;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
