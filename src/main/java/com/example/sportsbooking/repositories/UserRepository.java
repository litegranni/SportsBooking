package com.example.sportsbooking.repositories;

import com.example.sportsbooking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Metod för att hitta en användare baserat på e-post
    Optional<User> findByEmail(String email);
}
