package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_customer", uniqueConstraints = @UniqueConstraint(name = "idx_customer_id",
        columnNames = {"id"}))
public class Customer extends AbstractEntity {
    @Column(nullable = false)
    private String identificationNumber;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String email;
    private String phone;
    private String address;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    @ManyToOne
    private User user;
}

