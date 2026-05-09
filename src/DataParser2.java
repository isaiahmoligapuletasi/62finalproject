import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class DataParser2 {

    public static Wardrobe loadWardrobe(String filePath) {
        Wardrobe myWardrobe = new Wardrobe();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // for the first time the loop runs, skip the first line of data ("Clothing",
                // "Color", etc)
                line = line.trim().replace("\uFEFF", "");

                if (line.isEmpty()) {
                    continue;
                }

                if (isHeader) {
                    isHeader = false;
                    continue;
                }
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

                    ClothingItem item = new ClothingItem(name, category, color, texture, style, temperature);
                    myWardrobe.addClothingItem(item);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return myWardrobe;
    }

    // testing method
    public static void main(String[] args) {
        System.out.println("--- Starting DataParser Test ---");
        String testPath = "data/Testing_DataSet.csv";

        Wardrobe testWardrobe = loadWardrobe(testPath);

        List<ClothingItem> allItems = testWardrobe.getAllClothingItems();
        System.out.println("Items successfully loaded: " + allItems.size());

        if (allItems.size() > 0) {
            System.out.println("First item found: " + allItems.get(0).getName());
            System.out.println("Check: " + allItems.get(0).getName() + " should be a 'Tee'.");
        } else {
            System.out.println("TEST FAILED: No items found. Check your file folder!");
        }

    }
}