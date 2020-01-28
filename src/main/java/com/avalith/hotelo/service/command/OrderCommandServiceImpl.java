package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Cart;
import com.avalith.hotelo.domain.Customer;
import com.avalith.hotelo.domain.Order;
import com.avalith.hotelo.domain.OrderDetail;
import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.dto.cart.CartBaseDto;
import com.avalith.hotelo.dto.order.OrderDto;
import com.avalith.hotelo.enums.OrderPaidStatusEnum;
import com.avalith.hotelo.enums.OrderTypeEnum;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.OrderDetailRepository;
import com.avalith.hotelo.repository.OrderRepository;
import com.avalith.hotelo.service.query.CartQueryService;
import com.avalith.hotelo.service.query.CustomerQueryService;
import com.avalith.hotelo.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {
    private final OrderRepository orderRepository;
    private final CustomerQueryService customerQueryService;
    private final CartQueryService cartQueryService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderQueryService orderQueryService;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private void updateOrder(Order order, OrderDto orderDto) {
        addCustomer(order, orderDto.getCustomerDto());
        if (orderDto.getCartDto() == null) {
            throw new ConflictException("Cart not found");
        }
        if (orderDto.getCartDto().getId() == null) {
            throw new ConflictException(String.format("Cart %s not found", orderDto.getCartDto().getId()));
        }
        Cart cart = cartQueryService.findCartById(orderDto.getCartDto().getId());
        order.setCart(cart);
        order.setDiscount(cart.getDiscount());
        order.setIssueDate(new Date());
        order.setOrderType(OrderTypeEnum.INVOICE);
        order.setTotal(cart.getTotal());
        order.setSubtotal(cart.getSubtotal());
        order.setPaidStatus(OrderPaidStatusEnum.PAID);
        order.setOrderNumber("default");
        order.setTax(cart.getTax());
        cart.getCartItems().forEach(cartItem -> {
            Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(cartItem.getId());
            OrderDetail orderDetail;
            if (!orderDetailOptional.isPresent()) {
                orderDetail = new OrderDetail();
                orderDetail.setTotal(cartItem.getTotal());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTax(cartItem.getTax());
                orderDetail.setName(cartItem.getName());
                orderDetail.setUnitPrice(cartItem.getUnitPrice());
                orderDetail.setCartItem(cartItem);
            } else {
                orderDetail = orderDetailOptional.get();
                orderDetail.setName(cartItem.getName());
                orderDetail.setTax(cartItem.getTax());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotal(cartItem.getTotal());
            }
            order.addDetail(orderDetail);
        });

    }

    private Order mergeRequestToEntity(OrderDto source, Order destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    private OrderDto buildResponse(Order order) {
        OrderDto orderDto = dozerBeanMapper.map(order, OrderDto.class);
        orderDto.setCartDto(dozerBeanMapper.map(order.getCart(), CartBaseDto.class));
        orderDto.setOrderItemDtoList(orderQueryService.getDetails(order));
        return orderDto;
    }

    private void addCustomer(Order order, CustomerDto customerDto) {
        Customer customer;
        if (customerDto.getId() == null) {
            customer = dozerBeanMapper.map(customerDto, Customer.class);
        } else {
            customer = customerQueryService.findCustomerById(customerDto.getId());
            dozerBeanMapper.map(customerDto, customer);
        }
        log.info("Customer {} added!", customerDto.getIdentificationNumber());
        order.setCustomer(customer);
    }

    @Override
    public OrderDto add(OrderDto data) {
        try {
            Order orderMerged = dozerBeanMapper.map(data, Order.class);
            updateOrder(orderMerged, data);
            Order orderSaved = orderRepository.save(orderMerged);
            log.info("order saved {}!", orderSaved.getId());
            return buildResponse(orderSaved);
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public OrderDto edit(OrderDto data, Long id) {
        try {
            Order order = orderQueryService.findOrderById(id);
            Order orderMerged = mergeRequestToEntity(data, order);
            updateOrder(order, data);
            Order orderUpdated = orderRepository.save(orderMerged);
            log.info("order saved {}!", orderUpdated.getId());
            return buildResponse(orderUpdated);
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
