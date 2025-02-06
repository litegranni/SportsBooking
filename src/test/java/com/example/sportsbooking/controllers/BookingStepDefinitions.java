package com.example.sportsbooking.controllers;

import com.example.sportsbooking.models.Booking;
import com.example.sportsbooking.models.Event;
import com.example.sportsbooking.models.User;
import com.example.sportsbooking.services.BookingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookingStepDefinitions {

    @Autowired
    private BookingService bookingService;

    private List<Booking> bookings;

    @Given("det finns bokningar i systemet")
    public void det_finns_bokningar_i_systemet() {
        // Skapa testdata för Event och User
        Event tennisEvent = new Event();
        tennisEvent.setId(1L);
        tennisEvent.setName("Tennismatch");

        Event footballEvent = new Event();
        footballEvent.setId(2L);
        footballEvent.setName("Fotbollsmatch");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");

        // Lägg till bokningar via BookingService
        bookingService.addBooking(1L, "Tennishall", "2024-02-15", "10:00", tennisEvent.getId(), user.getId());
        bookingService.addBooking(2L, "Fotbollsplan", "2024-02-16", "14:00", footballEvent.getId(), user.getId());
    }

    @When("användaren hämtar alla bokningar via API")
    public void användaren_hämtar_alla_bokningar_via_api() {
        bookings = bookingService.getAllBookings(); // Korrekt metod från BookingService
    }

    @Then("ska systemet returnera en lista av bokningar")
    public void ska_systemet_returnera_en_lista_av_bokningar() {
        Assertions.assertFalse(bookings.isEmpty(), "Listan av bokningar är tom!");
        Assertions.assertEquals(2, bookings.size(), "Antalet bokningar matchar inte förväntat resultat!");
    }
}
