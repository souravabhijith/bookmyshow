//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.bookmyshow.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
    name = "theatres"
)
public class Theatre {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;

    String name;

    @ManyToOne
    @JoinColumn(
            name = "city_id"
    )
    City city;

    Integer maxSeats = 50;

    public Theatre() {

    }

    public Theatre(String name,City city) {
        this.name = name;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theatre theatre = (Theatre) o;
        return Objects.equals(id, theatre.id) &&
                Objects.equals(name, theatre.name) && Objects.equals(city, theatre.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city);
    }
}
