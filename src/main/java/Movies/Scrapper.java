package Movies;


import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public abstract class Scrapper {

    public static String URL;
    public static ChromeDriver driver;
    public static final int DELAY = 3;

    public Scrapper(String url){
        this.URL = url;
        driver = ChromeDriverService.getDriver();
    }

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
