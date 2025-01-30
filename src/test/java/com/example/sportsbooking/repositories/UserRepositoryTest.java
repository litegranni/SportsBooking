package com.example.sportsbooking.repositories;

import com.example.sportsbooking.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Säkerställer att application-test.yml används
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Använder den konfigurerade MySQL-databasen
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Given: Databasen rensas innan varje test
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndRetrieveUserSuccessfully() {
        // Given: En ny användare skapas
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        // When: Användaren sparas i databasen
        User savedUser = userRepository.save(user);

        // Then: Användaren ska kunna hämtas från databasen med korrekt data
        assertNotNull(savedUser.getId(), "Användarens ID bör inte vara null efter sparning");

        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent(), "Användaren bör hittas med sitt ID");
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    public void shouldFindUserByEmail() {
        // Given: En användare med en viss e-postadress sparas i databasen
        User user = new User();
        user.setUsername("emailuser");
        user.setEmail("email@example.com");
        user.setPassword("securepassword");
        userRepository.save(user);

        // When: En sökning görs efter användaren med e-postadressen
        Optional<User> foundUser = userRepository.findByEmail("email@example.com");

        // Then: Rätt användare returneras
        assertTrue(foundUser.isPresent(), "Användaren bör hittas med e-post");
        assertEquals("emailuser", foundUser.get().getUsername());

        // When: En sökning görs efter en ogiltig e-postadress
        Optional<User> notFoundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then: Ingen användare ska hittas
        assertFalse(notFoundUser.isPresent(), "Ingen användare bör hittas för en ogiltig e-postadress");
    }

    @Test
    public void shouldDeleteUserById() {
        // Given: En användare sparas i databasen
        User user = new User();
        user.setUsername("deleteuser");
        user.setEmail("delete@example.com");
        user.setPassword("deletepassword");
        User savedUser = userRepository.save(user);

        // When: Användaren raderas med sitt ID
        userRepository.deleteById(savedUser.getId());

        // Then: Användaren bör inte längre finnas i databasen
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent(), "Användaren bör inte finnas efter radering");
    }

    @Test
    public void shouldReturnEmptyWhenUserIdNotFound() {
        // Given: Ingen användare med det angivna ID:t finns i databasen

        // When: En sökning görs med ett ogiltigt ID
        Optional<User> foundUser = userRepository.findById(999L);

        // Then: Ingen användare ska hittas
        assertFalse(foundUser.isPresent(), "Ingen användare bör hittas med ett ogiltigt ID");
    }
}
