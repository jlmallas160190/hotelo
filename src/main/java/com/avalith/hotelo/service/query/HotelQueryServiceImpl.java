package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Hotel;
import com.avalith.hotelo.dto.*;
import com.avalith.hotelo.dto.location.LocationDto;
import com.avalith.hotelo.dto.room.RoomDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelQueryServiceImpl implements HotelQueryService {
    private final HotelRepository hotelRepository;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public Hotel findHotelById(Long id) {
        try {
            Optional<Hotel> hotelOptional = hotelRepository.findById(id);
            if (!hotelOptional.isPresent()) {
                throw new NotFoundException(String.format("The hotel with the id %s not found!", id));
            }
            return hotelOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public List<LocationDto> getLocations(Hotel hotel) {
        List<LocationDto> locationDtoList = new ArrayList<>();
        hotel.getLocations().forEach(location -> {
            LocationDto locationDto = dozerBeanMapper.map(location, LocationDto.class);
            location.getSections().forEach(section -> {
                SectionDto sectionDto = dozerBeanMapper.map(section, SectionDto.class);
                section.getRooms().forEach(room -> {
                    RoomDto roomDto = dozerBeanMapper.map(room, RoomDto.class);
                    roomDto.setPriceDtoList(room.getPrices().stream().map(price -> dozerBeanMapper.map(price, PriceDto.class)).collect(Collectors.toList()));
                    sectionDto.getRoomDtoList().add(roomDto);
                });
                locationDto.getSectionDtoList().add(sectionDto);
            });
            locationDtoList.add(locationDto);
        });
        return locationDtoList;
    }

    @Override
    public List<HotelDto> findAll() {
        try {
            List<HotelDto> hotelDtoList = new ArrayList<>();
            hotelRepository.findAll().stream().forEach(hotel -> {
                        HotelDto hotelDto = dozerBeanMapper.map(hotel, HotelDto.class);
                        hotelDto.setLocationDtoList(getLocations(hotel));
                        hotelDtoList.add(hotelDto);
                    }
            );
            log.info("{} hotels fetched!", hotelDtoList.size());
            return hotelDtoList;
        } catch (ConflictException | NotFoundException e) {
            log.error("{}", e);
            throw e;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public HotelDto findByID(Long id) {
        try {
            Hotel hotelFound = findHotelById(id);
            HotelDto hotelDto = dozerBeanMapper.map(hotelFound, HotelDto.class);
            hotelDto.setLocationDtoList(getLocations(hotelFound));
            return hotelDto;
        } catch (ConflictException | NotFoundException e) {
            log.error("{}", e);
            throw e;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
