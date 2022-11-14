package Movies;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

public class ChromeDriverService {
    private static ChromeDriverService instance;
    private org.openqa.selenium.chrome.ChromeDriver driver;

    private ChromeDriverService(ChromeDriver chromeDriver) {
        driver = chromeDriver;
    }

    private static ChromeDriverService getInstance() {
        if (instance == null) {
            instance = new ChromeDriverService(new org.openqa.selenium.chrome.ChromeDriver(new ChromeOptions().addArguments("--headless")));
        }
        return instance;
    }

    public static org.openqa.selenium.chrome.ChromeDriver getDriver(){
        return ChromeDriverService.getInstance().driver;
    }
}
