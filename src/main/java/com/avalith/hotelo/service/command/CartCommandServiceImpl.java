package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Cart;
import com.avalith.hotelo.domain.CartItem;
import com.avalith.hotelo.domain.Location;
import com.avalith.hotelo.domain.Room;
import com.avalith.hotelo.dto.cart.CartDto;
import com.avalith.hotelo.dto.cart.CartItemDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.CartRepository;
import com.avalith.hotelo.service.query.CartItemQueryService;
import com.avalith.hotelo.service.query.CartQueryService;
import com.avalith.hotelo.service.query.LocationQueryService;
import com.avalith.hotelo.service.query.RoomQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartCommandServiceImpl implements CartCommandService {
    private final CartRepository cartRepository;
    private final CartQueryService cartQueryService;
    private final LocationQueryService locationQueryService;
    private final CartItemQueryService cartItemQueryService;
    private final RoomQueryService roomQueryService;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private void updateCart(Cart cart, CartDto cartDto) {
        Location location = locationQueryService.findLocationById(cartDto.getLocationDto().getId());
        cart.setLocation(location);
        cartDto.getCartItemDtoList().stream().forEach(cartItemDto -> addCartItem(cart, cartItemDto));
        cart.setSubtotal(cart.calculateSubtotal());
        cart.setTax(cart.calculateTax());
        cart.setTotal(cart.calculateTotal());
    }

    private void addCartItem(Cart cart, CartItemDto cartItemDto) {
        CartItem cartItem;
        Room room;
        if (cartItemDto.getId() == null) {
            cartItem = dozerBeanMapper.map(cartItemDto, CartItem.class);
            room = roomQueryService.findRoomAvailable(cartItemDto.getRoomBaseDto().getId(), false);
        } else {
            cartItem = cartItemQueryService.findCartItemById(cartItemDto.getId());
            room = roomQueryService.findRoomAvailable(cartItemDto.getRoomBaseDto().getId(),
                    roomChanged(cartItem.getRoom().getId(), cartItemDto.getRoomBaseDto().getId()));
        }
        cartItem.setRoom(room);
        cart.addCartItem(cartItem);
        cartItem.setQuantity(cartItem.calculateQuantity());
        cartItem.setTotal(cartItem.calculateTotal());
    }

    private boolean roomChanged(Long olderRoomId, Long roomIdToUpdate) {
        return olderRoomId.equals(roomIdToUpdate);
    }

    private Cart mergeRequestToEntity(CartDto source, Cart destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    private CartDto buildResponse(Cart cart) {
        CartDto cartDto = dozerBeanMapper.map(cart, CartDto.class);
        cartDto.setLocationDto(cartQueryService.getLocationDto(cart));
        cartDto.setCartItemDtoList(cartQueryService.getCartItemDtoList(cart));
        return cartDto;
    }

    @Override
    public CartDto add(CartDto data) {
        try {
            Cart cartMerged = dozerBeanMapper.map(data, Cart.class);
            updateCart(cartMerged, data);
            Cart cartSaved = cartRepository.save(cartMerged);
            log.info("cart {} saved!", cartSaved.getId());
            data.setId(cartSaved.getId());
            return buildResponse(cartSaved);

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public CartDto edit(CartDto data, Long id) {
        try {
            Cart cart = cartQueryService.findCartById(id);
            Cart cartMerged = mergeRequestToEntity(data, cart);
            updateCart(cartMerged, data);
            Cart cartUpdated = cartRepository.save(cartMerged);
            log.info("cart {} updated!", cartUpdated.getId());
            return buildResponse(cartUpdated);
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
