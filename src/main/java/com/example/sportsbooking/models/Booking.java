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

    // Nytt fält för kundnamn
    private String customerName;

    // Constructor without parameters
    public Booking() {
    }

    // Constructor with parameters
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

    // Getter and Setter for 'event'
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        this.event = event;
    }

    // Getter and Setter for 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    // Getter and Setter for 'status'
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter för 'customerName'
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Validate method to ensure critical fields are not null
    public void validate() {
        if (this.event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (this.user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }
}
