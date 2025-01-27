package com.example.sportsbooking.repositories;

import com.example.sportsbooking.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
