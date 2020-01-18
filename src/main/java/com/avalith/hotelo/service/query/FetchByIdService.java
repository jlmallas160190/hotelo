package com.avalith.hotelo.service.query;

public interface FetchByIdService<T, ID> {
    T findByID(ID id);
}
