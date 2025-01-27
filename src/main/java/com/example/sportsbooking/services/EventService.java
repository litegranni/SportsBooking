package com.example.sportsbooking.services;

import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event med ID " + id + " hittades inte."));
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event med ID " + id + " hittades inte."));

        existingEvent.setName(updatedEvent.getName());
        existingEvent.setLocation(updatedEvent.getLocation());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setAvailableSeats(updatedEvent.getAvailableSeats());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException("Event med ID " + id + " hittades inte.");
        }
        eventRepository.deleteById(id);
    }
}
