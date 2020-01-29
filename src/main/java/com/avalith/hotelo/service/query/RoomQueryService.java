package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Room;
import com.avalith.hotelo.dto.room.RoomDto;

public interface RoomQueryService extends FetchByIdService<RoomDto, Long> {
    Room findRoomById(Long id);

    Room findRoomAvailable(Long id, Boolean isEdit);
}
