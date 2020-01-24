package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Customer;
import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {
    private final CustomerRepository customerRepository;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    @Override
    public List<CustomerDto> findAll(Pageable pageable) {
        try {
            List<CustomerDto> customerDtoList = customerRepository.findAll(pageable).stream().map(customer -> dozerBeanMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
            log.info("{} customer fetched!", customerDtoList.size());
            return customerDtoList;
        } catch (ConflictException | NotFoundException e) {
            log.error("{}", e);
            throw e;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public Customer findCustomerById(Long id) {
        try {
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (!customerOptional.isPresent()) {
                throw new NotFoundException(String.format("The customer with the id %s not found!", id));
            }
            return customerOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
