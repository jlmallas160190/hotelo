package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
