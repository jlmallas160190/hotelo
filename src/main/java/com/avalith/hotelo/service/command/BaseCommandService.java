package com.avalith.hotelo.service.command;

public interface BaseCommandService<T, ID> {
    T add(T data);

    T edit(T data, ID id);

}
