package com.avalith.hotelo.dto.order;

import com.avalith.hotelo.dto.AbstractDto;
import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.dto.cart.CartBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends AbstractDto {
    private CartBaseDto cartDto;
    private CustomerDto customerDto;
    private String orderNumber;
    private String orderType;
    private String paidStatus;
    private BigDecimal tax;
    private BigDecimal total;
    private BigDecimal subtotal;
    private BigDecimal discount;
    List<OrderItemDto> orderItemDtoList = new ArrayList<>();
}
