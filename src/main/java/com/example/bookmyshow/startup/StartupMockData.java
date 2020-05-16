package com.example.bookmyshow.startup;

import com.example.bookmyshow.entities.*;
import com.example.bookmyshow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by AbhijithRavuri.
 */
@Component
public class StartupMockData {


    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CityRepository cityRepository;

    @PostConstruct
    public void init() {
        initialize();
    }

    Map<String, Theatre> theatreMap = new HashMap<>();
    Map<String, City> cityMap = new HashMap<>();
    Map<Long, Movie> movieMap = new HashMap<>();

    public void initialize() {
        createCities();
        createTheatres();
        createMovies();
        createUsers();
        createShows();
    }

    public void createCities() {
        cityRepository.save(new City("hyderabad"));
        cityRepository.save(new City("bangalore"));
        List<City> cityList = cityRepository.findAll();
        for (City city : cityList) {
            cityMap.put(city.getName(), city);
        }
    }

    private void createTheatres() {
        theatreRepository.save(new Theatre("pvr", cityMap.get("hyderabad")));
        theatreRepository.save(new Theatre("inox",  cityMap.get("hyderabad")));
        theatreRepository.save(new Theatre("pvr-blr",  cityMap.get("bangalore")));
        List<Theatre> theatreList = theatreRepository.findAll();
        for (Theatre theatre : theatreList) {
            theatreMap.put(theatre.getName(), theatre);
        }
    }

    private void createMovies() {
        int movies = 10;
        for (int i=0;i<movies;i++) {
            Movie saved = movieRepository.save(new Movie("movie-" + i, 90));
            movieMap.put(saved.getId(), saved);
        }
    }

    public void createUsers() {
        userRepository.save(new User("admin","admin", Role.ROLE_ADMIN));
        userRepository.save(new User("user1","user1", Role.ROLE_USER));
        userRepository.save(new User("user2","user2", Role.ROLE_USER));
    }

    public void createShows() {
        showRepository.save(new Show(theatreMap.get("pvr"),
                                     movieMap.get(1L), 1, getTime("09:00"), getDate("2020-05-16")));
        showRepository.save(new Show(theatreMap.get("pvr"),
                                     movieMap.get(2L), 1, getTime("09:00"), getDate("2020-05-16")));
        showRepository.save(new Show(theatreMap.get("inox"),
                                     movieMap.get(1L), 1, getTime("09:00"), getDate("2020-05-16")));
        showRepository.save(new Show(theatreMap.get("inox"),
                                     movieMap.get(1L), 2, getTime("12:00"), getDate("2020-05-16")));
        showRepository.save(new Show(theatreMap.get("inox"),
                                     movieMap.get(1L), 3, getTime("20:00"), getDate("2020-05-16")));
        showRepository.save(new Show(theatreMap.get("pvr-blr"),
                                     movieMap.get(1L), 1, getTime("12:00"), getDate("2020-05-16")));

    }

    public Date getTime(String time) {
        time = getCurrentDate() + " " + time;
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return formatter.parse(time);
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        String newDateStr = formatter.format(date);
        return newDateStr.substring(0,10);
    }
    // format ="31/12/1998";
    public Date getDate(String date) {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
