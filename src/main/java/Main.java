import Movies.CinemaCity;
import Movies.Movie;
import Movies.Multikino;
import org.checkerframework.checker.units.qual.C;

import javax.swing.plaf.multi.MultiOptionPaneUI;
import java.util.Date;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        /*
        CinemaCity cinemaCity = CinemaCity.getInstance();
        List<Movie> movies1 = cinemaCity.getTimeTable(CinemaCity.getLocations().get(0), new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        Multikino multikino = Multikino.getInstance();
        List<Movie> movies2 = multikino.getTimeTable(Multikino.getLocations().get(0), new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));

        for (Movie m : movies1) {
            System.out.println(m.toString());
        }
        for (Movie m : movies2) {
            System.out.println(m.toString());
        }
        */
        SpringApplication.run(com.example.restservice.RestServiceApplication.class, args);
    }
}
