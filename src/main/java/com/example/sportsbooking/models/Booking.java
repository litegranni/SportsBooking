package com.example.sportsbooking.models;

import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String status;

    private String customerName;

    // Nya fält för plats, datum och tid
    private String location;
    private String date;
    private String time;

    // Default-konstruktor
    public Booking() {}

    // Konstruktor med parametrar
    public Booking(Event event, User user, String status, String customerName, String location, String date, String time) {
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
        this.location = location;
        this.date = date;
        this.time = time;
    }

    // Getter och Setter för 'event'
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        this.event = event;
    }

    // Getter och Setter för 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    // Getter och Setter för 'status'
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter och Setter för 'customerName'
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Getter och Setter för 'location'
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getter och Setter för 'date'
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter och Setter för 'time'
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Getter och Setter för 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Validate-metod för att säkerställa att kritiska fält inte är null
    public void validate() {
        if (this.event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (this.user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (this.location == null || this.location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (this.date == null || this.date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        if (this.time == null || this.time.isEmpty()) {
            throw new IllegalArgumentException("Time cannot be null or empty");
        }
    }
}
