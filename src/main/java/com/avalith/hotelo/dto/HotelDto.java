package com.avalith.hotelo.dto;

import com.avalith.hotelo.dto.location.LocationDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @NotBlank(message = "{field_required}")
    private String email;
    @Valid
    private List<LocationDto> locationDtoList = new ArrayList<>();
}
