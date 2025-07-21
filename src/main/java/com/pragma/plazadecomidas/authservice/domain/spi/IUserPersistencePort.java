package com.pragma.plazadecomidas.authservice.domain.spi;

import com.pragma.plazadecomidas.authservice.domain.model.User;

import java.util.Optional;

public interface  IUserPersistencePort {

    User saveUser(User user);

    boolean existsByEmail(String email);

    boolean existsByDniNumber(String dniNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findById(Long id);

}
