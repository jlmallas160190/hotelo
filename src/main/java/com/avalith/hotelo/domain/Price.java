package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "hotelo_price", uniqueConstraints = @UniqueConstraint(name = "idx_price_id",
        columnNames = {"id"}))
public class Price extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer priceOrder;
    @Min(0)
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Room room;
}
