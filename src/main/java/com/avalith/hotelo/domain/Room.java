package com.avalith.hotelo.domain;

import com.avalith.hotelo.enums.RoomStatusEnum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_room", uniqueConstraints = @UniqueConstraint(name = "idx_room_id",
        columnNames = {"id"}))
public class Room extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Min(value = 0)
    @Column(nullable = false)
    private Integer bedNumber;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices = new ArrayList<>();
    @ManyToOne
    @JoinColumn
    private Section section;
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum status;

    public Room() {
        this.status = RoomStatusEnum.AVAILABLE;
    }

    public void addPrice(Price priceToAdd) {
        boolean contains = prices.stream().filter(Price::getActive).anyMatch(price ->
                price.equals(priceToAdd));
        if (!contains) {
            prices.add(priceToAdd);
            priceToAdd.setRoom(this);
        }
    }

}
