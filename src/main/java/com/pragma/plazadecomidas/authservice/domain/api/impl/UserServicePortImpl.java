package com.pragma.plazadecomidas.authservice.domain.api.impl;

import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedBadRequestException;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedNotFoundException;
import com.pragma.plazadecomidas.authservice.domain.model.MessageEnum;
import com.pragma.plazadecomidas.authservice.domain.model.Role;
import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.domain.util.ValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServicePortImpl implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtils validationUtils;

    @Override
    public User saveOwner(User user) {

        if (!validationUtils.isValid(user.getName())) {
            throw new PersonalizedBadRequestException(MessageEnum.NAME_REQUIRED.getMessage());
        }
        if (!validationUtils.isValid(user.getLastName())) {
            throw new PersonalizedBadRequestException(MessageEnum.LASTNAME_REQUIRED.getMessage());
        }
        if (!validationUtils.isValid(user.getIdentityDocument())) {
            throw new PersonalizedBadRequestException(MessageEnum.DOCUMENT_REQUIRED.getMessage());
        }
        if (!validationUtils.containsOnlyNumbers(user.getIdentityDocument())) {
            throw new PersonalizedBadRequestException(MessageEnum.DOCUMENT_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getPhoneNumber())) {
            throw new PersonalizedBadRequestException(MessageEnum.PHONE_REQUIRED.getMessage());
        }
        if (!validationUtils.isValidPhoneStructure(user.getPhoneNumber())) {
            throw new PersonalizedBadRequestException(MessageEnum.PHONE_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getEmail())) {
            throw new PersonalizedBadRequestException(MessageEnum.EMAIL_REQUIRED.getMessage());
        }
        if (!validationUtils.isValidEmailStructure(user.getEmail())) {
            throw new PersonalizedBadRequestException(MessageEnum.EMAIL_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getPassword())) {
            throw new PersonalizedBadRequestException(MessageEnum.PASSWORD_REQUIRED.getMessage());
        }
        if (user.getBirthDate() == null) {
            throw new PersonalizedBadRequestException(MessageEnum.BIRTHDATE_REQUIRED.getMessage());
        }
        if (!validationUtils.isBirthDateNotFuture(user.getBirthDate())) {
            throw new PersonalizedException(MessageEnum.BIRTHDATE_PAST.getMessage());
        }
        if (!validationUtils.isAdult(user.getBirthDate())) {
            throw new PersonalizedException(MessageEnum.BIRTHDATE_ADULT.getMessage());
        }

        userPersistencePort.findByEmail(user.getEmail())
                .ifPresent(userEmail -> {
                    throw new PersonalizedException(MessageEnum.EMAIL_ALREADY_EXISTS.getMessage());
                });

        userPersistencePort.findByPhone(user.getPhoneNumber())
                .ifPresent(userPhone -> {
                    throw new PersonalizedException(MessageEnum.PHONE_ALREADY_EXISTS.getMessage());
                });

        userPersistencePort.findByIdentification(user.getIdentityDocument())
                .ifPresent(userIdentification -> {
                    throw new PersonalizedException(MessageEnum.DOCUMENT_ALREADY_EXISTS.getMessage());
                });

        Role ownerRole = rolePersistencePort.findByName(MessageEnum.PROPIETARIO.getMessage())
                .orElseThrow(() -> new PersonalizedNotFoundException(MessageEnum.ROLE_NOT_FOUND.getMessage()));

        return Optional.of(user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .roleId(String.valueOf(ownerRole.getId()))
                        .build())
                .map(userPersistencePort::save)
                .map(savedUser -> savedUser.toBuilder().roleName(ownerRole.getName()).build())
                .orElseThrow(() -> new PersonalizedNotFoundException(MessageEnum.ROLE_NOT_FOUND.getMessage()));
    }

    @Override
    public User isOwner(Long userId) {
        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new PersonalizedNotFoundException(MessageEnum.USER_NOT_FOUND.getMessage()));

        return rolePersistencePort.findById(Long.valueOf(user.getRoleId()))
                .map(role -> user.toBuilder()
                        .roleId(String.valueOf(role.getId()))
                        .roleName(role.getName())
                        .build())
                .orElseThrow(() -> new PersonalizedNotFoundException(MessageEnum.ROLE_NOT_FOUND.getMessage()));
    }

}
