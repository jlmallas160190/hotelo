package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Cart;
import com.avalith.hotelo.dto.cart.CartDto;
import com.avalith.hotelo.dto.cart.CartItemDto;
import com.avalith.hotelo.dto.location.LocationBaseDto;
import com.avalith.hotelo.dto.room.RoomBaseDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartQueryServiceImpl implements CartQueryService {
    private final CartRepository cartRepository;

    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public List<CartDto> findAll() {
        return null;
    }

    @Override
    public CartDto findByID(Long id) {
        try {
            Cart cartFound = findCartById(id);
            CartDto cartDto = dozerBeanMapper.map(cartFound, CartDto.class);
            cartDto.setLocationDto(getLocationDto(cartFound));
            cartDto.setCartItemDtoList(getCartItemDtoList(cartFound));
            log.info("cart {} found", cartFound.getId());
            return cartDto;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public Cart findCartById(Long id) {
        try {
            Optional<Cart> cartOptional = cartRepository.findById(id);
            if (!cartOptional.isPresent()) {
                throw new NotFoundException(String.format("The cart with the id %s not found!", id));
            }
            return cartOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public List<CartItemDto> getCartItemDtoList(Cart cart) {
        try {
            List<CartItemDto> cartItemDtoList = new ArrayList<>();
            cart.getCartItems().stream().forEach(cartItem -> {
                CartItemDto cartItemDto = dozerBeanMapper.map(cartItem, CartItemDto.class);
                cartItemDto.setRoomBaseDto(dozerBeanMapper.map(cartItem.getRoom(), RoomBaseDto.class));
                cartItemDtoList.add(cartItemDto);
            });
            log.info("carts {} fetched!", cartItemDtoList.size());
            return cartItemDtoList;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public LocationBaseDto getLocationDto(Cart cart) {
        return dozerBeanMapper.map(cart.getLocation(), LocationBaseDto.class);
    }
}