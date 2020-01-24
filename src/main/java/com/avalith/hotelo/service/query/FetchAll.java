package com.avalith.hotelo.service.query;

import java.util.List;

public interface FetchAll<T> {
    List<T> findAll();
}
