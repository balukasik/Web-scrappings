package Movies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CinemaCity extends Scrapper {

    private static CinemaCity instance;

    private static ArrayList<Location> locations;

    private CinemaCity() {
        super("https://www.cinema-city.pl");
    }

    public static CinemaCity getInstance() {
        if (instance == null) {
            instance = new CinemaCity();
            locations = setLocations();
        }
        return instance;
    }

    public static ArrayList<Location> setLocations() {
        ArrayList<Location> links = new ArrayList<>();
        driver.get(URL);
        ArrayList<Object> result = (ArrayList<Object>) driver.executeScript("return apiSitesList");
        for(Object o: result){
            Map<String,String> map = (Map<String, String>) o;
            Map<String,String> addressMap = ((Map<String, Map<String,String>>) o).get("address");
            links.add(new Location(map.get("name"), map.get("uri") + "/" + map.get("externalCode"), addressMap.get("address1"), addressMap.get("city"), Cinema.CINEMACITY));
        }
        return links;
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static List<Movie> getTimeTable(Location location, LocalDate date) {


        List<Movie> movies = new ArrayList<Movie>();
        driver.get(URL + location.getUrl() + "#/buy-tickets-by-cinema?at="+ date.toString());
        delay(DELAY);
        List<WebElement> table = driver.findElements(By.cssSelector(".row.movie-row"));
        for(WebElement elem: table){
            String title = elem.findElement(By.tagName("h3")).getText();
            List<String> times = new ArrayList<>();
            elem.findElements(By.className("qb-movie-info-column")).forEach(time -> times.add(time.getText()));
            movies.add(new Movie(title,date,times.toArray((new String[0])), location, Cinema.CINEMACITY));
        }
        return movies;

    }



}
