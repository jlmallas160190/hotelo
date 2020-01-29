package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private Timestamp created;
    @Version
    @Column
    private Timestamp updated;
    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @PrePersist
    public void createAt() {
        created = new Timestamp(System.currentTimeMillis());
        active = Boolean.TRUE;
    }
}
