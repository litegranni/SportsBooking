package com.example.sportsbooking.repositories;

import com.example.sportsbooking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}

