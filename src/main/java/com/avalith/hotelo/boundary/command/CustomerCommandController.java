package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.service.command.CustomerCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
@Api(value = "/customer")
public class CustomerCommandController {
    private final CustomerCommandService customerCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createEntity(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto response = customerCommandService.add(customerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody CustomerDto request) {
        CustomerDto response = customerCommandService.edit(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
