package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Cart;
import com.avalith.hotelo.dto.cart.CartDto;
import com.avalith.hotelo.dto.cart.CartItemDto;
import com.avalith.hotelo.dto.location.LocationBaseDto;

import java.util.List;

public interface CartQueryService extends FetchAll<CartDto>, FetchByIdService<CartDto, Long> {
    Cart findCartById(Long id);

    List<CartItemDto> getCartItemDtoList(Cart cart);

    LocationBaseDto getLocationDto(Cart cart);
}
