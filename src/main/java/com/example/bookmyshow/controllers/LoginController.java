package com.example.bookmyshow.controllers;

import com.example.bookmyshow.auth.JwtTokenProvider;
import com.example.bookmyshow.dto.UserDTO;
import com.example.bookmyshow.entities.Role;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.entities.User;
import com.example.bookmyshow.repositories.TheatreRepository;
import com.example.bookmyshow.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by AbhijithRavuri.
 */

@RestController
@Transactional
@RequestMapping("/login")
public class LoginController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;



    @Value("${security.jwt.token.expire-length:43200000}")
    private long validityInMilliseconds = 43200000; // 4h

    private ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "User signs in")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @ApiModelProperty
    @PostMapping("")
    public ResponseEntity<String> signIn(@RequestBody UserDTO body) throws Exception {

        String username = body.getUsername();
        String password = body.getPassword();
        User user = userRepository.findByUsernameAndPassword(username, password);

        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);
        String token = tokenProvider.createToken(username, roles, now, expiry);
        return ResponseEntity.ok().body(token);
    }
}
