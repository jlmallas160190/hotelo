package com.avalith.hotelo.boundary.query;

import com.avalith.hotelo.dto.HotelDto;
import com.avalith.hotelo.service.query.HotelQueryService;
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
@RequestMapping("/hotel")
@Api(value = "/hotel")
public class HotelQueryController {
    private final HotelQueryService hotelQueryService;

    @GetMapping
    public ResponseEntity<List> findAll() {
        List<HotelDto> hotelDtoList = hotelQueryService.findAll();
        return new ResponseEntity<>(hotelDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HotelDto> findCareerById(@PathVariable(value = "id") @Valid Long id) {
        HotelDto hotelDto = hotelQueryService.findByID(id);
        return new ResponseEntity<>(hotelDto, HttpStatus.OK);
    }
}
