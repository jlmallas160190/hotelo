package com.avalith.hotelo.dto;

import lombok.Data;

@Data
public class UserDto extends AbstractDto {
    private String username;
    private String password;
}
