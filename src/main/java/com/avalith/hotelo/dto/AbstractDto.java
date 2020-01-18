package com.avalith.hotelo.dto;

import lombok.Data;

@Data
public class AbstractDto {
    private Long id;
    private boolean active;

    public AbstractDto() {
        active = true;
    }
}
