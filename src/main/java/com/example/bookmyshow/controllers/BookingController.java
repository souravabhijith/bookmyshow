package com.example.bookmyshow.controllers;

import com.example.bookmyshow.entities.*;
import com.example.bookmyshow.repositories.*;
import com.example.bookmyshow.services.ISeatService;
import com.example.bookmyshow.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by AbhijithRavuri.
 */

@RestController
@Transactional
@RequestMapping("/api/bookings")
public class BookingController {


    @Autowired
    ShowSeatBookingRepository showSeatBookingRepository;

    @Autowired
    IUserService userService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ISeatService seatService;

    @Autowired
    BookingRepository bookingRepository;



    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Confirm booking")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Return booking ID")
            }
    )
    @GetMapping("/confirm/{bookingID}")
    public ResponseEntity<String> confirmBooking(@PathVariable(value = "bookingID") Long bookingID) throws Exception {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingID);
        if (!bookingOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Booking booking = bookingOptional.get();
        if (booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            return ResponseEntity.ok().body("Already confirmed booking ID " + booking.getId());

        }
        List<ShowSeatBooking> seats = showSeatBookingRepository.findSeatsByBookingID(bookingID);
        for (ShowSeatBooking seatBooking : seats) {
            seatBooking.setStatus(SeatStatus.BOOKED);
        }
        showSeatBookingRepository.saveAll(seats);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking = bookingRepository.save(booking);
        return ResponseEntity.ok().body("Confirmed booking ID " + booking.getId());
    }


    @ApiOperation(value = "Confirm booking")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Return booking ID")
            }
    )
    @GetMapping("/{bookingID}")
    public ResponseEntity<String> getBooking(@PathVariable(value = "bookingID") Long bookingID) throws Exception {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingID);
        if (!bookingOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Booking booking = bookingOptional.get();
        List<ShowSeatBooking> seats = showSeatBookingRepository.findSeatsByBookingID(bookingID);
        ObjectNode result = objectMapper.createObjectNode();


        return ResponseEntity.ok().body(result.toString());
    }

}
