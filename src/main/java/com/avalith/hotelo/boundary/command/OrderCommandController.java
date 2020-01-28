package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.order.OrderDto;
import com.avalith.hotelo.service.command.OrderCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
@Api(value = "/order")
public class OrderCommandController {
    private final OrderCommandService orderCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createEntity(@Valid @RequestBody OrderDto orderDto) {
        OrderDto response = orderCommandService.add(orderDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody OrderDto request) {
        OrderDto response = orderCommandService.edit(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
