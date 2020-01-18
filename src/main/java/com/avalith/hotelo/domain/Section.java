package com.avalith.hotelo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "hotelo_section", uniqueConstraints = @UniqueConstraint(name = "idx_section_id",
        columnNames = {"id"}))
public class Section extends AbstractEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Location location;
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
}
