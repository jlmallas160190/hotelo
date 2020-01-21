package com.avalith.hotelo.dto;

import lombok.Data;

@Data
public class TokenDto extends AbstractDto {
    private String data;
    private UserDto userDto;
}
