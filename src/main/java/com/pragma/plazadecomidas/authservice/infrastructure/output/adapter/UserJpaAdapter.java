package com.pragma.plazadecomidas.authservice.infrastructure.output.adapter;

import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser);
    }

    @Override
    public Optional<User> findByIdentification(String identification) {
        return userRepository.findByIdentityDocument(identification)
                .map(userEntityMapper::toUser);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhoneNumber(phone)
                .map(userEntityMapper::toUser);
    }

    @Override
    public User save(User user) {
        return userEntityMapper.toUser(userRepository.save(userEntityMapper.toUserEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toUser);
    }
}
