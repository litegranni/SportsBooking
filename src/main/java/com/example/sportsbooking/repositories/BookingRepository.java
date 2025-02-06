package com.example.sportsbooking.repositories;

import com.example.sportsbooking.models.Booking;
import com.example.sportsbooking.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByEventAndDateAndTime(Event event, String date, String time);
}
