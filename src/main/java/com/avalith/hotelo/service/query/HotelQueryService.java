package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Hotel;
import com.avalith.hotelo.dto.HotelDto;
import com.avalith.hotelo.dto.location.LocationDto;

import java.util.List;

public interface HotelQueryService extends FetchAll<HotelDto>, FetchByIdService<HotelDto, Long> {
    Hotel findHotelById(Long id);

    List<LocationDto> getLocations(Hotel hotel);
}
