package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.Customer;
import com.avalith.hotelo.dto.CustomerDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.CustomerRepository;
import com.avalith.hotelo.service.query.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCommandServiceImpl implements CustomerCommandService {
    private final CustomerRepository customerRepository;
    private final CustomerQueryService customerQueryService;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private Customer mergeRequestToEntity(CustomerDto source, Customer destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    @Override
    public CustomerDto add(CustomerDto data) {
        try {
            Customer customerMerged = dozerBeanMapper.map(data, Customer.class);
            Customer customerSaved = customerRepository.save(customerMerged);
            log.info("customer {} saved!", customerSaved.getId());
            return dozerBeanMapper.map(customerSaved, CustomerDto.class);

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public CustomerDto edit(CustomerDto data, Long id) {
        try {
            Customer customerFound = customerQueryService.findCustomerById(id);
            Customer customerMerged = mergeRequestToEntity(data, customerFound);
            Customer customerSaved = customerRepository.save(customerMerged);
            log.info("customer {} saved!", customerSaved.getId());
            return dozerBeanMapper.map(customerSaved, CustomerDto.class);

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
