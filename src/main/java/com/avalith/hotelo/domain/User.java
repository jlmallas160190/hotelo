package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hotelo_user", uniqueConstraints = @UniqueConstraint(name = "idx_user_id", columnNames = {"id"}))
@Data
public class User extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(name = "is_super_user")
    private Boolean superUser;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hotelo_user_rol",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "rol_id")})
    private Set<Role> roles = new HashSet<>();

}
