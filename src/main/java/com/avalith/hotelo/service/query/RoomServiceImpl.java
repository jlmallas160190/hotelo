package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Room;
import com.avalith.hotelo.enums.RoomStatusEnum;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomQueryService {
    private final RoomRepository roomRepository;

    @Override
    public Room findRoomById(Long id) {
        try {
            Optional<Room> roomOptional = roomRepository.findById(id);
            if (!roomOptional.isPresent()) {
                throw new NotFoundException(String.format("The room with the id %s not found!", id));
            }
            return roomOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public Room findRoomAvailable(Long id, Boolean isEdit) {
        Room room = findRoomById(id);
        if (isEdit) {
            return room;
        }
        String message;
        if (room.getStatus().equals(RoomStatusEnum.CLEANING)) {
            message = String.format("The room %s is in cleaning", room.getName());
            throw new ConflictException(message);
        }
        if (room.getStatus().equals(RoomStatusEnum.MAINTENANCE)) {
            message = String.format("The room %s is in maintenance", room.getName());
            throw new ConflictException(message);
        }
        if (room.getStatus().equals(RoomStatusEnum.OCCUPIED)) {
            message = String.format("The room %s is in occupied", room.getName());
            throw new ConflictException(message);
        }
        return room;
    }
}
