package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Order;
import com.avalith.hotelo.dto.order.OrderDto;
import com.avalith.hotelo.dto.order.OrderItemDto;

import java.util.List;

public interface OrderQueryService extends FetchByIdService<OrderDto, Long> {
    Order findOrderById(Long id);

    List<OrderItemDto> getDetails(Order order);
}
