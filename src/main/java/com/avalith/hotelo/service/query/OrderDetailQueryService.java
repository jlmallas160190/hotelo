package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.OrderDetail;

public interface OrderDetailQueryService {
    OrderDetail findOrderDetailById(Long id);
}
