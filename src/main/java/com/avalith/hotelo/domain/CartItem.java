package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "hotelo_cart_item", uniqueConstraints = @UniqueConstraint(name = "idx_cart_item_id",
        columnNames = {"id"}))
public class CartItem extends AbstractEntity {
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
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
    private Room room;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cart cart;
}
