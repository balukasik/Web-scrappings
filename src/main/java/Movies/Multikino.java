package Movies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Multikino extends Scrapper {

    private static Multikino instance;

    private static ArrayList<String> locations;

    private Multikino() {
        super("https://multikino.pl/");
    }

    public static Multikino getInstance() {
        if (instance == null) {
            instance = new Multikino();
            locations = setLocations();
        }
        return instance;
    }

    public static ArrayList<String> setLocations() {
        ArrayList<String> links = new ArrayList<>();
        driver.get(URL + "nasze-kina");
        delay(3);
        for (int i = 0; i < driver.findElements(new By.ByClassName("ml-columns__item")).size(); i++) {
            links.add(driver.findElements(new By.ByClassName("ml-columns__item")).get(i).getText());
        }
        return links;
    }

    public static List<Movie> getTimeTable(String location, Date date) {


        List<Movie> movies = new ArrayList<Movie>();
        driver.get(URL + "repertuar/" + location + "/teraz-gramy/alfabetyczny?data=" + new SimpleDateFormat("dd-MM-yyyy").format(date));
        delay(3);
        List<WebElement> table = driver.findElements(new By.ByClassName("filmlist__item"));
        for(WebElement elem: table){
            String title = elem.findElement(By.cssSelector(".filmlist__title.subtitle.h3")).getText();
            try{
                elem.findElements(By.className("times__detail")).get(4).findElement(By.tagName("a")).click();
            }catch (IndexOutOfBoundsException e) {}

            List<String> times = new ArrayList<>();
            elem.findElements(By.className("times__detail")).forEach(time -> times.add(time.getText()));
            movies.add(new Movie(title, date ,times.toArray()));
        }
        return movies;

    }


    public static ArrayList<String> getLocations() {
        return locations;
    }

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds + 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
