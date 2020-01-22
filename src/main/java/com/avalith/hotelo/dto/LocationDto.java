package com.avalith.hotelo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class LocationDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @NotBlank(message = "{field_required}")
    private String phone;
    @NotBlank(message = "{field_required}")
    private String city;
    private String postalCode;
    @NotBlank(message = "{field_required}")
    private String address;
    @Valid
    private List<SectionDto> sectionDtoList = new ArrayList<>();
}
