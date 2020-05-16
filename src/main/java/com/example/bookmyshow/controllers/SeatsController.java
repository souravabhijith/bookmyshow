package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.ShowsDTO;
import com.example.bookmyshow.entities.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.CityRepository;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.services.ISeatService;
import com.example.bookmyshow.services.IUserService;
import com.example.bookmyshow.services.SeatService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/api/seats")
public class SeatsController {


    @Autowired
    ShowRepository showRepository;

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


    @ApiOperation(value = "Lists All seats for a show")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping("")
    public ResponseEntity<String> showMoviesInCity(@RequestParam(required = true) Long showID) throws Exception {
        Optional<Show> showOptional = showRepository.findById(showID);
        if (!showOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Map<Integer, SeatStatus> seatStatusMap = seatService.getSeatInfo(showOptional.get());
        String body = objectMapper.writeValueAsString(seatStatusMap);
        return ResponseEntity.ok().body(body);
    }

    @ApiOperation(value = "Reserve few seats")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Return booking ID")
            }
    )
    @GetMapping("/reserve/{showID}")
    public ResponseEntity<String> reserveSeats(
            @PathVariable(value = "showID") Long showID,
            @RequestParam(required = true) String seatIDS) throws Exception {
        Optional<Show> showOptional = showRepository.findById(showID);
        if (!showOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        String[] tokens = seatIDS.split(",");
        if (tokens.length >= 5) {

            return ResponseEntity.badRequest().body("At max only 5 seaats can be reserved");
        }
        Set<Integer> seatIDSInts = new HashSet<>();
        for (String token : tokens) {
            seatIDSInts.add(Integer.parseInt(token));
        }
        User user = userService.getCurrentUser();
        Booking booking = new Booking(showOptional.get(), user, BookingStatus.PAYMENT_PENDING, seatIDSInts.size());
        booking = bookingRepository.save(booking);
        boolean successFull = seatService.reserveSeats(booking, showOptional.get(), seatIDSInts);
        if (!successFull) {
            bookingRepository.delete(booking);
            return ResponseEntity.ok().body("Some of the seats you selected are already reserved. Please try again with different seats");
        }
        return ResponseEntity.ok().body("Seats reserved with booking ID " + booking.getId());
    }


    // format ="31/12/1998";
    public Date getDate(String date) {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
