package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Location;
import com.avalith.hotelo.dto.location.LocationDto;

public interface LocationQueryService extends FetchByIdService<LocationDto, Long> {
    Location findLocationById(Long id);
}
