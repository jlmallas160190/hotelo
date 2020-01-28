package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Room;
import com.avalith.hotelo.dto.room.RoomDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.RoomRepository;
import com.avalith.hotelo.service.query.RoomQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {
    private final RoomQueryService roomQueryService;
    private final RoomRepository roomRepository;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private Room mergeRequestToEntity(RoomDto source, Room destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    @Override
    public RoomDto add(RoomDto data) {
        return null;
    }

    @Override
    public RoomDto edit(RoomDto data, Long id) {
        try {
            Room room = roomQueryService.findRoomById(id);
            Room roomMerged = mergeRequestToEntity(data, room);
            Room roomSaved = roomRepository.save(roomMerged);
            log.info("Room saved {}", roomMerged.getName());
            return dozerBeanMapper.map(roomSaved, RoomDto.class);
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
