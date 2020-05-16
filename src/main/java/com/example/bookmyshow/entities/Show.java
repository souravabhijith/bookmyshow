//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.bookmyshow.entities;

import com.example.bookmyshow.dto.ShowDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(
    name = "shows"
)
public class Show {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "theatre_id"
    )
    Theatre theatre;

    @ManyToOne
    @JoinColumn(
            name = "movie_id"
    )
    Movie movie;

    @Column(nullable = false)
    Integer show_number;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date starttime;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;


    public Show() {

    }

    public Show(Theatre theatre, Movie movie, int show_number, Date starttime, Date date) {
        this.theatre = theatre;
        this.movie = movie;
        this.show_number = show_number;
        this.starttime = starttime;
        this.date = date;
    }


    public void setShow_number(Integer show_number) {
        this.show_number = show_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getShow_number() {
        return show_number;
    }

    public void setShow_number(int show_number) {
        this.show_number = show_number;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return show_number == show.show_number &&
                Objects.equals(id, show.id) &&
                Objects.equals(theatre, show.theatre) &&
                Objects.equals(movie, show.movie) &&
                Objects.equals(starttime, show.starttime) &&
                Objects.equals(date, show.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theatre, movie, show_number, starttime, date);
    }
}
