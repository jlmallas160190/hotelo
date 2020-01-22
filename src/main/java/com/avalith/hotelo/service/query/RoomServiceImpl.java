package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Room;
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
}
