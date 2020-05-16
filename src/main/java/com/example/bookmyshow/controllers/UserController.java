package com.example.bookmyshow.controllers;

import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.entities.User;
import com.example.bookmyshow.repositories.TheatreRepository;
import com.example.bookmyshow.repositories.UserRepository;
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
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    UserRepository userRepository;


    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Create a User")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody User user) throws Exception {
        User saved = userRepository.save(user);
        return ResponseEntity.ok().body("User saved with id : " + saved.getId());
    }
}
