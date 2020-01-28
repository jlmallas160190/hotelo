package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.OrderDetail;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderDetailQueryServiceImpl implements OrderDetailQueryService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail findOrderDetailById(Long id) {
        try {
            Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
            if (!orderDetailOptional.isPresent()) {
                throw new NotFoundException(String.format("The order detail with the id %s not found!", id));
            }
            return orderDetailOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
