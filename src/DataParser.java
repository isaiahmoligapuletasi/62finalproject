
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; 
import java.util.ArrayList;
import java.util.List;

public class DataParser {

    public static void main(String[] args) {
       String filePath = "data/Testing_DataSet.csv";
       // Print the current working directory
       System.out.println("Current working directory: " + System.getProperty("user.dir"));
       System.out.println(new java.io.File(filePath).getAbsolutePath());

        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by comma
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Print result
        data.forEach(row -> System.out.println(String.join(" | ", row)));
    }
}

