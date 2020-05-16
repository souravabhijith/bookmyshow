package com.example.bookmyshow.repositories;

/**
 * Created by AbhijithRavuri.
 */
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("Select s FROM Show s INNER JOIN Theatre t ON s.theatre.id=t.id  WHERE t.city.id = :cityID")
    List<Show> getShowsForCity(@Param("cityID") Long cityID);

    @Query("Select s FROM Show s INNER JOIN Theatre t ON s.theatre.id=t.id  WHERE t.city.id = :cityID AND s.movie.id = :movieID")
    List<Show> getShowsForCityAndMovie(@Param("cityID") Long cityID, @Param("movieID") Long movieID);

    @Query("Select s FROM Show s INNER JOIN Theatre t ON s.theatre.id=t.id  WHERE t.city.id = :cityID AND s.movie.id = :movieID AND s.date = :date")
    List<Show> getShowsForCityAndMovieAndDate(@Param("cityID") Long cityID, @Param("movieID") Long movieID,
                                              @Param("date") Date date);

}
