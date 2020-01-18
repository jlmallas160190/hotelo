package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_cart", uniqueConstraints = @UniqueConstraint(name = "idx_cart_id",
        columnNames = {"id"}))
public class Cart extends AbstractEntity {
    @Column(nullable = false)
    private BigDecimal subtotal;
    @Column(nullable = false)
    private BigDecimal total;
    @Column(nullable = false)
    private BigDecimal tax;
    @Column(nullable = false)
    private BigDecimal discount;
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Location location;
    @OneToOne
    private Order order;
}
