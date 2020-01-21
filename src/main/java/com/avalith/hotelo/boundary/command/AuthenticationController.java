package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.LoginDto;
import com.avalith.hotelo.dto.UserLoggedDto;
import com.avalith.hotelo.service.command.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
@Api(value = "/login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoggedDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
        UserLoggedDto response = authenticationService.authenticate(loginDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
