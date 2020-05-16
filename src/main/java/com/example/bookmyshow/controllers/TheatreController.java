package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dto.TheatreDTO;
import com.example.bookmyshow.entities.City;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.repositories.CityRepository;
import com.example.bookmyshow.repositories.TheatreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by AbhijithRavuri.
 */

@RestController
@Transactional
@RequestMapping("/api/theatres")
public class TheatreController {


    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    CityRepository cityRepository;


    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Lists Theatre")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping("")
    public ResponseEntity<String> theatreList() throws Exception {

        return ResponseEntity.ok().body("Theatres count " + theatreRepository.findAll().size());
    }

    @ApiOperation(value = "Create a Theatre")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> createTheatre(@RequestBody TheatreDTO theatreDTO) throws Exception {
        City city = cityRepository.findByName(theatreDTO.getCity());
        Theatre saved = theatreRepository.save(new Theatre(theatreDTO.getName(), city, theatreDTO.getSeats()));
        return ResponseEntity.ok().body("Theatre saved with id : " + saved.getId());
    }

    @ApiOperation(value = "Get Theatre", response = Theatre.class)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getTheatre(@PathVariable(value = "id") Long id) throws Exception {
        Optional<Theatre> theatreOptional = theatreRepository.findById(id);
        if (theatreOptional.isPresent()) {
            String body = objectMapper.writeValueAsString(theatreOptional.get());
            return ResponseEntity.ok().body(body);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
