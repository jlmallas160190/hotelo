package com.avalith.hotelo.dto;

import com.avalith.hotelo.dto.room.RoomBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDto extends AbstractDto {
    @NotNull(message = "{field_null}")
    private Date startDate;
    @NotNull(message = "{field_null}")
    private Date endDate;
    private String name;
    @Min(value = 1, message = "{field_number_greater_zero}")
    private Integer quantity;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal unitPrice = BigDecimal.ZERO;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal total = BigDecimal.ZERO;
    @Min(value = 0, message = "{field_number_greater_zero}")
    @Digits(integer = 12, fraction = 2, message = "{invalid_number_format}")
    private BigDecimal tax = BigDecimal.ZERO;
    @Valid
    @NotNull(message = "{field_null}")
    private RoomBaseDto roomBaseDto;
}
