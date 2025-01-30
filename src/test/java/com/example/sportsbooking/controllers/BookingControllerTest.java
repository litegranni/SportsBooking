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
    public void shouldCreateBookingWhenValidRequestIsProvided() throws Exception {
        // Given: Ett giltigt bokningsobjekt och tillhörande evenemang
        Event event = new Event();
        event.setId(1L);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setEvent(event);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.createBooking(any(Booking.class))).thenReturn(booking);

        // When: En POST-begäran skickas till /api/bookings
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\":{\"id\":1},\"customerName\":\"John Doe\"}"))
                // Then: Bokningen skapas och returnerar 201 Created med korrekt data
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.event.id").value(1L));
    }

    @Test
    public void shouldReturnAllBookingsWhenRequested() throws Exception {
        // Given: En lista med bokningar finns i systemet
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.getAllBookings()).thenReturn(Collections.singletonList(booking));

        // When: En GET-begäran skickas till /api/bookings
        mockMvc.perform(get("/api/bookings"))
                // Then: En lista med bokningar returneras med status 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    public void shouldReturnBookingByIdWhenValidIdIsProvided() throws Exception {
        // Given: En bokning med ID 1 finns i systemet
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCustomerName("John Doe");

        Mockito.when(bookingService.getBookingById(eq(1L))).thenReturn(booking);

        // When: En GET-begäran skickas till /api/bookings/1
        mockMvc.perform(get("/api/bookings/1"))
                // Then: Bokningen returneras med status 200 OK och korrekt data
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void shouldUpdateBookingWhenValidRequestIsProvided() throws Exception {
        // Given: Ett uppdaterat bokningsobjekt och tillhörande evenemang
        Event event = new Event();
        event.setId(1L);

        Booking updatedBooking = new Booking();
        updatedBooking.setId(1L);
        updatedBooking.setEvent(event);
        updatedBooking.setCustomerName("Jane Smith");

        Mockito.when(bookingService.updateBooking(any(Booking.class))).thenReturn(updatedBooking);

        // When: En PUT-begäran skickas till /api/bookings/1
        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"event\":{\"id\":1},\"customerName\":\"Jane Smith\"}"))
                // Then: Bokningen uppdateras och returneras med status 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Jane Smith"))
                .andExpect(jsonPath("$.event.id").value(1L));
    }

    @Test
    public void shouldDeleteBookingWhenValidIdIsProvided() throws Exception {
        // Given: En bokning med ID 1 finns i systemet
        Mockito.doNothing().when(bookingService).deleteBooking(eq(1L));

        // When: En DELETE-begäran skickas till /api/bookings/1
        mockMvc.perform(delete("/api/bookings/1"))
                // Then: Bokningen raderas och returnerar status 204 No Content
                .andExpect(status().isNoContent());
    }
}
