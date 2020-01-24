package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.CartItem;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartItemQueryServiceImpl implements CartItemQueryService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem findCartItemById(Long id) {
        try {
            Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
            if (!cartItemOptional.isPresent()) {
                throw new NotFoundException(String.format("The cart item with the id %s not found!", id));
            }
            return cartItemOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
