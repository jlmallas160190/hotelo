package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Room;

public interface RoomQueryService {
    Room findRoomById(Long id);

    Room findRoomAvailable(Long id, Boolean isEdit);
}
