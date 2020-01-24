package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Location;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationQueryServiceImpl implements LocationQueryService {
    private final LocationRepository locationRepository;

    @Override
    public Location findLocationById(Long id) {
        try {
            Optional<Location> locationOptional = locationRepository.findById(id);
            if (!locationOptional.isPresent()) {
                throw new NotFoundException(String.format("The location with the id %s not found!", id));
            }
            return locationOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
