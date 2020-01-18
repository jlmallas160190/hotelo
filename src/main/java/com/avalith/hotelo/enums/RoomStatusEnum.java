package com.avalith.hotelo.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoomStatusEnum {
    CLEANING("CLEANING", "Cleaning"),
    MAINTENANCE("MAINTENANCE", "Maintenance"),
    OCCUPIED("OCCUPIED", "Occupied"),
    AVAILABLE("AVAILABLE", "Available");
    private String type;
    private String value;

    RoomStatusEnum(String type, String value) {
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
