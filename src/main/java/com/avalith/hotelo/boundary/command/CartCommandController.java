package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.CartDto;
import com.avalith.hotelo.service.command.CartCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
@Api(value = "/cart")
public class CartCommandController {
    private final CartCommandService cartCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDto> createEntity(@Valid @RequestBody CartDto cartDto) {
        CartDto response = cartCommandService.add(cartDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody CartDto request) {
        CartDto response = cartCommandService.edit(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
