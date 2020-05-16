package com.example.bookmyshow.controllers;

import com.example.bookmyshow.entities.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.CityRepository;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.ShowSeatBookingRepository;
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

import java.util.List;
import java.util.Optional;

/**
 * Created by AbhijithRavuri.
 */

@RestController
@Transactional
@RequestMapping("/api/movies")
public class MovieController {


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

    @ApiOperation(value = "Get movie")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Return movie")
            }
    )
    @GetMapping("/{movieID}")
    public ResponseEntity<String> getMovie(@PathVariable(value = "movieID") Long movieID) throws Exception {
        Optional<Movie> movieOptional = movieRepository.findById(movieID);
        if (!movieOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(movieOptional.get()));
    }


    @ApiOperation(value = "Create movie")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Return booking ID")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> createMovie(@RequestBody Movie movie) throws Exception {
        Movie saved = movieRepository.save(movie);
        return ResponseEntity.ok().body("Created movie with name => " + movie.getName() + " and id " + movie.getId());
    }

}
