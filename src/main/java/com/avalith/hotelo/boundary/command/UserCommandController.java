package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.UserDto;
import com.avalith.hotelo.service.command.UserCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Api(value = "/user")
public class UserCommandController {
    private UserCommandService userCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createEntity(@Valid @RequestBody UserDto userRequest) {
        UserDto userResponse = userCommandService.add(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto userToUpdate) {
        UserDto userResponse = userCommandService.edit(userToUpdate, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
