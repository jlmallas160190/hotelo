package com.avalith.hotelo.domain;

import com.avalith.hotelo.enums.RoomStatusEnum;
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

    public void addCartItem(CartItem cartItemToAdd) {
        boolean contains = cartItems.stream().filter(CartItem::getActive).anyMatch(cartItem ->
                cartItem.equals(cartItemToAdd));
        if (!contains) {
            cartItems.add(cartItemToAdd);
            cartItemToAdd.getRoom().setStatus(RoomStatusEnum.OCCUPIED);
            cartItemToAdd.setCart(this);
        }
    }

    public BigDecimal calculateTotal() {
        return this.subtotal.add(this.tax).subtract(this.discount);
    }

    public BigDecimal calculateSubtotal() {
        return this.cartItems.stream().map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTax() {
        return this.cartItems.stream().map(CartItem::getTax).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
