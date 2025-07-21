package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        return userEntityMapper.toUser(userRepository.save(userEntityMapper.toUserEntity(user)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDniNumber(String dniNumber) {
        return userRepository.existsByDniNumber(dniNumber);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhone(phoneNumber);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .map(userEntityMapper::toUser);
    }
}
