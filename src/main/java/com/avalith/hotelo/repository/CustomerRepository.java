package com.avalith.hotelo.repository;

import com.avalith.hotelo.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
