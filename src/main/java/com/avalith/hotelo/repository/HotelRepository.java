package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
