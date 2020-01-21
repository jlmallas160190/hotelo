package com.avalith.hotelo.boundary.query;


import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.service.query.CustomerQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
@Api(value = "/customer")
public class CustomerController {
    private final CustomerQueryService customerQueryService;

    @GetMapping
    public ResponseEntity<List> findAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        List<CustomerDto> careerDtoList = customerQueryService.findAll(PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(careerDtoList, HttpStatus.OK);
    }
}
