import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class SaveLogs {
    public static void writeToFile(String line) {
        try {
            FileWriter myWriter = new FileWriter("empik.txt",true);
            myWriter.write(line +"\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}