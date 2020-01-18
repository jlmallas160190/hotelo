package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_location")
public class Location extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    private String phone;
    private String city;
    private String postalCode;
    private String address;
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Hotel hotel;
}
