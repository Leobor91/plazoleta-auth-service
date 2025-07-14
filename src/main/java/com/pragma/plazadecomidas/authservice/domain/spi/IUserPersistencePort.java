package com.pragma.plazadecomidas.authservice.domain.spi;

import com.pragma.plazadecomidas.authservice.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdentification(String identification);

    Optional<User> findByPhone(String phone);

    User save(User user);

}
