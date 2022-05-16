import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scrapper {

    private static final String URL = "http://olis.onyx.pl/listy/archiwum.asp?lang=";

    private ChromeDriver driver;

    public Scrapper(){
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--headless");
        driver = new ChromeDriver(op);
    }

    public void scrape(final String value){
        List<DoubleString> links = new ArrayList<DoubleString>();
        driver.get(URL+value);
        WebElement table = driver.findElement(new By.ByClassName("archive"));
        final List<WebElement> rows = table.findElements(By.tagName("tr"));
        boolean save = false;
        for(WebElement row: rows) {
            List<WebElement> tds = row.findElements(By.tagName("td"));
            if(!tds.get(1).getText().contains("za okres") && save == true){
                WebElement href = tds.get(2).findElement(By.tagName("a"));
                links.add(new DoubleString(tds.get(1).getText(),(href.getAttribute("href"))));
            }
            if(tds.get(1).getText().contains("25.12.2015 - 31.12.2015")){
                break;
            }
            if(tds.get(1).getText().contains("za okres")){
                save = true;
            }
        }
        float iteration = 0;
        float percentage = 100*iteration /(float)(links.size());

        for (DoubleString ds : links) {
            try{
                iteration++;
                System.out.println(percentage+ "% processing " + ds.s1);
                List<String> logs = scrape2(ds.s2, ds.s1);
                logs.forEach(log-> SaveLogs.writeToFile(log));
            }catch (Exception e){
                System.out.println("error with link: " + ds.s2);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        driver.quit();
    }

    public List<String> scrape2(final String value, final String dates){
        driver.get(value);
        List<String> entities = new ArrayList<String>();
        WebElement table = driver.findElements(By.tagName("tbody")).get(4);
        Entity td1 = generateMainEntity(table.findElement(By.tagName("tbody")));

        List<WebElement> tds = table.findElements(By.className("l1"));
        entities.add(dates+"; " + td1);
        for(WebElement td : tds){
            entities.add(dates+"; " + generateEntity(td));
        }
        return entities;

    }
    private Entity generateEntity(WebElement td){
        List<WebElement> elems = td.findElements(By.className("cx"));
        String spadek = "";
        for(WebElement img: elems.get(0).findElements(By.tagName("img"))){
            spadek += mapLinkToValue(img.getAttribute("src"));
        }
        String pozycja = "";
        for(WebElement img: elems.get(1).findElements(By.tagName("img"))){
            pozycja += mapLinkToValue(img.getAttribute("src"));
        }
        String notowania = "";
        for(WebElement img: elems.get(2).findElements(By.tagName("img"))){
            notowania += mapLinkToValue(img.getAttribute("src"));
        }
        String najwyzej = "";
        for(WebElement img: elems.get(3).findElements(By.tagName("img"))){
            najwyzej += mapLinkToValue(img.getAttribute("src"));
        }
        String nazwa = getString(td,"p3");
        String wykonawca = getString(td,"p2");
        String producent = getString(td,"p4");
        String kategoria = new ScrapperEmpik().scrape(nazwa + " " + wykonawca);
        return new Entity(spadek, pozycja, notowania, najwyzej, nazwa, wykonawca, producent,kategoria);
    }

    private Entity generateMainEntity(WebElement td){
        List<WebElement> elems = td.findElements(By.className("cx"));
        String spadek = "";
        for(WebElement img: elems.get(0).findElements(By.tagName("img"))){
            spadek += mapLinkToValue(img.getAttribute("src"));
        }
        String pozycja = "";
        for(WebElement img: elems.get(1).findElements(By.tagName("img"))){
            pozycja += mapLinkToValue(img.getAttribute("src"));
        }
        String notowania = "";
        for(WebElement img: elems.get(2).findElements(By.tagName("img"))){
            notowania += mapLinkToValue(img.getAttribute("src"));
        }
        String najwyzej = "";
        for(WebElement img: elems.get(3).findElements(By.tagName("img"))){
            najwyzej += mapLinkToValue(img.getAttribute("src"));
        }
        String nazwa = getString(td,"p1").split("-")[1];
        String wykonawca = getString(td,"p1").split("-")[0];
        String producent = getString(td,"p4");
        String kategoria = new ScrapperEmpik().scrape(nazwa + " " + wykonawca);
        return new Entity(spadek, pozycja, notowania, najwyzej, nazwa, wykonawca, producent, kategoria);
    }

    private String getString(WebElement td, String className) {
        if(td.findElement(By.className(className))!= null){
            return td.findElement(By.className(className)).getText();
        }
        return td.findElement(By.className(className)).findElement(By.tagName("a")).getText();
    }

    private String mapLinkToValue(String src) {
        if (src.endsWith("zbok.gif")) {
            return "bok";
        }
        if (src.endsWith("zn.gif")) {
            return "nie";
        }
        if (src.endsWith("zdol.gif")) {
            return "dół";
        }
        if (src.endsWith("zgora.gif")) {
            return "góra";
        }
        String[] words = src.split("/");
        String number = words[words.length - 1].split("\\.")[0];
        return number;
    }
}
