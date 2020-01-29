package com.avalith.hotelo.boundary.command;

import com.avalith.hotelo.dto.room.RoomDto;
import com.avalith.hotelo.service.command.RoomCommandService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/room")
@Api(value = "/room")
public class RoomCommandController {
    private final RoomCommandService roomCommandService;

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomDto> updateEntity(@PathVariable(value = "id") Long id, @Valid @RequestBody RoomDto request) {
        RoomDto response = roomCommandService.edit(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
