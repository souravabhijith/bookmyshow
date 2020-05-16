//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.bookmyshow.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(ShowSeatCompositeKey.class)
@Table(
    name = "seatbookings"
)
public class ShowSeatBooking {

    @ManyToOne
    @JoinColumn(
            name = "booking_id",
            nullable = false
    )
    Booking booking;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "show_id",
            nullable = false
    )
    Show show;

    @Id
    @Column(nullable = false)
    Integer seat_number;

    @Column(nullable = false)
    SeatStatus status;


    public ShowSeatBooking() {

    }


    public ShowSeatBooking(Show show, Booking booking, SeatStatus status, Integer seat_number) {
        this.show = show;
        this.booking = booking;
        this.seat_number = seat_number;
        this.status = status;
    }



    public Integer getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(Integer seat_number) {
        this.seat_number = seat_number;
    }


    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowSeatBooking that = (ShowSeatBooking) o;
        return Objects.equals(show, that.show) &&
                Objects.equals(booking, that.booking) &&
                Objects.equals(seat_number, that.seat_number) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(show, booking, status, seat_number);
    }
}
