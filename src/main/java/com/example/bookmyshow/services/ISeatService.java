package com.example.bookmyshow.services;

import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.SeatStatus;
import com.example.bookmyshow.entities.Show;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by AbhijithRavuri.
 */
public interface ISeatService {

    Map<Integer, SeatStatus> getSeatInfo(Show show);


    /*
    Try to reserve all seats. If reservation succesfull return booking id. else return null.
     */
    boolean reserveSeats(Booking booking, Show show, Set<Integer> seats);

    boolean confirmSeat(Show show, int seatnum);

}
