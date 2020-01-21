package com.avalith.hotelo.service.command;

import com.avalith.hotelo.dto.LoginDto;
import com.avalith.hotelo.dto.UserLoggedDto;

public interface AuthenticationService {
    UserLoggedDto authenticate(LoginDto loginDto);
}
