package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Price;

public interface PriceQueryService {
    Price findPriceById(Long id);
}
