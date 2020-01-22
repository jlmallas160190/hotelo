package com.avalith.hotelo.service.command;

import com.avalith.hotelo.domain.User;
import com.avalith.hotelo.dto.UserDto;
import com.avalith.hotelo.exceptions.ConflictException;
import com.avalith.hotelo.exceptions.NotFoundException;
import com.avalith.hotelo.repository.UserRepository;
import com.avalith.hotelo.service.query.PasswordService;
import com.avalith.hotelo.utils.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private DozerBeanMapper dozerBeanMapper;

    @PostConstruct
    public void init() {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private Boolean isPasswordCorrect(String password) {
        return Validator.isValidPassword(password);
    }

    private Boolean alreadyExistsUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }

    private boolean usernameChanged(String oldUsername, String usernameToUpdate) {
        return !oldUsername.equals(usernameToUpdate);
    }

    private User mergeRequestToEntity(UserDto source, User destination) {
        dozerBeanMapper.map(source, destination);
        return destination;
    }

    @Override
    public UserDto add(UserDto data) {
        try {
            User userMerged = dozerBeanMapper.map(data, User.class);
            if (!isPasswordCorrect(data.getPassword())) {
                throw new ConflictException("Password invalid!");
            }
            if (alreadyExistsUsername(data.getUsername())) {
                throw new ConflictException("Username already exists!");
            }
            userMerged.setPassword(passwordService.generatePassword(data.getPassword()));
            User userCreated = userRepository.save(userMerged);
            log.info("user {} created!", userCreated.getUsername());
            data.setId(userCreated.getId());
            return data;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }

    @Override
    public UserDto edit(UserDto data, Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                throw new ConflictException("User not found");
            }
            User userMerged = mergeRequestToEntity(data, userOptional.get());
            if (data.getPassword() != null && !isPasswordCorrect(data.getPassword())) {
                throw new ConflictException("Password invalid!");
            }
            if (usernameChanged(userOptional.get().getUsername(), data.getUsername()) && alreadyExistsUsername(data.getUsername())) {
                throw new ConflictException("Username already exists!");
            }
            userMerged.setPassword(passwordService.generatePassword(data.getPassword()));
            User userUpdated = userRepository.save(userMerged);
            log.info("user {} updated!", userUpdated.getUsername());
            return data;
        } catch (ConflictException | NotFoundException ex) {
            log.error("{}", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("{}", ex);
            throw new ConflictException(ex.getMessage());
        }
    }
}
