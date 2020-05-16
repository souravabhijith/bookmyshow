package com.example.bookmyshow.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by AbhijithRavuri.
 */
public class TheatreDTO {

    @ApiModelProperty(name = "Sample Name", position = 0, example = "theatre-a")
    String name;
    int seats;
    String city;

    public TheatreDTO() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
