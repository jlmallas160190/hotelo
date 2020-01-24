package com.avalith.hotelo.dto;

import com.avalith.hotelo.dto.room.RoomDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class SectionDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @Valid
    private List<RoomDto> roomDtoList = new ArrayList<>();
}
