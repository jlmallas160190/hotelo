package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Price;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceQueryServiceImpl implements PriceQueryService {
    private final PriceRepository priceRepository;

    @Override
    public Price findPriceById(Long id) {
        try {
            Optional<Price> priceOptional = priceRepository.findById(id);
            if (!priceOptional.isPresent()) {
                throw new NotFoundException(String.format("The price with the id %s not found!", id));
            }
            return priceOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
