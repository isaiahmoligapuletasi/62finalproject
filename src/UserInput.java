import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * UserInput is the main class for the Outfit Selector
 * 
 * This class handles all our user interations through the
 * console menu system. This includes: generating outfits suggestions, 
 * viewing the laundry hamper, marking clothing items as dirty, washing 
 * clothing items/marking items as clean, and displaying clothing wear statistics. 
 * 
 * This class also contains a helper class, OutfitOption, which stores outfit 
 * combinations and their scores.
 * 
 * @author Lewhat Kahsay
 * @author Joselyn Quinteros
 * @author Isaiah Moliga-Puletasi
 */
public class UserInput {
    
    /**
     * Helper class used to store a complete outfit combination 
     * along with its calculated score.
     * 
     * Each OutfitOption object contains: two required clothing items, 
     * an optional jacket, and an overall compatibility score. 
     * These objects are later sorter from highest score to lowest to 
     * generate the best outfit recommendations.
     */
    static class OutfitOption {
        // required clothing items
        ClothingItem item1;
        ClothingItem item2;

        // optional jacket
        ClothingItem jacket;

        //compatability score for the outfit based on users preferences
        int score;

        /**
         * Constructs and OutfitOption object containing a complete 
         * outfit and its ranking score.
         * 
         * @param i1 the first clothing item in the outfit
         * @param i2 the second clothinh item in the outfit
         * @param jacket the optional jacket for the outfit
         * @param s the ranking score
         */
        OutfitOption(ClothingItem i1, ClothingItem i2, ClothingItem jacket, int s) {
            this.item1 = i1;
            this.item2 = i2;
            this.jacket = jacket;
            this.score = s;
        }
    }

    /**
     * Main method that starts the Outfit Selector.
     * 
     * This method loads clothing data from the CSV file, 
     * prompts the user for input, and displays the main menu.
     * 
     * This program runs until the user chooses to exit.
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // loading data into the wardrobe
        String path = "data/62 FINAL_GENERATED DATA WITH TEMPS.csv";
        Wardrobe myCloset = DataParser2.loadWardrobe(path);
        Scanner reader = new Scanner(System.in);

        System.out.println("\n============ VIRTUAL OUTFIT SELECTOR ============");

        System.out.print("Enter your name: ");
        String userName = reader.nextLine();

        String currentTemp = WeatherService.getCurrentTemperature();

        // the main loop the keeps the program running until user exits
        boolean running = true;

        while (running) {
            // Display menu options
            System.out.println("\n\n==========================================");

            System.out.println("            " + userName.toUpperCase() + "'S WARDROBE");

            System.out.println("\n           CURRENT WEATHER: " + currentTemp.toUpperCase());

            System.out.println("==========================================");

            System.out.println("1. Generate Outfit Suggestions (Top 3)");
            System.out.println("2. View Laundry Hamper");
            System.out.println("3. Mark an Item as Dirty (Move to Hamper)");
            System.out.println("4. Wash an Item (Move to Wardrobe)");
            System.out.println("5. View Most/Least Worn Items");
            System.out.println("6. Exit");

            System.out.println("Select an option (1-6): ");

            String choice = reader.nextLine();

            // connecting the user's choice to helper methods
            if (choice.equals("1")) {
                completeOutfitGeneration(myCloset, reader, currentTemp);

            } else if (choice.equals("2")) {
                displayHamper(myCloset);

            } else if (choice.equals("3")) {
                completeMarkDirty(myCloset, reader);

            } else if (choice.equals("4")) {
                completeItemWash(myCloset, reader);

            } else if (choice.equals("5")) {
                displayStats(myCloset);

            } else if (choice.equals("6")) {
                running = false;
                System.out.println("Goodbye!");

            } else {
                System.out.println("Invalid input. Please choose 1-6.");
            }
        }

        reader.close();
    }

    /**
     * Generates and ranks outfit suggestions based on users clothing 
     * preferences and current weather conditions. 
     * 
     * This method gathers users preferences and compares all possible 
     * outfit combinations. Here is where the compatibility scores are 
     * calaculate to be able to sort outfits from highest to lowest scores. 
     * Displayes teh top 3 outfit suggestions as well. 
     * 
     * Outfit scores are based on weather, style, color, and texture compatibility.
     * There is also a option color preference matching 
     * 
     * @param w the users wardrobe 
     * @param reader Scanner object user for users input 
     * @param weather the current weather temperature
     */
    private static void completeOutfitGeneration(Wardrobe w, Scanner reader, String weather) {

        System.out.println("\n===== OUTIFIT PREFERENCES=====");

        // gathering the users preferences (type, style, and color)
        System.out.println("\nWhat type of outfit would you like?");
        System.out.print("  [A] Tops/Bottoms\n  [B] Jackets/Dresses\nEnter: ");
        String typePreference = reader.nextLine().toLowerCase();

        //validate the users input
        while (!typePreference.equals("a") && !typePreference.equals("b")) {
            System.out.println("Error: Please enter 'A' or 'B': ");
            typePreference = reader.nextLine().toLowerCase();
        }

        System.out.println("\nWhat is the style of the outfit?");
        System.out.println("  - Casual");
        System.out.println("  - Formal");
        System.out.println("  - Athletic");
        System.out.println("  - Loungewear");
        System.out.println("  - Outerwear");

        System.out.print("Enter style: ");

        String stylePreference = reader.nextLine().toLowerCase();

        //validate users style input
        while (!stylePreference.equals("casual") && !stylePreference.equals("formal") &&
                !stylePreference.equals("athletic") && !stylePreference.equals("loungewear") &&
                !stylePreference.equals("outerwear")) {

            System.out.println("\nError: Please choose a valid style from the list above");
            System.out.print("Enter style: ");

            stylePreference = reader.nextLine().toLowerCase();
        }

        System.out.println("\nIs there a specific color you want to wear?");
        System.out.print("(or type 'any'): ");
        String colorPreference = reader.nextLine().toLowerCase();

        boolean wantJacket = false;

        // store clothing lists based on outfit type
        ArrayList<ClothingItem> list1;
        ArrayList<ClothingItem> list2;

        // tops + bottom
        if (typePreference.equals("a")) {
            list1 = w.getTops();
            list2 = w.getBottoms();

            System.out.println("\nWould you like a jacket? (yes/no)");
            String jacketPreference = reader.nextLine().toLowerCase();

            while (!jacketPreference.equals("yes") && !jacketPreference.equals("no")) {
                System.out.println("Please enter yes or no: ");
                jacketPreference = reader.nextLine().toLowerCase();
            }

            wantJacket = jacketPreference.equals("yes");
        } else {

            // dress + jacket
            list1 = w.getJackets();
            list2 = w.getDresses();
        }

        // store all outfit combinations
        ArrayList<OutfitOption> allPossibilities = new ArrayList<>();

        //compare all possible outfit combinations
        for (ClothingItem i1 : list1) {
            for (ClothingItem i2 : list2) {
                int score = 0;

                //weather compatability has highest priority
                if (i1.getTemperature().equals(weather)) {
                    score += 20;
                }
                if (i2.getTemperature().equals(weather)) {
                    score += 20;
                }

                // if each item in the outfit matches the user style
                // preference, we award the outfit 10 points per match
                // (max 20)
                if (i1.getStyle().toLowerCase().equals(stylePreference)) {
                    score += 10;
                }

                if (i2.getStyle().toLowerCase().equals(stylePreference)) {
                    score += 10;
                }

                // if both items in the outfit are compatibile by color
                // and texture, the outfit is awarded 5 points per match
                // (max 10)
                if (w.isColorCompatible(i1, i2)) {
                    score += 5;
                }
                if (w.isTextureCompatible(i1, i2)) {
                    score += 5;
                }

                // if both items in the outfit matches the user color
                // preference, we award the outfit 3 points per match
                // (max 6 points)
                // IMPORTANT TO NOTE: if the user selects any, this entire
                // section is skipped
                if (!colorPreference.equals("any")) {
                    if (i1.getColor().toLowerCase().contains(colorPreference)) {
                        score += 3;
                    }
                    if (i2.getColor().toLowerCase().contains(colorPreference)) {
                        score += 3;
                    }
                }

                // optional jacket, start with no jacket selected
                ClothingItem extraJacket = null;

                if (typePreference.equals("a") && wantJacket) {
                    int bestJacketScore = Integer.MIN_VALUE;
                    
                    //find highest scoring jacket
                    for (ClothingItem jacket : w.getJackets()) {
                        int jacketScore = 0;

                        // style compatability
                        if (jacket.getStyle().toLowerCase().equals(stylePreference)) {
                            jacketScore += 10;
                        }

                        //compatability with item 1
                        if (w.isColorCompatible(jacket, i1)) {
                            jacketScore += 5;
                        }

                        if (w.isTextureCompatible(jacket, i1)) {
                            jacketScore += 5;
                        }

                        //compatability with item 2
                        if (w.isColorCompatible(jacket, i2)) {
                            jacketScore += 5;
                        }

                        if (w.isTextureCompatible(jacket, i2)) {
                            jacketScore += 5;
                        }

                        // take into account color preference
                        if (!colorPreference.equals("any") &&
                            jacket.getColor().toLowerCase().contains(colorPreference)) {
                            jacketScore += 3;

                        }

                        // Keep highest scoring jacket
                        if (jacketScore > bestJacketScore) {
                            extraJacket = jacket;
                            bestJacketScore = jacketScore;
                        }
                    }
                    if (extraJacket != null) {
                        score += bestJacketScore;
                    }
                }
                // adding each outfit object (top, bottom, and outfit score)
                // to the master list of all outfit combinations
                allPossibilities.add(new OutfitOption(i1, i2, extraJacket, score));
            }
        }

        // we sort the list of all outfit objects/possibilites from highest
        // to lowest based on score
        Collections.sort(allPossibilities, new Comparator<OutfitOption>() {
            @Override
            public int compare(OutfitOption o1, OutfitOption o2) {
                return Integer.compare(o2.score, o1.score);
            }
        });

        if (allPossibilities.isEmpty()) {
            System.out.println("No items found to generate suggestions.");

        } else {

            System.out.println("\n--- TOP 3 RANKED SUGGESTIONS ---");

            // if are less than 3 options, justs shows how many actually exist
            int count = Math.min(3, allPossibilities.size());

            for (int i = 0; i < count; i++) {
                OutfitOption opt = allPossibilities.get(i);

                String outfitText = opt.item1.getName() + " (" + opt.item1.getColor() + ") + "
                        + opt.item2.getName() + " (" + opt.item2.getColor() + ")";

                if (opt.jacket != null) {
                    outfitText += " + " + opt.jacket.getName() + " (" + opt.jacket.getColor() + ")";
                }

                System.out.println((i + 1) + ": " + outfitText);
            }

            System.out.print("\nPick an outfit (1-3) or 0 to skip: ");
            try {
                int pick = Integer.parseInt(reader.nextLine());

                if (pick > 0 && pick <= count) {
                    OutfitOption chosen = allPossibilities.get(pick - 1);

                    w.markItemAsWorn(chosen.item1);
                    w.markItemAsWorn(chosen.item2);

                    if (chosen.jacket != null) {
                        w.markItemAsWorn(chosen.jacket);

                    }
                    System.out.println(
                            "Your wardrobe has been updated! Items are still in your closet, mark them dirty later if needed.");
                }
            } catch (Exception e) {
                System.out.println("Invalid selection.");
            }
        }
    }

    /**
     * Displays the dirty clothing items currently stored in 
     * the laundry hamper. 
     * 
     * @param w the users wardrobe
     */
    public static void displayHamper(Wardrobe w) {
        System.out.println("\n--- LAUNDRY HAMPER ---");

        ArrayList<ClothingItem> hamper = w.getLaundryHamper();

        if (hamper.isEmpty()) {
            System.out.println("The hamper is empty.");

        } else {

            for (ClothingItem item : hamper) {
                System.out.println("- " + item.getName() + " (" + item.getColor() + ")");
            }
        }
    }

    /**
     * Allows the user to mark a clothing item as dirty and move 
     * it to the laundry hamper.
     * 
     * The user may enter parts of an item's name to search. If there 
     * exists multiplle matching items then the user is asked to choose
     * the correct item from a numbered list. 
     * 
     * Once choosen, then item is removed from the wardrobe and added
     * to the laundry hamper.
     * 
     * @param w the users wardrobe
     * @param reader Scanner to read user input 
     */
   private static void completeMarkDirty(Wardrobe w, Scanner reader) {
    System.out.println("\n--- MARK ITEM AS DIRTY ---");
    System.out.print("Enter part of the item name (ex: 'halter', 'jeans', 'raincoat'): ");

    String nameInput = reader.nextLine().toLowerCase();

    ArrayList<ClothingItem> matches = new ArrayList<>();

    // find all matching clean items
    for (ClothingItem i : w.getAllClothingItems()) {
        if (!i.isDirty() &&
            i.getName().toLowerCase().contains(nameInput)) {
            matches.add(i);
        }
    }

    // if there are no matches to the users input then print message
    if (matches.isEmpty()) {
        System.out.println("No clean items found matching '" + nameInput + "'.");
        return;
    }

    // one match then auto-select
    if (matches.size() == 1) {
        ClothingItem item = matches.get(0);
        w.markItemDirty(item);
        System.out.println("Found it! '" + item.getName() + "' moved to laundry.");
        return;
    }

    // multiple matches then user chooses which one is dirty
    System.out.println("\nMultiple items found. Please choose:");

    for (int j = 0; j < matches.size(); j++) {
        ClothingItem item = matches.get(j);
        System.out.println((j + 1) + ": " + item.getName() + " (" + item.getColor() + ")");
    }

    System.out.print("Enter the number of the item: ");

    try {
        int choice = Integer.parseInt(reader.nextLine());

        if (choice < 1 || choice > matches.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        ClothingItem selected = matches.get(choice - 1);
        w.markItemDirty(selected);

        System.out.println("Found it! '" + selected.getName() + "' moved to laundry.");

    } catch (Exception e) {
        System.out.println("Invalid input.");
    }
}

    /**
     * Allows the user to wash a dirty item and return it into 
     * the wardrobe.
     * This methods searches the laundry hamper for an item matching 
     * the users input. Once a item is marked cleaned the wear count resets. 
     * 
     * 
     * @param w the users wardrobe
     * @param reader Scanner used for user input
     */
    private static void completeItemWash(Wardrobe w, Scanner reader) {
        System.out.print("Enter the name of the item you washed: ");
        String nameInput = reader.nextLine().toLowerCase();
        boolean found = false;

        // looking in laundry hamper
        ArrayList<ClothingItem> hamper = w.getLaundryHamper();
        
        for (ClothingItem i : hamper) {
            if (i.getName().toLowerCase().contains(nameInput)) {
                w.markItemClean(i);
                System.out.println("'" + i.getName() + "' is now clean and back in the wardrobe.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("That item was not found in the hamper.");
        }
    }

    /**
     * Displayes the most worn and least worn items for each clothing category.
     * 
     * Wear counts are determined using each items stored wearCount value.
     * 
     * @param w the users wardrobe
     */
    private static void displayStats(Wardrobe w) {
        System.out.println("\n--- MOST/LEAST WORN STATISTICS ---");

        // tops
        ClothingItem mTop = w.getMostWornTop();
        ClothingItem lTop = w.getLeastWornTop();
        if (mTop != null) {
            System.out.println("Most Worn Top: " + mTop.getName() + " (" + mTop.getWearCount() + " wears)");
            System.out.println("Least Worn Top: " + lTop.getName() + " (" + lTop.getWearCount() + " wears)");
        } else {
            System.out.println("Tops: No clean tops available to track.");
        }

        System.out.println("----------------------------------");

        // bottoms
        ClothingItem mBot = w.getMostWornBottom();
        ClothingItem lBot = w.getLeastWornBottom();
        if (mBot != null) {
            System.out.println("Most Worn Bottom: " + mBot.getName() + " (" + mBot.getWearCount() + " wears)");
            System.out.println("Least Worn Bottom: " + lBot.getName() + " (" + lBot.getWearCount() + " wears)");
        } else {
            System.out.println("Bottoms: No clean bottoms available to track.");
        }

        System.out.println("----------------------------------");

        // jackets
        ClothingItem mJack = w.getMostWornJacket();
        ClothingItem lJack = w.getLeastWornJacket();
        if (mJack != null) {
            System.out.println("Most Worn Jacket: " + mJack.getName() + " (" + mJack.getWearCount() + " wears)");
            System.out.println("Least Worn Jacket: " + lJack.getName() + " (" + lJack.getWearCount() + " wears)");
        } else {
            System.out.println("Jackets: No clean jackets available to track.");
        }

        System.out.println("----------------------------------");

        // dresses
        ClothingItem mDress = w.getMostWornDress();
        ClothingItem lDress = w.getLeastWornDress();
        if (mDress != null) {
            System.out.println("Most Worn Dress: " + mDress.getName() + " (" + mDress.getWearCount() + " wears)");
            System.out.println("Least Worn Dress: " + lDress.getName() + " (" + lDress.getWearCount() + " wears)");
        } else {
            System.out.println("Dresses: No clean dresses available to track.");
        }
    }

}
