package com.example.bookmyshow.services;

import com.example.bookmyshow.async.BookingChecker;
import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.SeatStatus;
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.ShowSeatBooking;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowSeatBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by AbhijithRavuri.
 */
@Component
public class SeatService implements ISeatService {



    @Autowired
    ShowSeatBookingRepository showSeatBookingRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScheduledExecutorService taskScheduler;

    @Override
    public Map<Integer, SeatStatus> getSeatInfo(Show show) {
        List<ShowSeatBooking> showSeatBookingList = showSeatBookingRepository.findSeats(show.getId());
        Map<Integer, SeatStatus> statusMap = new HashMap<>();
        for (int i=0;i<show.getTheatre().getMaxSeats();i++) {
            statusMap.put(i,SeatStatus.AVAILABLE);
        }
        for (ShowSeatBooking seatBooking : showSeatBookingList) {
            statusMap.put(seatBooking.getSeat_number(), seatBooking.getStatus());
        }
        return statusMap;
    }

    /*
    This method is transactional since its being called from a transactional controller.
     */
    @Override
    public boolean reserveSeats(final Booking booking, Show show, Set<Integer> seats) {

        //Validate if seats are already booked or taken
        List<ShowSeatBooking> seatsList = showSeatBookingRepository.findSeats(show.getId());
        Map<Integer, ShowSeatBooking> seatNumberToShowSeat = new HashMap<>();

        for (ShowSeatBooking seatBooking : seatsList) {
            seatNumberToShowSeat.put(seatBooking.getSeat_number(), seatBooking);
            if (seats.contains(seatBooking.getSeat_number())) {
                if (!seatBooking.getStatus().equals(SeatStatus.AVAILABLE)) {
                    return false;
                }
            }
        }

        //Lock on show seat id ?
        try {
            for (Integer seatNumber : seats) {
                //Reserve current seat
                if (seatNumberToShowSeat.get(seatNumber) == null) {
                    //No Entry for this seat is yet created. We are creating now.
                    //Since we are having a composite key of show_id,seat_number, we cant have 2 rows
                    //for same seat. MySQL DB implements row level locking so consistent
                    //updates for same row are serialized.
                    ShowSeatBooking seatBooking = new ShowSeatBooking(show, booking, SeatStatus.RESERVED, seatNumber);
                    seatBooking.setShow(show);
                    showSeatBookingRepository.save(seatBooking);
                } else {
                    /*
                    Enable below sleep for concurrent db row modifications exceptions.
                     */
                    //System.out.println("Sleeping for " + booking.getId());
                    //Thread.sleep(5000);
                    int count = showSeatBookingRepository.updateOnlyIfStatus(seatNumberToShowSeat.get(seatNumber).getShow().getId(),
                                                                             seatNumberToShowSeat.get(seatNumber).getSeat_number(),
                                                                             booking.getId(),
                                                                             SeatStatus.RESERVED,
                                                                             SeatStatus.AVAILABLE);
                    if (count == 0) {
                    /*
                    This is to check if update failed. This happens when 2 users at the same time tried to reserve the same seats
                    which are available before. For one user the updates will fail and the all modifications done by this
                    user will be rolledback
                     */
                        System.out.println("Rolling back changes for " + booking.getId());
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception raised for " + booking.getId());
            throw new RuntimeException(e);
        } finally {
            taskScheduler.schedule(new BookingChecker(bookingRepository, showSeatBookingRepository, booking.getId()),
                                   30, TimeUnit.SECONDS);
        }


        return true;
    }

    @Override
    public boolean confirmSeat(Show show, int seatnum) {
        return false;
    }
}
