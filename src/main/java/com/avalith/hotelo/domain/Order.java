package com.avalith.hotelo.domain;

import com.avalith.hotelo.enums.OrderPaidStatusEnum;
import com.avalith.hotelo.enums.OrderTypeEnum;
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
    private BigDecimal subtotal;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private BigDecimal tax;
    @Column(nullable = false)
    private BigDecimal discount;
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
}
