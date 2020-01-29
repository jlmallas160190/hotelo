package com.avalith.hotelo.boundary.query;

import com.avalith.hotelo.dto.room.RoomDto;
import com.avalith.hotelo.service.query.RoomQueryService;
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
@RequestMapping("/room")
@Api(value = "/room")
public class RoomQueryController {
    private RoomQueryService roomQueryService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<RoomDto> findCareerById(@PathVariable(value = "id") @Valid Long id) {
        RoomDto roomDto = roomQueryService.findByID(id);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }

}
