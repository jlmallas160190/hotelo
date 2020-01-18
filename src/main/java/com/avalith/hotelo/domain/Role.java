package com.avalith.hotelo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "hotelo_role", uniqueConstraints = @UniqueConstraint(name = "idx_role_id", columnNames = {"id"}))
@Data
public class Role extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @Column(length = 500)
    private String description;
}
