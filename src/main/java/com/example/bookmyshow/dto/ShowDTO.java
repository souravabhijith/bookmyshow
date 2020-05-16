package com.example.bookmyshow.dto;

import com.example.bookmyshow.entities.Movie;
import com.example.bookmyshow.entities.Theatre;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by AbhijithRavuri.
 */
public class ShowDTO {

    Long theatre_id;
    Long movie_id;

    Integer show_number;

    @ApiModelProperty(name = "Start time of show", position = 0, example = "09:00")
    private String starttime;

    @ApiModelProperty(name = "Date for show", position = 0, example = "2020-05-16")
    private String date;

    public ShowDTO() {

    }

    public Long getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(Long theatre_id) {
        this.theatre_id = theatre_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getShow_number() {
        return show_number;
    }

    public void setShow_number(Integer show_number) {
        this.show_number = show_number;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
