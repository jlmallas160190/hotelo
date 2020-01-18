package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_hotel", uniqueConstraints = @UniqueConstraint(name = "idx_hotel_id",
        columnNames = {"id"}))
public class Hotel extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    private String email;
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();
}
