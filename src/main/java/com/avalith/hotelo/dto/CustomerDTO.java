package com.avalith.hotelo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerDTO extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String identificationNumber;
    @NotBlank(message = "{field_required}")
    private String firstName;
    @NotBlank(message = "{field_required}")
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
