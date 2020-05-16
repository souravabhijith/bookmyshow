package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.ShowsDTO;
import com.example.bookmyshow.entities.City;
import com.example.bookmyshow.entities.Movie;
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.repositories.CityRepository;
import com.example.bookmyshow.repositories.MovieRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.TheatreRepository;
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
@RequestMapping("/api/shows")
public class ShowController {


    @Autowired
    ShowRepository showRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    MovieRepository movieRepository;


    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Lists Shows for a theatre")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping("")
    public ResponseEntity<String> showList() throws Exception {

        return ResponseEntity.ok().body("Shows count " + showRepository.findAll().size());
    }

    @ApiOperation(value = "Lists All movies for a city")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping("/{city}")
    public ResponseEntity<String> showMoviesInCity(@PathVariable("city") String city) throws Exception {
        City cityObj= cityRepository.findByName(city);
        if (cityObj == null) {
            return ResponseEntity.notFound().build();
        }
        List<Show> shows = showRepository.getShowsForCity(cityObj.getId());
        Set<Movie> moviesPlaying = new HashSet<>();
        for (Show show : shows) {
            moviesPlaying.add(show.getMovie());
        }
        String body = objectMapper.writeValueAsString(moviesPlaying);
        return ResponseEntity.ok().body(body);
    }

    @ApiOperation(value = "Lists All shows for a city and movieID", produces = "application/json")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping(value = "/{city}/movies/{movieID}", produces = { "application/json"})
    public ResponseEntity<String> showMoviesInCity(@PathVariable("city") String city,
                                                   @PathVariable("movieID") Long movieID,
                                                   @RequestParam(required = true) String dateStr) throws Exception {
        City cityObj= cityRepository.findByName(city);
        if (cityObj == null) {
            return ResponseEntity.notFound().build();
        }
        Date date = getDate(dateStr);
        if (date == null) {
            return ResponseEntity.badRequest().body("Invalid date format");
        }

        List<Show> shows = showRepository.getShowsForCityAndMovieAndDate(cityObj.getId(), movieID, date);

        String body = new ShowsDTO(objectMapper, shows).toJson();
        return ResponseEntity.ok().body(body);
    }

    @ApiOperation(value = "Create a Show")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "id")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> createShow(@RequestBody Show show) throws Exception {
        Show saved = showRepository.save(show);
        return ResponseEntity.ok().body("Show saved with id : " + saved.getId());
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
