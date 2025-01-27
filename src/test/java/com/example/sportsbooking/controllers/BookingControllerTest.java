package com.example.sportsbooking.controllers;

import com.example.sportsbooking.config.TestSecurityConfig;
import com.example.sportsbooking.models.Booking;
import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.services.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)

@WebMvcTest(BookingController.class)
public class BookingControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Test
    public void testCreateBooking() throws Exception {
        Event event = new Event();
        event.setId(1L);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setEvent(event);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.createBooking(any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\":{\"id\":1},\"customerName\":\"John Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.event.id").value(1L));
    }

    @Test
    public void testGetAllBookings() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.getAllBookings()).thenReturn(Collections.singletonList(booking));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    public void testGetBookingById() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.getBookingById(eq(1L))).thenReturn(booking);

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testUpdateBooking() throws Exception {
        Event event = new Event();
        event.setId(1L);

        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);
        updatedBooking.setEvent(event);
        updatedBooking.setCustomerName("Jane Smith");

        Mockito.when(bookingService.updateBooking(any(Booking.class))).thenReturn(updatedBooking);

        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\":{\"id\":1},\"customerName\":\"Jane Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Jane Smith"))
                .andExpect(jsonPath("$.event.id").value(1L));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Mockito.doNothing().when(bookingService).deleteBooking(eq(1L));

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }
}
