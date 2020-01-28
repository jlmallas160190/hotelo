package com.avalith.hotelo.domain;

import com.avalith.hotelo.enums.OrderPaidStatusEnum;
import com.avalith.hotelo.enums.OrderTypeEnum;
import com.avalith.hotelo.enums.RoomStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "hotelo_order", uniqueConstraints = @UniqueConstraint(name = "idx_order_id",
        columnNames = {"id"}))
public class Order extends AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date issueDate;
    @Column(nullable = false)
    private BigDecimal subtotal = BigDecimal.ZERO;
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(nullable = false)
    private BigDecimal tax = BigDecimal.ZERO;
    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderPaidStatusEnum paidStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;
    private String orderNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Customer customer;
    @OneToOne
    private Cart cart;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addDetail(OrderDetail orderDetailToAdd) {
        boolean contains = orderDetails.stream().filter(OrderDetail::getActive).anyMatch(orderDetail ->
                orderDetail.equals(orderDetailToAdd));
        if (!contains) {
            orderDetails.add(orderDetailToAdd);
            orderDetailToAdd.getCartItem().getRoom().setStatus(RoomStatusEnum.AVAILABLE);
            orderDetailToAdd.setOrder(this);
        }
    }

}
