import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserInput {
    // helper class for ranking
    static class OutfitOption {
        // holds two items together (top & bottoms/dress & jacket)
        // so we can give each pair a single score when looping
        // through outfit combinations
        ClothingItem item1;
        ClothingItem item2;

        //optional jacket
        ClothingItem jacket; 
        int score;

        OutfitOption(ClothingItem i1, ClothingItem i2, ClothingItem jacket, int s) {
            this.item1 = i1;
            this.item2 = i2;
            this.jacket = jacket;
            this.score = s;
        }
    }

    public static void main(String[] args) {
        // loading data into the Wardrobe
        String path = "data/Testing_DataSet.csv";
        Wardrobe myCloset = DataParser2.loadWardrobe(path);
        Scanner reader = new Scanner(System.in);
        boolean running = true;

        System.out.println("============ VIRTUAL OUTFIT SELECTOR ============");
        // check all items loaded correctly
        System.out.println("Loaded " + myCloset.getAllClothingItems().size() + " items from the dataset.");

        // the main loop the keeps the program running until user exits
        while (running) {
            // Display menu options
            System.out.println("\n\n==========================================");
            // TODO: cutomize to user's name
            System.out.println("            WARDROBE");
            System.out.println("==========================================");
            System.out.println("1. Generate Outfit Suggestions (Top 3)");
            System.out.println("2. View Laundry Hamper");
            System.out.println("3. Mark an Item as Dirty (Move to Hamper)");
            System.out.println("4. Wash an Item (Move to Wardrobe)");
            System.out.println("5. Veiw Most/Least Worn Items");
            System.out.println("6. Exit");
            System.out.println("Select an option (1-6): ");

            String choice = reader.nextLine();

            // connecting the user's choice to the correct helper method
            if (choice.equals("1")) {
                completeOutfitGeneration(myCloset, reader);
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

    private static void completeOutfitGeneration(Wardrobe w, Scanner reader) {
        System.out.println("\n===== OUTIFIT PREFERENCES=====");

        // gathering the users preferences (type, style, and color)
        System.out.println("\nWhat type of outfit would you like?");
        System.out.print("  [A] Tops/Bottoms\n  [B] Jackets/Dresses\nEnter: ");
        String typePreference = reader.nextLine().toLowerCase();

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

        // we only want to use the relevant lists based on
        // the users input (ex. if we choose tops/bottoms
        // list1 becomes a copy of the tops array in Wardrobe
        // and list2 becomes a copy of the bottoms array in Wardrobe)
        ArrayList<ClothingItem> list1;
        ArrayList<ClothingItem> list2;

        // Type + Bottom 
        if (typePreference.equals("a")) {
            list1 = w.getTops();
            list2 = w.getBottoms();

            System.out.println("\nWould you like a jacket? (yes/no)");
            String jacketPreference = reader.nextLine().toLowerCase();

            while(!jacketPreference.equals("yes") && !jacketPreference.equals("no")) {
                System.out.println("Please enter yes or no: ");
                jacketPreference = reader.nextLine().toLowerCase();
            }

            wantJacket = jacketPreference.equals("yes");
        } else {
            //Dress + Jacket
            list1 = w.getJackets();
            list2 = w.getDresses();
        }

        // looping through every possible combination of the two
        // lists (ex. all combinations of tops and bottoms) to create
        // outfit objects (OutfitOption)
        ArrayList<OutfitOption> allPossibilities = new ArrayList<>();

        for (ClothingItem i1 : list1) {
            for (ClothingItem i2 : list2) {
                int score = 0;

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

                //Optional Jacket, start with no jacket selected
                ClothingItem extraJacket = null;

                //If the user says they want a jacket then run
                if (typePreference.equals("a") && wantJacket) {
                    int bestJacketScore = Integer.MIN_VALUE;


                    for (ClothingItem jacket : w.getJackets()) {
                        int jacketScore = 0;

                        //style
                        if (jacket.getStyle().toLowerCase().equals(stylePreference)) {
                            jacketScore += 10;
                        }

                        if (w.isColorCompatible(jacket, i1)) {
                            jacketScore += 5;
                        }
                        if (w.isTextureCompatible(jacket, i1)) {
                            jacketScore += 5;
                        }

                        if (w.isColorCompatible(jacket, i2)) {
                            jacketScore += 5;
                        }
                        if (w.isTextureCompatible(jacket, i2)) {
                            jacketScore += 5;
                        }
                        
                        //Take into account colorPreference
                        if(!colorPreference.equals("any") && 
                        jacket.getColor().toLowerCase().contains(colorPreference)) {
                            jacketScore += 3;

                        }

                        //Keep highest scoring jacket
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
                // this is just typical comparator behavior (the subtraction
                // i mentioned in class) - if the algorithm sees a negative
                // result, it will know that outfit 1's score is smaller and
                // will place outfit 1 after outfit 2 in the sorted
                // array (opposite if the result is positive, and leaves
                // items in their order if result is 0/the scores are equal)
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

                    if(chosen.jacket != null) {
                        w.markItemAsWorn(chosen.jacket);

                    }
                    System.out.println(
                            "Your wardrobe has been updated! Items are still in your closer, mark them dirty later if needed.");
                }
            } catch (Exception e) {
                System.out.println("Invalid selection.");
            }
        }
    }

    // shows the item in the laundry hamper
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

    // searches all items for a name match and moves it to the laundry hamper
    private static void completeMarkDirty(Wardrobe w, Scanner reader) {
        System.out.print("Enter the name of the item that is dirty: ");
        String nameInput = reader.nextLine().toLowerCase();

        boolean found = false;
        for (ClothingItem i : w.getAllClothingItems()) {
            // .contains() in case the user input isn't an exact match
            if (i.getName().toLowerCase().contains(nameInput) && !i.isDirty()) {
                w.markItemDirty(i);
                System.out.println("Found it! '" + i.getName() + "' moved to laundry.");
                found = true;
                // stop searching after we find the first match (not sure if we want to do this
                // another way)
                break;
            }
        }

        if (!found) {
            System.out.println("Could not find a clean item matching '" + nameInput + "'.");
        }
    }

    // finds an item in the laundry hamper and moves it back to its respective
    // category list
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

    // displays most/least worn stats
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
