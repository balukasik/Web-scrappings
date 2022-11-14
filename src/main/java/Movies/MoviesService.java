package Movies;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableScheduling
@Service
public class MoviesService {
    private static final int MOVIE_BUFFER_DURATION = 1;
    private static final int MOVIE_NAME_BUFFER_MAX_SIZE = 200;

    private final static Cache<String, List<Movie>> movieCache = Caffeine.newBuilder()
            .expireAfterWrite(MOVIE_BUFFER_DURATION, TimeUnit.DAYS)
            .maximumSize(MOVIE_BUFFER_DURATION)
            .build();

    private final static Cache<String, String> movieNameCache = Caffeine.newBuilder()
            .expireAfterWrite(MOVIE_BUFFER_DURATION, TimeUnit.DAYS)
            .maximumSize(MOVIE_NAME_BUFFER_MAX_SIZE)
            .build();


    @Scheduled(cron="0 1 0 * * ?")
    @PostConstruct
    public void fill_cache() {
        System.out.println("-----------------------------\t\tCACHE LOADING\t\t-----------------------------");
        LocalDate date = LocalDate.now();
        for(int i = 0; i < MOVIE_BUFFER_DURATION; i++){
            getMovies(date,null,null,null,null, null, null);
            date = date.plusDays(1);
            System.out.println(String.format("-----------------------------\t\tCACHE LOADING %d/%d\t-----------------------------", i+1, MOVIE_BUFFER_DURATION));
        }
        System.out.println("-----------------------------\t\tCACHE LOADED\t\t-----------------------------");
    }

    public static List<Movie> getMovies(LocalDate date, String name, String category, String country, String location, String city, String cinema) {
        List<Movie> cachedMovies = movieCache.getIfPresent(date.toString());
        if(cachedMovies == null){
            List<Movie> movies = Stream.concat(
                            Multikino.getInstance().getTimeTable(Multikino.getLocations().get(0), date).stream(),
                            CinemaCity.getInstance().getTimeTable(CinemaCity.getLocations().get(0), date).stream())
                    .collect(Collectors.toList());
            movieCache.put(date.toString(), movies);
            movies.forEach(m -> movieNameCache.put(m.getName(), m.getName()));
            return filter(movies, name, category, country, location, city, cinema);
        }
        return filter(cachedMovies, name, category, country, location, city, cinema);
    }

    public static List<Movie> filter(List<Movie> movies, String name, String category, String country, String location, String city, String cinema){
        if(name != null){
            movies.removeIf(movie -> movie.getName() != name);
        }
        if(category != null){
            //#TODO movies.removeIf(movie -> movie.getCategory() != category);
        }
        if(country != null){
            //#TODO movies.removeIf(movie -> movie.getCountry() != country);
        }
        if(location != null){
            movies.removeIf(movie -> movie.getLocation().getName() != location);
        }
        if(city != null){
            movies.removeIf(movie -> movie.getLocation().getCity() != city);
        }
        if(cinema != null){
            movies.removeIf(movie -> movie.getCinema() != Cinema.valueOf(cinema));
        }
        return movies;
    }

    public static List<String> getNames(){
        return new ArrayList<String>(movieNameCache.asMap().values());
    }

    public static List<Location> getLocations(String cinema){
        if(cinema!= null){
            switch(Cinema.valueOf(cinema)){
                case MULTIKINO:
                    return Multikino.getLocations();
                case CINEMACITY:
                    return CinemaCity.getLocations();
            }
        }
        return Stream.concat(Multikino.getLocations().stream(), CinemaCity.getLocations().stream()).collect(Collectors.toList());
    }
}
