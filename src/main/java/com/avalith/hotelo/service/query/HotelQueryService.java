package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Hotel;

public interface HotelQueryService {
    Hotel findHotelById(Long id);
}
