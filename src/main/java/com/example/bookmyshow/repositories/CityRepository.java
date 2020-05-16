package com.example.bookmyshow.repositories;

/**
 * Created by AbhijithRavuri.
 */
import com.example.bookmyshow.entities.City;
import com.example.bookmyshow.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);
}
