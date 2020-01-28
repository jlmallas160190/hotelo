package com.avalith.hotelo.dto.order;

import com.avalith.hotelo.dto.cart.CartItemBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDto {
    private CartItemBaseDto cartItemDto;
}
