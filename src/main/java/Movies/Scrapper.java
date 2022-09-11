package Movies;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public abstract class Scrapper {

    public static String URL;
    public static ChromeDriver driver;


    public Scrapper(String url){
        this.URL = url;
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--headless");
        driver = new ChromeDriver(op);
    }

    public static void delay(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
