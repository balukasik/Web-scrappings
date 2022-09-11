package Movies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CinemaCity extends Scrapper {

    private static CinemaCity instance;

    private static ArrayList<String> locations;

    private CinemaCity() {
        super("https://www.cinema-city.pl/");
    }

    public static CinemaCity getInstance() {
        if (instance == null) {
            instance = new CinemaCity();
            locations = setLocations();
        }
        return instance;
    }

    public static ArrayList<String> setLocations() {
        ArrayList<String> links = new ArrayList<>();
        driver.get(URL);
        ArrayList<Object> result = (ArrayList<Object>) driver.executeScript("return apiSitesList");
        for(Object o: result){
            Map<String,String> map = (Map<String, String>) o;
            links.add(map.get("uri") + "/" + map.get("externalCode"));
        }
        return links;
    }

    public static ArrayList<String> getLocations() {
        return locations;
    }

    public List<Movie> getTimeTable(String location, Date date) {


        List<Movie> movies = new ArrayList<Movie>();
        driver.get(URL + location + "#/buy-tickets-by-cinema?at="+ new SimpleDateFormat("yyyy-MM-dd").format(date));
        delay(3);
        List<WebElement> table = driver.findElements(By.cssSelector(".row.movie-row"));
        for(WebElement elem: table){
            String title = elem.findElement(By.tagName("h3")).getText();
            List<String> times = new ArrayList<>();
            elem.findElements(By.className("qb-movie-info-column")).forEach(time -> times.add(time.getText()));
            movies.add(new Movie(title,date,times.toArray()));
        }
        return movies;

    }



}
