package com.example.bookmyshow.controllers;

import com.example.bookmyshow.entities.Activity;
import com.example.bookmyshow.repositories.ActivityRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by AbhijithRavuri.
 */

@RestController
@Transactional
@RequestMapping("/api/activities")
public class ActivityController {


    @Autowired
    ActivityRepository activityRepository;

    @ApiOperation(value = "Lists Activity")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "list")
            }
    )
    @GetMapping("")
    public ResponseEntity<String> activitysList() throws Exception {

        return ResponseEntity.ok().body("Activties count " + activityRepository.findAll().size());
    }
}
