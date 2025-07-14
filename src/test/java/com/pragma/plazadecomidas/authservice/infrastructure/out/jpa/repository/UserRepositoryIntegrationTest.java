package com.pragma.plazadecomidas.authservice.infrastructure.out.jpa.repository;

import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.RoleEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.plazadecomidas.authservice.infrastructure.output.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private UserEntity testUserEntity;
    private RoleEntity testRoleEntity;

    @BeforeEach
    void setUp() {

        testRoleEntity = new RoleEntity(null, "ADMIN", "Rol de administrador");
        entityManager.persistAndFlush(testRoleEntity);

        testUserEntity = new UserEntity(
                null,
                "Carlos",
                "Gomez",
                "10001000",
                "+573123456789",
                "carlos@example.com",
                "hashedPassword",
                LocalDate.of(1980, 7, 10),
                testRoleEntity
        );

    }

    @Test
    @DisplayName("Should save a user successfully")
    void saveUser_ShouldSaveSuccessfully() {
        // WHEN
        UserEntity savedUser = userRepository.save(testUserEntity);

        // THEN
        assertNotNull(savedUser.getId());
        assertEquals(testUserEntity.getEmail(), savedUser.getEmail());
        assertEquals(testUserEntity.getIdentityDocument(), savedUser.getIdentityDocument());
        assertEquals(testUserEntity.getBirthDate(), savedUser.getBirthDate());
        assertEquals(testUserEntity.getRole().getName(), savedUser.getRole().getName());
    }

    @Test
    @DisplayName("Should find user by email when email exists")
    void findByEmail_ShouldReturnUserWhenExists() {
        // GIVEN
        entityManager.persistAndFlush(testUserEntity);
        String existingEmail = testUserEntity.getEmail();

        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByEmail(existingEmail);

        // THEN
        assertTrue(foundUser.isPresent());
        assertEquals(existingEmail, foundUser.get().getEmail());
    }

    @Test
    @DisplayName("Should not find user by email when email does not exist")
    void findByEmail_ShouldReturnEmptyWhenNotExists() {
        // GIVEN
        String nonExistentEmail = "nonexistent@example.com";

        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByEmail(nonExistentEmail);

        // THEN
        assertFalse(foundUser.isPresent());
    }

    @Test
    @DisplayName("Should find user by document number when document number exists")
    void findByDocumentNumber_ShouldReturnUserWhenExists() {
        // GIVEN
        entityManager.persistAndFlush(testUserEntity);
        String existingDocument = testUserEntity.getIdentityDocument();

        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByIdentityDocument(existingDocument);

        // THEN
        assertTrue(foundUser.isPresent());
        assertEquals(existingDocument, foundUser.get().getIdentityDocument());
    }

    @Test
    @DisplayName("Should not find user by document number when document number does not exist")
    void findByDocumentNumber_ShouldReturnEmptyWhenNotExists() {
        // GIVEN
        String nonExistentDocument = "999999999";

        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByIdentityDocument(nonExistentDocument);

        // THEN
        assertFalse(foundUser.isPresent());
    }
}
