package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Hotel;
import com.avalith.hotelo.domain.Location;
import com.avalith.hotelo.domain.Room;
import com.avalith.hotelo.domain.Section;
import com.avalith.hotelo.dto.HotelDto;
import com.avalith.hotelo.dto.LocationDto;
import com.avalith.hotelo.dto.RoomDto;
import com.avalith.hotelo.dto.SectionDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.HotelRepository;
import com.avalith.hotelo.service.query.HotelQueryService;
import com.avalith.hotelo.service.query.LocationQueryService;
import com.avalith.hotelo.service.query.RoomQueryService;
import com.avalith.hotelo.service.query.SectionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelCommandServiceImpl implements HotelCommandService {
    private final HotelRepository hotelRepository;
    private final LocationQueryService locationQueryService;
    private final RoomQueryService roomQueryService;
    private final HotelQueryService hotelQueryService;
    private final SectionQueryService sectionQueryService;
    private DozerBeanMapper dozerBeanMapper;

    private void updateHotel(Hotel hotel, HotelDto hotelDto) {
        hotelDto.getLocationDtoList().forEach(locationDto -> addLocation(hotel, locationDto));
    }

    private Hotel mergeRequestToEntity(HotelDto source, Hotel destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    private HotelDto buildResponse(Hotel hotel) {
        HotelDto hotelDto = dozerBeanMapper.map(hotel, HotelDto.class);
        hotel.getLocations().forEach(location -> {
            LocationDto locationDto = dozerBeanMapper.map(location, LocationDto.class);
            location.getSections().forEach(section -> {
                SectionDto sectionDto = dozerBeanMapper.map(section, SectionDto.class);
                section.getRooms().forEach(room -> sectionDto.getRoomDtoList().add(dozerBeanMapper.map(room, RoomDto.class)));
                locationDto.getSectionDtoList().add(sectionDto);
            });
            hotelDto.getLocationDtoList().add(locationDto);
        });
        return hotelDto;
    }

    private void addLocation(Hotel hotel, LocationDto locationDto) {
        Location location;
        if (locationDto.getId() == null) {
            location = dozerBeanMapper.map(locationDto, Location.class);

        } else {
            location = locationQueryService.findLocationById(locationDto.getId());
            dozerBeanMapper.map(locationDto, location);
        }

        locationDto.getSectionDtoList().forEach(sectionDto -> addSection(location, sectionDto));

        hotel.addLocation(location);
    }

    private void addSection(Location location, SectionDto sectionDto) {
        Section section;
        if (sectionDto.getId() == null) {
            section = dozerBeanMapper.map(sectionDto, Section.class);
        } else {
            section = sectionQueryService.findSectionById(sectionDto.getId());
            dozerBeanMapper.map(sectionDto, section);
        }

        sectionDto.getRoomDtoList().forEach(roomDto -> addRoom(section, roomDto));

        location.addSection(section);
    }

    private void addRoom(Section section, RoomDto roomDto) {
        Room room;
        if (roomDto.getId() == null) {
            room = dozerBeanMapper.map(roomDto, Room.class);
        } else {
            room = roomQueryService.findRoomById(roomDto.getId());
            dozerBeanMapper.map(roomDto, room);
        }

        section.addRoom(room);
    }

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public HotelDto add(HotelDto data) {
        try {
            Hotel hotelMerged = dozerBeanMapper.map(data, Hotel.class);
            updateHotel(hotelMerged, data);
            Hotel hotelSaved = hotelRepository.save(hotelMerged);
            log.info("hotel {} saved!", hotelSaved.getId());
            data.setId(hotelSaved.getId());
            return buildResponse(hotelSaved);

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public HotelDto edit(HotelDto data, Long id) {
        try {
            Hotel hotel = hotelQueryService.findHotelById(id);
            Hotel hotelMerged = mergeRequestToEntity(data, hotel);
            updateHotel(hotelMerged, data);
            Hotel hotelUpdated = hotelRepository.save(hotelMerged);
            log.info("hotel {} updated!", hotelUpdated.getId());
            return buildResponse(hotelUpdated);
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
