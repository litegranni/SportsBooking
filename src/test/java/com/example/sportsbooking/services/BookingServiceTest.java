package com.example.sportsbooking.services;

import com.example.sportsbooking.models.Booking;
import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.models.User;
import com.example.sportsbooking.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Event event1 = new Event();
        event1.setId(1L);

        Event event2 = new Event();
        event2.setId(2L);

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("TestUser1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("TestUser2");

        Booking booking1 = new Booking(event1, user1, "CONFIRMED", "TestUser1");
        Booking booking2 = new Booking(event2, user2, "PENDING", "TestUser2");

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));
    }

    @Test
    void testFindAllBookings() {
        List<Booking> bookings = bookingService.findAllBookings();
        assertEquals(2, bookings.size());
    }
}
