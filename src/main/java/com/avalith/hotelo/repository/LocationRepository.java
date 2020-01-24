package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
