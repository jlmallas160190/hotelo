package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
