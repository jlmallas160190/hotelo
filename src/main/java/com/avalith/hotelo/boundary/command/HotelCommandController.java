package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.HotelDto;
import com.avalith.hotelo.service.command.HotelCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/hotel")
@Api(value = "/hotel")
public class HotelCommandController {
    private final HotelCommandService hotelCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDto> createEntity(@Valid @RequestBody HotelDto hotelDto) {
        HotelDto response = hotelCommandService.add(hotelDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody HotelDto request) {
        HotelDto response = hotelCommandService.edit(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
