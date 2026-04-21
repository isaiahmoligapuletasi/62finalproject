import java.util.ArrayList;

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

    //Looking at all clothing items
    public ArrayList<ClothingItem> getAllClothingItems();

    //Filtering 

    //Top or Bottom
    public ArrayList<ClothingItem> filterByType(String type);

    //Casual, formal, athletic, etc
    public ArrayList<ClothingItem> filterByStyle(String style);

    //Hot, Snowy, etc
    public ArrayList<ClothingItem> filterByWeather(String weather);

    //Generated an outfit based on user input like 
    //"I need something casual for a hot day"
    public Outfit generateOutfit(String userInput);

    //Wear Tracking 
    public void markItemAsWorn(ClothingItem item);

    public void incrementWearCount(ClothingItem item);

    public int getWearCount(ClothingItem item);

    //Classifying item as clean or dirty 
    public void itemDirty(ClothingItem item);

    public void itemClean(ClothingItem item);

    public boolean isItemDirty(ClothingItem item);

    //Check whether two clothing items work well together
    public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2);
    





    


    
}
