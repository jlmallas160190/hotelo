package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
