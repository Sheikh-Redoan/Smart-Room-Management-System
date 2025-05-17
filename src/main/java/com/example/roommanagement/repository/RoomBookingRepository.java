package com.example.roommanagement.repository;

import com.example.roommanagement.model.Room;
import com.example.roommanagement.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByRoom(Room room);

    @Query("SELECT b FROM RoomBooking b WHERE b.room = :room AND " +
            "((b.startTime BETWEEN :start AND :end) OR " +
            "(b.endTime BETWEEN :start AND :end) OR " +
            "(b.startTime <= :start AND b.endTime >= :end))")
    List<RoomBooking> findConflictingBookings(@Param("room") Room room,
                                              @Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    @Query("SELECT b FROM RoomBooking b WHERE " +
            "((b.startTime BETWEEN :start AND :end) OR " +
            "(b.endTime BETWEEN :start AND :end) OR " +
            "(b.startTime <= :start AND b.endTime >= :end))")
    List<RoomBooking> findAllBookingsBetween(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);
}