package Movies;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


@RestController
public class MoviesController {

    // Paremeters: date, name, category, country, localization Cinema.valueOf("MULTIKINO");
    @GetMapping("/movies")
    public String getMovies(@RequestParam String date,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String country,
                            @RequestParam(required = false) String location,
                            @RequestParam(required = false) String city,
                            @RequestParam(required = false) String cinema) {
        return new Gson().toJson(MoviesService.getMovies(LocalDate.parse(date), name, category, country, location, city, cinema));
    }

    @GetMapping("/movieNames")
    public String getMovieNames() {
        return new Gson().toJson(MoviesService.getNames());
    }

    @GetMapping("/locations")
    public String locations(@RequestParam(required = false) String cinema) {
        return new Gson().toJson(MoviesService.getLocations(cinema));
    }

    @GetMapping("/cinemas")
    public String cinemas() {
        return new Gson().toJson(Cinema.values());
    }
}
