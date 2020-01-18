package com.avalith.hotelo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderTypeEnum {
    INVOICE("INVOICE", "Invoice"),
    PAYMENT("PAYMENT", "Payment");
    private String type;
    private String value;

    OrderTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
