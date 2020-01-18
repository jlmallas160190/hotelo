package com.avalith.hotelo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hotelo_token", uniqueConstraints = @UniqueConstraint(name = "idx_token_id", columnNames = {"id"}))
@Data
@EqualsAndHashCode(callSuper = false)
public class Token extends AbstractEntity {
    @Lob
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String data;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date expired;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenStatus tokenStatus;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType tokenType;

    public enum TokenStatus {
        ACTIVE,
        PROCESSING,
        SUBMITTED,
        EXPIRED
    }

    public enum TokenType {
        CONFIRM_ACCOUNT,
        SIGN_IN,
    }
}
