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

    @Autowired
    private EntityManager entityManager;

    //Skapa en bokning med validering för Event och User, samt kontroll av dubbelbokningar.
    @Transactional
    public Booking createBooking(Booking booking) {
        validateBookingEntities(booking);

        if (bookingRepository.existsByEventAndDateAndTime(booking.getEvent(), booking.getDate(), booking.getTime())) {
            throw new RuntimeException("Det finns redan en bokning vid denna tidpunkt.");
        }

        return bookingRepository.save(booking);
    }

    //Lägg till en bokning baserat på parametrar.
    @Transactional
    public Booking addBooking(Long bookingId, String location, String date, String time, Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setEvent(event);
        booking.setUser(user);
        booking.setLocation(location);
        booking.setDate(date);
        booking.setTime(time);

        if (bookingRepository.existsByEventAndDateAndTime(event, date, time)) {
            throw new RuntimeException("Det finns redan en bokning för detta event vid denna tidpunkt.");
        }

        return bookingRepository.save(booking);
    }

    /**
     * Hämta alla bokningar.
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Hämta en specifik bokning baserat på ID.
     */
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    /**
     * Uppdatera en bokning med validering.
     */
    @Transactional
    public Booking updateBooking(Booking booking) {
        if (booking.getId() == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        if (!bookingRepository.existsById(booking.getId())) {
            throw new RuntimeException("Booking not found");
        }

        validateBookingEntities(booking);

        if (bookingRepository.existsByEventAndDateAndTime(booking.getEvent(), booking.getDate(), booking.getTime())) {
            throw new RuntimeException("Det finns redan en bokning för detta event vid denna tidpunkt.");
        }

        return bookingRepository.save(booking);
    }

    /**
     * Radera en bokning baserat på ID.
     */
    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found");
        }
        bookingRepository.deleteById(id);
    }

    /**
     * Testa databaskopplingen (användbar för felsökning).
     */
    public void testDatabaseConnection() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Validera att Event och User är kopplade till bokningen.
     */
    private void validateBookingEntities(Booking booking) {
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
    }
}
