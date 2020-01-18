package com.avalith.hotelo.service.query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FetchAllService<T, ID> {
    List<T> findAll(Pageable pageable);

    List<T> findAllBy(ID id);

    List<T> findAllBy(Object... params);

}
