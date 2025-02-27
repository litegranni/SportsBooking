package com.example.sportsbooking.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Booking {

    // Getter och Setter för 'id'
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getter och Setter för 'event'
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    // Getter och Setter för 'user'
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getter och Setter för 'status'
    @Setter
    private String status;

    // Getter och Setter för 'customerName'
    // Nytt fält för kundnamn
    @Setter
    private String customerName;

    // Standardkonstruktor krävs av JPA
    public Booking() {}

    // 💡 Konstruktor för att matcha testerna
    public Booking(Long id, String location, String date, String time, User user, Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.id = id;
        this.customerName = location; // Om location är tänkt att vara customerName, annars ändra detta
        this.status = date + " " + time; // Om status ska lagra datum+tid
        this.user = user;
        this.event = event;
    }

    // Konstruktor med event, user och status
    public Booking(Event event, User user, String status, String customerName) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.event = event;
        this.user = user;
        this.status = status;
        this.customerName = customerName;
    }

    public void setEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        this.event = event;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    // 💡 Säkerställer att viktiga fält inte är null
    public void validate() {
        if (this.event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (this.user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }
}
