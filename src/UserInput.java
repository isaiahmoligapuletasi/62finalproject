import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserInput {
    // helper class for ranking
    static class OutfitOption {
        ClothingItem item1;
        ClothingItem item2;
        int score;

        OutfitOption(ClothingItem i1, ClothingItem i2, int s) {
            this.item1 = i1;
            this.item2 = i2;
            this.score = s;
        }
    }

    public static void main(String[] args) {
        // loading data
        String path = "data/Testing_DataSet.csv";
        Wardrobe myCloset = DataParser2.loadWardrobe(path);
        Scanner reader = new Scanner(System.in);
        boolean running = true;

        System.out.println("============ VIRTUAL OUTFIT SELECTOR ============");
        // check all items loaded correctly
        System.out.println("Loaded " + myCloset.getAllClothingItems().size() + " items from the dataset.");

        while (running) {
            // Display menu options
            System.out.println("\n\n==========================================");
            System.out.println("            MAIN MENU");
            System.out.println("==========================================");
            System.out.println("1. Generate Outfit Suggestions (Top 3)");
            System.out.println("2. View Laundry Hamper");
            System.out.println("3. Mark an Item as Dirty (Move to Hamper)");
            System.out.println("4. Wash an Item (Move to Wardrobe)");
            System.out.println("5. Veiw Most/Least Worn Items");
            System.out.println("6. Exit");
            System.out.println("Select an option (1-6): ");

            String choice = reader.nextLine();

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
        // get preferences
        System.out.println("\n===== OUTIFIT PREFERENCES=====");

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
        // do we want to print a list of all the colors/patterns we have (there are 72
        // unique ones)
        System.out.println("\nIs there a specific color you want to wear?");
        System.out.print("(or type 'any'): ");
        String colorPreference = reader.nextLine().toLowerCase();

        // lists to store top 3 matches
        ArrayList<ClothingItem> list1;
        ArrayList<ClothingItem> list2;

        if (typePreference.equals("a")) {
            list1 = w.getTops();
            list2 = w.getBottoms();
        } else {
            list1 = w.getJackets();
            list2 = w.getDresses();
        }

        // loop through all items and score them
        ArrayList<OutfitOption> allPossibilities = new ArrayList<>();

        for (ClothingItem i1 : list1) {
            for (ClothingItem i2 : list2) {
                int score = 0;

                // checking for style match
                if (i1.getStyle().toLowerCase().equals(stylePreference)) {
                    score += 10;
                }

                if (i2.getStyle().toLowerCase().equals(stylePreference)) {
                    score += 10;
                }

                // checking for compatability
                if (w.isColorCompatible(i1, i2)) {
                    score += 5;
                }
                if (w.isTextureCompatible(i1, i2)) {
                    score += 5;
                }

                // checking for color match
                if (!colorPreference.equals("any")) {
                    if (i1.getColor().toLowerCase().contains(colorPreference)) {
                        score += 3;
                    }
                    if (i2.getColor().toLowerCase().contains(colorPreference)) {
                        score += 3;
                    }
                }

                allPossibilities.add(new OutfitOption(i1, i2, score));
            }

        }

        Collections.sort(allPossibilities, new Comparator<OutfitOption>() {
            @Override
            public int compare(OutfitOption o1, OutfitOption o2) {
                return o2.score - o1.score;
            }
        });

        if (allPossibilities.isEmpty()) {
            System.out.println("No items found to generate suggestions.");
        } else {
            System.out.println("\n--- TOP 3 RANKED SUGGESTIONS ---");
            int count = Math.min(3, allPossibilities.size());
            for (int i = 0; i < count; i++) {
                OutfitOption opt = allPossibilities.get(i);
                System.out.println((i + 1) + ": " + opt.item1.getName() + " (" + opt.item1.getColor() + ") + "
                        + opt.item2.getName() + " (" + opt.item2.getColor() + ")");
            }

            System.out.print("\nPick an outfit (1-3) or 0 to skip: ");
            try {
                int pick = Integer.parseInt(reader.nextLine());
                if (pick > 0 && pick <= count) {
                    w.markItemAsWorn(allPossibilities.get(pick - 1).item1);
                    w.markItemAsWorn(allPossibilities.get(pick - 1).item2);
                    System.out.println(
                            "Your wardrobe has been updated! Items are still in your closer, mark them dirty later if needed.");
                }
            } catch (Exception e) {
                System.out.println("Invalid selection.");
            }
        }
    }

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
