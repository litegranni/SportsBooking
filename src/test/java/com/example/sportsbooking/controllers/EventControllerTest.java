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
    public void testGetAllEvents() throws Exception {
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.getAllEvents()).thenReturn(Collections.singletonList(event));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Football Match"))
                .andExpect(jsonPath("$[0].location").value("Stadium A"))
                .andExpect(jsonPath("$[0].date").value("2025-05-15"))
                .andExpect(jsonPath("$[0].availableSeats").value(100));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Football Match\",\"location\":\"Stadium A\",\"date\":\"2025-05-15\",\"availableSeats\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Football Match"))
                .andExpect(jsonPath("$.location").value("Stadium A"))
                .andExpect(jsonPath("$.date").value("2025-05-15"))
                .andExpect(jsonPath("$.availableSeats").value(100));
    }

    @Test
    public void testGetEventById() throws Exception {
        Event event = new Event();
        event.setId(1L);
        event.setName("Football Match");
        event.setLocation("Stadium A");
        event.setDate(LocalDate.of(2025, 5, 15));
        event.setAvailableSeats(100);

        Mockito.when(eventService.getEventById(eq(1L))).thenReturn(event);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Football Match"))
                .andExpect(jsonPath("$.location").value("Stadium A"))
                .andExpect(jsonPath("$.date").value("2025-05-15"))
                .andExpect(jsonPath("$.availableSeats").value(100));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Event updatedEvent = new Event();
        updatedEvent.setId(1L);
        updatedEvent.setName("Updated Event");
        updatedEvent.setLocation("Updated Location");
        updatedEvent.setDate(LocalDate.of(2025, 6, 20));
        updatedEvent.setAvailableSeats(50);

        Mockito.when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(updatedEvent);

        mockMvc.perform(put("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Event\",\"location\":\"Updated Location\",\"date\":\"2025-06-20\",\"availableSeats\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Event"))
                .andExpect(jsonPath("$.location").value("Updated Location"))
                .andExpect(jsonPath("$.date").value("2025-06-20"))
                .andExpect(jsonPath("$.availableSeats").value(50));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Mockito.doNothing().when(eventService).deleteEvent(eq(1L));

        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isNoContent());
    }
}
