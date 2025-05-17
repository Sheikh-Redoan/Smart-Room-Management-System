package com.example.roommanagement.controller;

import com.example.roommanagement.model.RoomBooking;
import com.example.roommanagement.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RoomBookingController {
    @Autowired
    private RoomBookingService bookingService;

    @GetMapping
    public List<RoomBooking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomBooking> getBookingById(@PathVariable Long id) {
        RoomBooking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @PostMapping
    public ResponseEntity<RoomBooking> bookRoom(
            @RequestParam Long roomId,
            @RequestParam String bookedBy,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam String purpose) {

        try {
            RoomBooking booking = bookingService.bookRoom(roomId, bookedBy, startTime, endTime, purpose);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/room/{roomId}")
    public List<RoomBooking> getBookingsForRoom(@PathVariable Long roomId) {
        return bookingService.getBookingsForRoom(roomId);
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        boolean isAvailable = bookingService.isRoomAvailable(roomId, start, end);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/between")
    public List<RoomBooking> getBookingsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        return bookingService.getBookingsBetween(start, end);
    }
}