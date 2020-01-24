package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Customer;
import com.avalith.hotelo.dto.CustomerDto;

public interface CustomerQueryService extends FetchAllByPageable<CustomerDto> {
    Customer findCustomerById(Long id);
}
