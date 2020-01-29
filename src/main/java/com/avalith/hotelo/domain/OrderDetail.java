package com.avalith.hotelo.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "hotelo_order_detail", uniqueConstraints = @UniqueConstraint(name = "idx_order_detail_id",
        columnNames = {"id"}))
public class OrderDetail extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer quantity;
    @Min(0)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false)
    private BigDecimal unitPrice;
    @Min(0)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false)
    private BigDecimal total;
    @Min(0)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false)
    private BigDecimal tax;
    @ManyToOne
    @JoinColumn(nullable = false)
    private CartItem cartItem;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

}