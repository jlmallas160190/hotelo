package com.avalith.hotelo.dto.room;

import com.avalith.hotelo.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomBaseDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @Min(value = 0, message = "{field_number_greater_zero}")
    private Integer bedNumber;
    private String status;
}
