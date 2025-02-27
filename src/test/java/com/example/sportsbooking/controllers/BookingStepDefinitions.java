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
        Event tennisEvent = new Event();
        tennisEvent.setId(1L);
        tennisEvent.setName("Tennismatch");

        Event footballEvent = new Event();
        footballEvent.setId(2L);
        footballEvent.setName("Fotbollsmatch");

        User user = new User();
        user.setId(1L);
        user.setUsername("John Doe"); // Fixad metod

        Booking booking1 = new Booking(tennisEvent, user, "CONFIRMED", "John Doe");
        Booking booking2 = new Booking(footballEvent, user, "PENDING", "John Doe");

        bookingService.createBooking(booking1);
        bookingService.createBooking(booking2);
    }

    @When("användaren hämtar alla bokningar via API")
    public void användaren_hämtar_alla_bokningar_via_api() {
        bookings = bookingService.getAllBookings();
    }

    @Then("ska systemet returnera en lista av bokningar")
    public void ska_systemet_returnera_en_lista_av_bokningar() {
        Assertions.assertFalse(bookings.isEmpty(), "Listan av bokningar är tom!");
        Assertions.assertEquals(2, bookings.size(), "Antalet bokningar matchar inte förväntat resultat!");
    }
}
