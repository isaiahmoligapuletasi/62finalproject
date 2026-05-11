import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * DataParser2 reads clothing data from a CSV file and loads the 
 * clothing items into a Wardrobe object.
 * This class is responsible for reading clothing data files, 
 * creasting clothing item objects, and adding items into the 
 * correct wardrobe categories. 
 * 
 * @author Lewhat Kahsay
 * @author Isaiah Moliga-Puletasi
 * @author Joselyn Quinteros
 */
public class DataParser2 {
    /**
     * Reada a CSV file and loads all clothinng items into a Wardrobe.
     * 
     * Each row in the CSV file is converted into a ClothingItem object and 
     * added into the wardrobe using the addClothingItem method. Empty lines 
     * and the header row are skipped. 
     * 
     * @param filePath the path to the CSV file
     * @return a Wardrobe object containing all loaded clothing items
     */
    public static Wardrobe loadWardrobe(String filePath) {
        //create an empty wardrobe
        Wardrobe myWardrobe = new Wardrobe();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            //skip the CSV header row
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                // removes extra spaces 
                line = line.trim().replace("\uFEFF", "");

                //skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                //skip the first row containing column titles
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                //split each row into separate values using commas
                String[] values = line.split(",");

                // just to ensure all data maps to correct place
                if (values.length >= 7) {

                    String name = values[0].trim();
                    String color = values[1].trim();
                    String texture = values[2].trim();

                    // skipping wearCount (constructor sets it to 0 automatically)
                    String style = values[4].trim();
                    String category = values[5].trim();
                    String temperature = values[6].trim();

                    // create a clothing item using CSV data
                    ClothingItem item = new ClothingItem(name, category, color, texture, style, temperature);

                    //add the item into the wardrobe
                    myWardrobe.addClothingItem(item);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return myWardrobe;
    }

    /**
     * Testing method used to verify that clothing items are correctly
     * loaded from the CSV file. 
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("--- Starting DataParser Test ---");

        //path to the testing dataset
        String testPath = "data/Testing_DataSet.csv";

        //load the test wardrobe
        Wardrobe testWardrobe = loadWardrobe(testPath);

        //store all clothing items into a list
        List<ClothingItem> allItems = testWardrobe.getAllClothingItems();
        System.out.println("Items successfully loaded: " + allItems.size());

        //check whether items were loaded correctly
        if (allItems.size() > 0) {

            System.out.println("First item found: " + allItems.get(0).getName());
            System.out.println("Check: " + allItems.get(0).getName() + " should be a 'Tee'.");

        } else {
            
            System.out.println("TEST FAILED: No items found. Check your file folder!");
        }

    }
}