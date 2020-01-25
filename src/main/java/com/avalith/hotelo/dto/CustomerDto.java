package com.avalith.hotelo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto extends AbstractDto {
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
