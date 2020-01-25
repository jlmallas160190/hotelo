package com.avalith.hotelo.dto;

import com.avalith.hotelo.dto.location.LocationBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto extends AbstractDto {
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal subtotal = BigDecimal.ZERO;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal total = BigDecimal.ZERO;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal tax = BigDecimal.ZERO;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal discount = BigDecimal.ZERO;
    @NotNull(message = "{field_null}")
    @Valid
    private LocationBaseDto locationDto;
    @Valid
    private List<CartItemDto> cartItemDtoList = new ArrayList<>();
}
