package com.avalith.hotelo.service.query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FetchAllByPageable<T> {
    List<T> findAll(Pageable pageable);
}
