package com.avalith.hotelo.service.query;

import com.avalith.hotelo.domain.Section;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SectionQueryServiceImpl implements SectionQueryService {
    private final SectionRepository sectionRepository;
    @Override
    public Section findSectionById(Long id) {
        try {
            Optional<Section> sectionOptional = sectionRepository.findById(id);
            if (!sectionOptional.isPresent()) {
                throw new NotFoundException(String.format("The section with the id %s not found!", id));
            }
            return sectionOptional.get();

        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
