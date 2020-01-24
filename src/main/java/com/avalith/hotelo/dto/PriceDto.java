package com.avalith.hotelo.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class PriceDto extends AbstractDto {
    @NotBlank(message = "{field_required}")
    private String name;
    @Min(value = 0, message = "{field_number_greater_zero}")
    private Integer priceOrder;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 4, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal price = BigDecimal.ZERO;
}
