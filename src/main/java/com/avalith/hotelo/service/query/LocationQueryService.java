package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Location;

public interface LocationQueryService {
    Location findLocationById(Long id);
}
