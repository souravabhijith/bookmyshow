package com.example.bookmyshow.repositories;

/**
 * Created by AbhijithRavuri.
 */
import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
