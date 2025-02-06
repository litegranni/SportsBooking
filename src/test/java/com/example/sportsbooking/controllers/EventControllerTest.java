package com.example.sportsbooking.controllers;

import com.example.sportsbooking.config.TestSecurityConfig;
import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void shouldReturnAllEventsWhenRequested() throws Exception {
        // Given: Ett evenemang finns i systemet
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.getAllEvents()).thenReturn(Collections.singletonList(event));

        // When: En GET-begäran skickas till /api/events
        mockMvc.perform(get("/api/events"))
                // Then: En lista med evenemang returneras med status 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Football Match"))
                .andExpect(jsonPath("$[0].location").value("Stadium A"))
                .andExpect(jsonPath("$[0].date").value("2025-05-15"))
                .andExpect(jsonPath("$[0].availableSeats").value(100));
    }

    @Test
    public void shouldCreateEventWhenValidRequestIsProvided() throws Exception {
        // Given: Ett giltigt evenemangsobjekt
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.createEvent(any(Event.class))).thenReturn(event);

        // When: En POST-begäran skickas till /api/events
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Football Match\",\"location\":\"Stadium A\",\"date\":\"2025-05-15\",\"availableSeats\":100}"))
                // Then: Evenemanget skapas och returnerar status 201 Created med korrekt data
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Football Match"))
                .andExpect(jsonPath("$.location").value("Stadium A"))
                .andExpect(jsonPath("$.date").value("2025-05-15"))
                .andExpect(jsonPath("$.availableSeats").value(100));
    }

    @Test
    public void shouldReturnEventByIdWhenValidIdIsProvided() throws Exception {
        // Given: Ett evenemang finns i systemet
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.getEventById(eq(1L))).thenReturn(event);

        // When: En GET-begäran skickas till /api/events/1
        mockMvc.perform(get("/api/events/1"))
                // Then: Evenemanget returneras med status 200 OK och korrekt data
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Football Match"))
                .andExpect(jsonPath("$.location").value("Stadium A"))
                .andExpect(jsonPath("$.date").value("2025-05-15"))
                .andExpect(jsonPath("$.availableSeats").value(100));
    }

    @Test
    public void shouldUpdateEventWhenValidRequestIsProvided() throws Exception {
        // Given: Ett uppdaterat evenemangsobjekt
        Event updatedEvent = new Event();
        updatedEvent.setId(1L);
        updatedEvent.setName("Updated Event");
        updatedEvent.setLocation("Updated Location");
        updatedEvent.setDate(LocalDate.of(2025, 6, 20));
        updatedEvent.setAvailableSeats(50);

        Mockito.when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(updatedEvent);

        // When: En PUT-begäran skickas till /api/events/1
        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Event\",\"location\":\"Updated Location\",\"date\":\"2025-06-20\",\"availableSeats\":50}"))
                // Then: Evenemanget uppdateras och returnerar status 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Event"))
                .andExpect(jsonPath("$.location").value("Updated Location"))
                .andExpect(jsonPath("$.date").value("2025-06-20"))
                .andExpect(jsonPath("$.availableSeats").value(50));
    }

    @Test
    public void shouldDeleteEventWhenValidIdIsProvided() throws Exception {
        // Given: Ett evenemang med ID 1 finns i systemet
        Mockito.doNothing().when(eventService).deleteEvent(eq(1L));

        // When: En DELETE-begäran skickas till /api/events/1
        mockMvc.perform(delete("/api/events/1"))
                // Then: Evenemanget raderas och returnerar status 204 No Content
                .andExpect(status().isNoContent());
    }
}
