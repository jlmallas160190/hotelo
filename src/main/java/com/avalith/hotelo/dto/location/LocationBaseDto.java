package com.avalith.hotelo.dto.location;

import com.avalith.hotelo.dto.AbstractDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationBaseDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @NotBlank(message = "{field_required}")
    private String phone;
    @NotBlank(message = "{field_required}")
    private String city;
    private String postalCode;
    @NotBlank(message = "{field_required}")
    private String address;
}
