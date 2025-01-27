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
        // Rensa databasen innan varje test för att säkerställa ett rent tillstånd
        userRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindUser() {
        // Skapa en ny användare
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        // Spara användaren i databasen
        User savedUser = userRepository.save(user);

        // Kontrollera att användaren sparades korrekt
        assertNotNull(savedUser.getId(), "Användarens ID bör inte vara null efter sparning");

        // Hämta användaren från databasen
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Kontrollera att användaren hittades
        assertTrue(foundUser.isPresent(), "Användaren bör hittas med sitt ID");
        assertEquals("testuser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testFindByEmail() {
        // Skapa och spara en användare
        User user = new User();
        user.setUsername("emailuser");
        user.setEmail("email@example.com");
        user.setPassword("securepassword");
        userRepository.save(user);

        // Hämta användaren via e-post
        Optional<User> foundUser = userRepository.findByEmail("email@example.com");

        // Kontrollera att rätt användare hittades
        assertTrue(foundUser.isPresent(), "Användaren bör hittas med e-post");
        assertEquals("emailuser", foundUser.get().getUsername());

        // Testa med en ogiltig e-postadress
        Optional<User> notFoundUser = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(notFoundUser.isPresent(), "Ingen användare bör hittas för en ogiltig e-postadress");
    }

    @Test
    public void testDeleteUser() {
        // Skapa och spara en användare
        User user = new User();
        user.setUsername("deleteuser");
        user.setEmail("delete@example.com");
        user.setPassword("deletepassword");
        User savedUser = userRepository.save(user);

        // Radera användaren
        userRepository.deleteById(savedUser.getId());

        // Kontrollera att användaren inte längre finns i databasen
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent(), "Användaren bör inte finnas efter radering");
    }

    @Test
    public void testFindById_NotFound() {
        // Försök att hämta en användare med ett ogiltigt ID
        Optional<User> foundUser = userRepository.findById(999L);

        // Kontrollera att ingen användare hittades
        assertFalse(foundUser.isPresent(), "Ingen användare bör hittas med ett ogiltigt ID");
    }
}
