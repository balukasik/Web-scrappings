package Movies;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Movie {
    private final String name;
    private final LocalDate date;
    private final String[] hours;
    private final Location location;
    private final Cinema cinema;

    public Movie(String name, LocalDate date, String[] hours, Location location, Cinema cinema) {

        this.name = name;
        this.date = date;
        this.hours = hours;
        this.location = location;
        this.cinema = cinema;
    }

    @Override
    public String toString() {
        String result = cinema + " " + location + " " + name + " " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n";
        for (Object line : hours) {
            result += line + " ";
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String[] getHours() {
        return hours;
    }

    public Location getLocation() {
        return location;
    }

    public Cinema getCinema(){
        return cinema;
    }

}
