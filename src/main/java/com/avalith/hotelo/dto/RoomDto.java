package com.avalith.hotelo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @Min(value = 0, message = "{field_number_greater_zero}")
    private Integer bedNumber;
    @Valid
    private List<PriceDto> priceDtoList = new ArrayList<>();

}
