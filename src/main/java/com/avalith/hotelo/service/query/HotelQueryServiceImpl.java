package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Hotel;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelQueryServiceImpl implements HotelQueryService {
    private final HotelRepository hotelRepository;

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
}
