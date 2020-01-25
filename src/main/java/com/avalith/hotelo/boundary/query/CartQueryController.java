package com.avalith.hotelo.boundary.query;

import com.avalith.hotelo.dto.CartDto;
import com.avalith.hotelo.service.query.CartQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
@Api(value = "/cart")
public class CartQueryController {
    private final CartQueryService cartQueryService;

    @GetMapping
    public ResponseEntity<List> findAll() {
        List<CartDto> cartDtoList = cartQueryService.findAll();
        return new ResponseEntity<>(cartDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CartDto> findCareerById(@PathVariable(value = "id") @Valid Long id) {
        CartDto cartDto = cartQueryService.findByID(id);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
