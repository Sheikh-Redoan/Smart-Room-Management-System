package com.example.roommanagement.service;

import com.example.roommanagement.model.Room;
import com.example.roommanagement.model.RoomBooking;
import com.example.roommanagement.repository.RoomBookingRepository;
import com.example.roommanagement.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomBookingService {
    @Autowired
    private RoomBookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomBooking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public RoomBooking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public RoomBooking bookRoom(Long roomId, String bookedBy, LocalDateTime startTime,
                                LocalDateTime endTime, String purpose) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("Invalid room ID"));

        // Check for conflicting bookings
        List<RoomBooking> conflicts = bookingRepository.findConflictingBookings(
                room, startTime, endTime);

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Room is already booked for the requested time");
        }

        RoomBooking booking = new RoomBooking(room, bookedBy, startTime, endTime, purpose);
        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public List<RoomBooking> getBookingsForRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("Invalid room ID"));
        return bookingRepository.findByRoom(room);
    }

    public List<RoomBooking> getBookingsBetween(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findAllBookingsBetween(start, end);
    }

    public boolean isRoomAvailable(Long roomId, LocalDateTime start, LocalDateTime end) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("Invalid room ID"));
        List<RoomBooking> conflicts = bookingRepository.findConflictingBookings(room, start, end);
        return conflicts.isEmpty();
    }
}