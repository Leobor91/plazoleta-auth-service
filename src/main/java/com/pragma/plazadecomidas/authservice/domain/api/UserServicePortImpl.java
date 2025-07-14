package com.pragma.plazadecomidas.authservice.domain.api;

import com.pragma.plazadecomidas.authservice.domain.exception.PersonalizedException;
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
            throw new PersonalizedException(MessageEnum.NAME_REQUIRED.getMessage());
        }
        if (!validationUtils.isValid(user.getLastName())) {
            throw new PersonalizedException(MessageEnum.LASTNAME_REQUIRED.getMessage());
        }
        if (!validationUtils.isValid(user.getIdentityDocument())) {
            throw new PersonalizedException(MessageEnum.DOCUMENT_REQUIRED.getMessage());
        }
        if (!validationUtils.containsOnlyNumbers(user.getIdentityDocument())) {
            throw new PersonalizedException(MessageEnum.DOCUMENT_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getPhoneNumber())) {
            throw new PersonalizedException(MessageEnum.PHONE_REQUIRED.getMessage());
        }
        if (!validationUtils.isValidPhoneStructure(user.getPhoneNumber())) {
            throw new PersonalizedException(MessageEnum.PHONE_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getEmail())) {
            throw new PersonalizedException(MessageEnum.EMAIL_REQUIRED.getMessage());
        }
        if (!validationUtils.isValidEmailStructure(user.getEmail())) {
            throw new PersonalizedException(MessageEnum.EMAIL_FORMAT.getMessage());
        }
        if (!validationUtils.isValid(user.getPassword())) {
            throw new PersonalizedException(MessageEnum.PASSWORD_REQUIRED.getMessage());
        }
        if (user.getBirthDate() == null) {
            throw new PersonalizedException(MessageEnum.BIRTHDATE_REQUIRED.getMessage());
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
                .orElseThrow(() -> new PersonalizedException(MessageEnum.ROLE_NOT_FOUND.getMessage()));

        User userToSave = user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .roleId(String.valueOf(ownerRole.getId()))
                .build();

        return Optional.of(userToSave)
                .map(userPersistencePort::save)
                .map(savedUser -> savedUser.toBuilder().roleName(ownerRole.getName()).build())
                .orElseThrow(() -> new PersonalizedException(MessageEnum.ROLE_NOT_FOUND.getMessage()));
    }

    @Override
    public boolean isOwner(Long userId) {
        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new PersonalizedException(MessageEnum.USER_NOT_FOUND.getMessage()));

        return rolePersistencePort.findById(Long.valueOf(user.getRoleId()))
                .map(role -> role.getName().equalsIgnoreCase(MessageEnum.PROPIETARIO.getMessage()))
                .orElse(false);
    }

}
