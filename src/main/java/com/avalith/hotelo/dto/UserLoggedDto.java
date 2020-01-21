package com.avalith.hotelo.dto;

import lombok.Data;

@Data
public class UserLoggedDto {
    private String sessionId;
    private String username;
}
