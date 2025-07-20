package com.pragma.plazadecomidas.authservice.domain.usecase;

import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.DomainException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidAgeException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidDniFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.InvalidPhoneFormatException;
import com.pragma.plazadecomidas.authservice.domain.exception.NotFound;
import com.pragma.plazadecomidas.authservice.domain.exception.UserAlreadyExistsException;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.utils.ValidationConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveOwner(User user) {
        validateUserBusinessRules(user);
        Role ownerRole = rolePersistencePort.findByName(ValidationConstants.OWNER_ROLE_NAME)
                .orElseThrow(() -> new NotFound(ValidationConstants.ROLE_NOT_FOUND_MESSAGE));
        return Optional.ofNullable(user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                        .role(ownerRole)
                        .build())
                .map(userPersistencePort::saveUser)
                .map(saveOwner -> saveOwner.toBuilder()
                        .role(ownerRole)
                        .build())
                .orElseThrow(() -> new DomainException(ValidationConstants.NOT_SAVED_MESSAGE));


    }

    @Override
    public User isOwner(Long userId) {

        User userExists = userPersistencePort.findById(userId)
                .orElseThrow(() -> new NotFound(ValidationConstants.USER_NOT_FOUND_MESSAGE));
        return rolePersistencePort.findById(userExists.getRole().getId())
                .map(role -> userExists.toBuilder()
                        .role(role)
                        .build())
                .orElseThrow(() -> new NotFound(ValidationConstants.ROLE_NOT_FOUND_MESSAGE));
    }

    private void validateUserBusinessRules(User user) {

        if (user.getBirthDate().plusYears(ValidationConstants.MIN_AGE_FOR_ADULT).isAfter(LocalDate.now())) {
            throw new InvalidAgeException(ValidationConstants.USER_MUST_BE_ADULT_MESSAGE);
        }
        if (userPersistencePort.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(ValidationConstants.EMAIL_ALREADY_EXISTS_MESSAGE.concat(user.getEmail()));
        }
        if (userPersistencePort.existsByDniNumber(user.getDniNumber())) {
            throw new UserAlreadyExistsException(ValidationConstants.DNI_ALREADY_EXISTS_MESSAGE.concat(user.getDniNumber()));
        }
        if(userPersistencePort.existsByPhoneNumber(user.getPhone())){
            throw new UserAlreadyExistsException(ValidationConstants.PHONE_ALREADY_EXISTS_MESSAGE.concat(user.getPhone()));
        }
        if (!Pattern.matches(ValidationConstants.DNI_NUMBER_REGEX, user.getDniNumber())) {
            throw new InvalidDniFormatException(ValidationConstants.DNI_NUMBER_FORMAT_MESSAGE);
        }
        if (!Pattern.matches(ValidationConstants.PHONE_REGEX, user.getPhone())) {
            throw new InvalidPhoneFormatException(ValidationConstants.PHONE_FORMAT_MESSAGE);

        }
    }
}
