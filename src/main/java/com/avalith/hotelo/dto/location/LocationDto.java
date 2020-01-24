package com.avalith.hotelo.dto.location;

import com.avalith.hotelo.dto.SectionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto extends LocationBaseDto {
    @Valid
    private List<SectionDto> sectionDtoList = new ArrayList<>();
}
