package com.pragma.plazadecomidas.authservice.infrastructure.configuration;

import com.pragma.plazadecomidas.authservice.domain.api.IUserServicePort;
import com.pragma.plazadecomidas.authservice.domain.api.impl.UserServicePortImpl;
import com.pragma.plazadecomidas.authservice.domain.spi.IRolePersistencePort;
import com.pragma.plazadecomidas.authservice.domain.spi.IUserPersistencePort;
import com.pragma.plazadecomidas.authservice.domain.util.ValidationUtils;
import com.pragma.plazadecomidas.authservice.infrastructure.output.adapter.RoleJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.output.adapter.UserJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IRoleRepository;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IUserRepository;
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
    private final ValidationUtils validationUtils;

    @Bean
    public IUserServicePort userServicePort() {
        return new UserServicePortImpl(userPersistencePort(), rolePersistencePort(), passwordEncoder(), validationUtils);
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
