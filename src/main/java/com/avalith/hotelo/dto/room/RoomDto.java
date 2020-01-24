package com.avalith.hotelo.dto.room;

import com.avalith.hotelo.dto.PriceDto;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDto extends RoomBaseDto {
    @Valid
    private List<PriceDto> priceDtoList = new ArrayList<>();

}
