package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.CartItem;

public interface CartItemQueryService {
    CartItem findCartItemById(Long id);
}
