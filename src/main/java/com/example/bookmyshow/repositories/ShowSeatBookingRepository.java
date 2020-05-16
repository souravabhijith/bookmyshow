package com.example.bookmyshow.repositories;

/**
 * Created by AbhijithRavuri.
 */
import com.example.bookmyshow.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatBookingRepository extends JpaRepository<ShowSeatBooking, Long> {

    @Query("SELECT s FROM ShowSeatBooking s WHERE s.show.id = :show_id")
    List<ShowSeatBooking> findSeats(@Param("show_id") Long show_id);

    @Query("SELECT s FROM ShowSeatBooking s WHERE s.booking.id = :bookingID")
    List<ShowSeatBooking> findSeatsByBookingID(@Param("bookingID") Long bookingID);

    @Modifying
    @Query("Update ShowSeatBooking s SET s.status = :next_status, s.booking.id = :booking_id WHERE s.status = :prev_status AND s.show.id = :show_id AND s.seat_number = :seat_number")
    Integer updateOnlyIfStatus(
            @Param("show_id") Long show_id,
            @Param("seat_number") Integer seat_number,
            @Param("booking_id") Long booking_id,
            @Param("next_status") SeatStatus next_status,
            @Param("prev_status") SeatStatus prev_status);

    @Modifying
    @Query("Update ShowSeatBooking s SET s.status = :next_status WHERE s.show.id = :show_id AND s.seat_number = :seat_number")
    Integer updateStatus(
            @Param("show_id") Long show_id,
            @Param("seat_number") Integer seat_number,
            @Param("next_status") SeatStatus next_status);
}
