import java.util.List;

/**
 * Outfit Selector Interface
 * 
 * Includes the core functionality for an outfit selector system.
 * 
 * Supports clothing storage, outfit generation, 
 * wear tracking, and compatibility checks.
 * 
 * @author Joselyn Quinteros
 * @author Isaiah Moliga-Puletasi
 * @author Lewhat Kahsay
 */
public interface OutfitSelectorInterface{
    //Managing clothing options 

    public void addClothingItem(ClothingItem item);

    public boolean removeClothingItem(ClothingItem item);

    //Retrieves all clothing items stored in system
    public List<ClothingItem> getAllClothingItems();

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

}
