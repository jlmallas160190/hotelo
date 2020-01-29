package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Order;
import com.avalith.hotelo.dto.cart.CartBaseDto;
import com.avalith.hotelo.dto.cart.CartItemDto;
import com.avalith.hotelo.dto.order.OrderDto;
import com.avalith.hotelo.dto.order.OrderItemDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.OrderRepository;
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
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderRepository orderRepository;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public Order findOrderById(Long id) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (!orderOptional.isPresent()) {
                throw new NotFoundException(String.format("The order with the id %s not found!", id));
            }
            return orderOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public List<OrderItemDto> getDetails(Order order) {
        try {
            List<OrderItemDto> orderItemDtoList = new ArrayList<>();
            order.getOrderDetails().stream().forEach(orderDetail -> {
                        OrderItemDto orderItemDto = dozerBeanMapper.map(orderDetail, OrderItemDto.class);
                        orderItemDto.setCartItemDto(dozerBeanMapper.map(orderDetail.getCartItem(), CartItemDto.class));
                        orderItemDtoList.add(orderItemDto);
                    }
            );
            return orderItemDtoList;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public OrderDto findByID(Long id) {
        try {
            Order order = findOrderById(id);
            OrderDto orderDto = dozerBeanMapper.map(order, OrderDto.class);
            orderDto.setCartDto(dozerBeanMapper.map(order.getCart(), CartBaseDto.class));
            orderDto.setOrderItemDtoList(getDetails(order));
            return orderDto;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
