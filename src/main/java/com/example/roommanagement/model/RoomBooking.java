package com.example.roommanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private String bookedBy;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;

    // Constructors, Getters, and Setters
    public RoomBooking() {}

    public RoomBooking(Room room, String bookedBy, LocalDateTime startTime, LocalDateTime endTime, String purpose) {
        this.room = room;
        this.bookedBy = bookedBy;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public String getBookedBy() { return bookedBy; }
    public void setBookedBy(String bookedBy) { this.bookedBy = bookedBy; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
}