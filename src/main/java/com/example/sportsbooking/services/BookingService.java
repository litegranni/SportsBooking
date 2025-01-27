package com.example.sportsbooking.services;

import com.example.sportsbooking.models.Booking;
import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.models.User;
import com.example.sportsbooking.repositories.BookingRepository;
import com.example.sportsbooking.repositories.EventRepository;
import com.example.sportsbooking.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Booking createBooking(Booking booking) {
        if (booking.getEvent() == null || booking.getEvent().getId() == null) {
            throw new IllegalArgumentException("Event or Event ID cannot be null");
        }

        if (booking.getUser() == null || booking.getUser().getId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null");
        }

        Event event = eventRepository.findById(booking.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        booking.setEvent(event);
        booking.setUser(user);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public Booking updateBooking(Booking booking) {
        if (booking.getId() == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        if (!bookingRepository.existsById(booking.getId())) {
            throw new RuntimeException("Booking not found");
        }
        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    @Autowired
    private EntityManager entityManager;

    public void testDatabaseConnection() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
