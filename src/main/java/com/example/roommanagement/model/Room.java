package com.example.roommanagement.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int capacity;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomBooking> bookings;

    // Constructors, Getters, and Setters
    public Room() {}

    public Room(String name, String description, int capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<RoomBooking> getBookings() { return bookings; }
    public void setBookings(List<RoomBooking> bookings) { this.bookings = bookings; }
}