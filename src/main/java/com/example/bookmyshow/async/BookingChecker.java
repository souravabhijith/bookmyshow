package com.example.bookmyshow.async;

import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.BookingStatus;
import com.example.bookmyshow.entities.SeatStatus;
import com.example.bookmyshow.entities.ShowSeatBooking;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowSeatBookingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by AbhijithRavuri.
 */
public class BookingChecker implements Runnable {


    BookingRepository bookingRepository;
    ShowSeatBookingRepository showSeatBookingRepository;
    Long bookingID;
    Long seatBookingID;

    public BookingChecker(BookingRepository bookingRepository, ShowSeatBookingRepository showSeatBookingRepository,
                   Long bookingID) {
        this.bookingRepository = bookingRepository;
        this.showSeatBookingRepository = showSeatBookingRepository;
        this.bookingID = bookingID;
    }

    @Override
    public void run() {
        Optional<Booking> currentBookingOpt = bookingRepository.findById(bookingID);
        if (!currentBookingOpt.isPresent()) {
            return;
        }
        Booking currentBooking = currentBookingOpt.get();
        //Get current seats
        if (!currentBooking.getStatus().equals(BookingStatus.CONFIRMED)) {
            List<ShowSeatBooking> seats = showSeatBookingRepository.findSeatsByBookingID(bookingID);
            for (ShowSeatBooking seatBooking : seats) {
                seatBooking.setStatus(SeatStatus.AVAILABLE);
            }
            showSeatBookingRepository.saveAll(seats);
            currentBooking.setStatus(BookingStatus.FAILED);
        }
    }
}
