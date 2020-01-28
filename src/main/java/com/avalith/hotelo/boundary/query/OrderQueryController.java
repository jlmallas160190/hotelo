package com.avalith.hotelo.boundary.query;

import com.avalith.hotelo.dto.order.OrderDto;
import com.avalith.hotelo.service.query.OrderQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
@Api(value = "/order")
public class OrderQueryController {
    private OrderQueryService orderQueryService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDto> findCareerById(@PathVariable(value = "id") @Valid Long id) {
        OrderDto orderDto = orderQueryService.findByID(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}
