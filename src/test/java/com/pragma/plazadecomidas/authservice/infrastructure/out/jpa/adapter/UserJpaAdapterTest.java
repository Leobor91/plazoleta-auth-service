package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.adapter;


import com.pragma.plazadecomidas.authservice.domain.model.User;
import com.pragma.plazadecomidas.authservice.infrastructure.output.adapter.UserJpaAdapter;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository; 
    @Mock
    private IUserEntityMapper userEntityMapper; 

    @InjectMocks
    private UserJpaAdapter userJpaAdapter; 

    private User domainUser;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {

        domainUser = new User(
                1L,
                "Pedro",
                "Gomez",
                "12345678",
                "+573001234567",
                "pedro@example.com",
                "hashedPass",
                LocalDate.of(1992, 3, 20),
                "1",
                "PROPIETARIO"
        );

        userEntity = new UserEntity(
                1L,
                "Pedro",
                "Gomez",
                "12345678",
                "+573001234567",
                "pedro@example.com",
                "hashedPass",
                LocalDate.of(1992, 3, 20),
                null);
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        
        // GIVEN
        when(userEntityMapper.toUserEntity(any(User.class))).thenReturn(userEntity);       
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);        
        when(userEntityMapper.toUser(any(UserEntity.class))).thenReturn(domainUser);

        // WHEN
        User savedUser = userJpaAdapter.save(domainUser);

        // THEN
        assertNotNull(savedUser);
        assertEquals(domainUser.getEmail(), savedUser.getEmail());
        verify(userEntityMapper, times(1)).toUserEntity(any(User.class));
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userEntityMapper, times(1)).toUser(any(UserEntity.class));
    }

    @Test
    void findByEmail_ShouldReturnUserWhenFound() {
        // GIVEN
        when(userRepository.findByEmail(domainUser.getEmail())).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(domainUser);

        // WHEN
        Optional<User> foundUser = userJpaAdapter.findByEmail(domainUser.getEmail());

        // THEN
        assertTrue(foundUser.isPresent());
        assertEquals(domainUser.getEmail(), foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail(domainUser.getEmail());
        verify(userEntityMapper, times(1)).toUser(userEntity);
    }

    @Test
    void findByEmail_ShouldReturnEmptyWhenNotFound() {
        // GIVEN
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // WHEN
        Optional<User> foundUser = userJpaAdapter.findByEmail("nonexistent@example.com");

        // THEN
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userEntityMapper, never()).toUser(any(UserEntity.class)); 
    
    }

    @Test
    void findByIdentityDocument_ShouldReturnUserWhenFound() {
        // GIVEN
        when(userRepository.findByIdentityDocument(domainUser.getIdentityDocument())).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(domainUser);

        // WHEN
        Optional<User> foundUser = userJpaAdapter.findByIdentification(domainUser.getIdentityDocument());

        // THEN
        assertTrue(foundUser.isPresent());
        assertEquals(domainUser.getIdentityDocument(), foundUser.get().getIdentityDocument());
        verify(userRepository, times(1)).findByIdentityDocument(domainUser.getIdentityDocument());
        verify(userEntityMapper, times(1)).toUser(userEntity);
    }

    @Test
    void findByIdentityDocument_ShouldReturnEmptyWhenNotFound() {
        // GIVEN
        when(userRepository.findByIdentityDocument(anyString())).thenReturn(Optional.empty());

        // WHEN
        Optional<User> foundUser = userJpaAdapter.findByIdentification("999999");

        // THEN
        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByIdentityDocument(anyString());
        verify(userEntityMapper, never()).toUser(any(UserEntity.class));
    }
}
