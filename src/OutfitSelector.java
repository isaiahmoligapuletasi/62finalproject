import java.util.List;

/**
 * Outfit Selector Interface
 * 
 * Includes the core functionality for an outfit selector system.
 * 
 * Supports clothing storage, filtering, outfit generation, 
 * wear tracking, compatibility checks, and scoring
 */
public interface OutfitSelector{
    //Managing clothing options 

    public void addClothingItem(ClothingItem item);

    public boolean removeClothingItem(ClothingItem item);

    //Retrieves all clothing items stored in system
    public List<ClothingItem> getAllClothingItems();

    //Generated an outfit based on user input like 
    //"I need something casual for a hot day"
    public Outfit generateOutfit(String userInput);

    //Wear Tracking 
    public void markItemAsWorn(ClothingItem item);

    public int getWearCount(ClothingItem item);

    //Classifying item as clean or dirty 
    public void markItemDirty(ClothingItem item);

    public void markItemClean(ClothingItem item);

    public boolean isItemDirty(ClothingItem item);

    //Check whether two clothing items work well together
    public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2);
    public boolean isColorCompatible(ClothingItem item1, ClothingItem item2);

    //Convert natural language input into usable keyword for filtering
    public List<String> parseUserInput(String input);

    //Returns the top B most worn items
    public List<ClothingItem> getMostWornItems(int topB);

}
