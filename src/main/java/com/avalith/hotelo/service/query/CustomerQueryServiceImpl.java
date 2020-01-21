package com.avalith.hotelo.service.query;

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
    public List<CustomerDto> findAllBy(Long aLong) {
        throw new NotFoundException("Service not available");
    }

    @Override
    public List<CustomerDto> findAllBy(Object... params) {
        throw new NotFoundException("Service not available");
    }
}
