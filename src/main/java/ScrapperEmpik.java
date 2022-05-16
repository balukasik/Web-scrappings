import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrapperEmpik {

    private static final String URL = "https://www.empik.com/muzyka,32,s?q=";

    private ChromeDriver driver;

    public ScrapperEmpik(){
        ChromeOptions op = new ChromeOptions();
        op.addArguments("--headless");
        driver = new ChromeDriver(op);
    }

    public String scrape(final String value){
        driver.get(URL+value);

        String table = "";
        try{

        table = driver.findElement(new By.ByClassName("product-title")).findElement(new By.ByTagName("a")).getAttribute("href");
        table = scrape2(table);
        }catch(Exception e){
            System.out.println("Not found for: "+ value);
        }
        driver.quit();
        return table;
    }

    public String scrape2(final String value){
        driver.get(value);
        String table = driver.findElements(new By.ByClassName("ta-breadcrumb")).get(2).findElement(new By.ByTagName("a")).getText();
        if (table.equals("Vinyle")){
            table = driver.findElements(new By.ByClassName("ta-breadcrumb")).get(3).findElement(new By.ByTagName("a")).getText();
        }
        return table;
    }

    public static List<String> loadData() {
        List<String> lines = new ArrayList<String>();
        try{
        File file=new File("data.txt");    //creates a new file instance
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        String line;
        while((line=br.readLine())!=null)
        {
            String[] tmp = line.split(",");
            String tmpLine = "";
            for(int i = 5;i<tmp.length ;i++){
                tmpLine += tmp[i] + " ";
            }
            lines.add(tmpLine);      //appends line to string buffer
        }
        fr.close();    //closes the
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return lines;
    }


    private String getString(WebElement td, String className) {
        if(td.findElement(By.className(className))!= null){
            return td.findElement(By.className(className)).getText();
        }
        return td.findElement(By.className(className)).findElement(By.tagName("a")).getText();
    }
}
