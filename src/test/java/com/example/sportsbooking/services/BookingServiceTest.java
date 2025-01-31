package com.sportsbooking.services;

import com.sportsbooking.models.Booking;
import com.sportsbooking.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        Booking booking1 = new Booking(1L, "Tennishall", "2024-02-15", "10:00");
        Booking booking2 = new Booking(2L, "Fotbollsplan", "2024-02-16", "14:00");

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));
    }

    @Test
    void testFindAllBookings() {
        List<Booking> bookings = bookingService.findAllBookings();
        assertEquals(2, bookings.size());
    }
}
