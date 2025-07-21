package com.pragma.plazadecomidas.authservice.infrastructure.configuration;

import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.domain.usecase.UserUseCase;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), rolePersistencePort(), passwordEncoder());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
