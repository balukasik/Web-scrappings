package Movies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Multikino extends Scrapper {

    private static Multikino instance;

    private static ArrayList<Location> locations;

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

    public static ArrayList<Location> setLocations() {
        ArrayList<Location> links = new ArrayList<>();
        driver.get(URL + "nasze-kina");
        delay(DELAY);
        List<WebElement> table = driver.findElements(new By.ByClassName("ml-columns__item"));
        for (int i = 0; i < table.size(); i++) {

            links.add(new Location(table.get(i).getText(), table.get(i).findElement(By.tagName("a")).getAttribute("href"), Cinema.MULTIKINO));
        }
        for(Location location : links){

            driver.get(location.getUrl());
            WebElement button = new WebDriverWait(driver, Duration.ofSeconds(DELAY)).until(ExpectedConditions.elementToBeClickable( new By.ByCssSelector("a[href *= 'dojazd-i-parking']")));
            driver.executeScript("arguments[0].click();", button);
            WebElement cinemaDetails = new WebDriverWait(driver, Duration.ofSeconds(DELAY)).until(ExpectedConditions.presenceOfElementLocated(new By.ByClassName("collapse--cinema-details")));
            cinemaDetails = new WebDriverWait(driver,Duration.ofSeconds(DELAY)).until(ExpectedConditions.presenceOfNestedElementLocatedBy(cinemaDetails, new By.ByClassName("active")));
            cinemaDetails = new WebDriverWait(driver,Duration.ofSeconds(DELAY)).until(ExpectedConditions.presenceOfNestedElementLocatedBy(cinemaDetails, new By.ByClassName("container--scroll")));
            driver.executeScript("var elems = arguments[0].getElementsByClassName(\"collapse--accordion\"); Array.from(elems).forEach(element => element.parentNode.removeChild(element));", cinemaDetails);

            String[] cinemaInfo = new WebDriverWait(driver,Duration.ofSeconds(DELAY)).until(ExpectedConditions.presenceOfNestedElementLocatedBy(cinemaDetails, new By.ByCssSelector("div"))).getAttribute("innerHTML").split("<.+>");
            if(cinemaInfo.length < 4){
                cinemaInfo = new WebDriverWait(driver,Duration.ofSeconds(DELAY)).until(ExpectedConditions.presenceOfNestedElementLocatedBy(cinemaDetails, new By.ByClassName("iconified"))).getAttribute("innerHTML").split("<.+>");
            }
            try{
                location.setAddressData(cinemaInfo[1].trim(), cinemaInfo[3].trim());
            } catch (Exception e){
                System.out.println(location.getName() + "FAILED TO LOAD");
            }
            System.out.println(location.getName() + " LOADED");
        }
        return links;
    }

    public static List<Movie> getTimeTable(Location location, LocalDate date) {


        List<Movie> movies = new ArrayList<Movie>();
        driver.get(location.getUrl() + "/teraz-gramy/alfabetyczny?data=" + date.toString());
        delay(DELAY);
        List<WebElement> table = driver.findElements(new By.ByClassName("filmlist__item"));
        for(WebElement elem: table){
            String title = elem.findElement(By.cssSelector(".filmlist__title.subtitle.h3")).getText();
            try{
                elem.findElements(By.className("times__detail")).get(4).findElement(By.tagName("a")).click();
            }catch (IndexOutOfBoundsException e) {}

            List<String> times = new ArrayList<>();
            elem.findElements(By.className("times__detail")).forEach(time -> times.add(time.getText()));
            movies.add(new Movie(title, date, times.toArray(new String[0]), location, Cinema.MULTIKINO));
        }
        return movies;

    }


    public static ArrayList<Location> getLocations() {
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
