package com.example.bookmyshow.repositories;

/**
 * Created by AbhijithRavuri.
 */
import com.example.bookmyshow.entities.Activity;
import com.example.bookmyshow.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

}
