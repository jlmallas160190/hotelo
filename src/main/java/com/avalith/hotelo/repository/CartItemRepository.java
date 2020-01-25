package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
