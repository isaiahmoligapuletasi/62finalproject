import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the OutfitSelectorInterface interface by
 * providing functionality for clothing mangements, compatibility checks, 
 * and clothing wear stats. 
 * 
 * Wardrobe stores and manages all clothing items used by our Outfit 
 * Selector. This class separates clothing into categories such as tops,
 * bottoms, jackets, dresses, and dirty clothes. 
 * 
 * @author Joselyn Quinteros 
 * @author Isaiah Moliga-Puletasi
 * @author Lewhat Kahsay
 */
public class Wardrobe implements OutfitSelectorInterface {

  // clean tops
  private ArrayList<ClothingItem> tops;

  // clean bottoms
  private ArrayList<ClothingItem> bottoms;

  // clean jackets
  private ArrayList<ClothingItem> jackets;

  // clean dresses
  private ArrayList<ClothingItem> dresses;

  // dirty clothes
  private ArrayList<ClothingItem> laundryHamper;

  /**
   * Constructs and empty Wardrobe object.
   * Intializes all clothing categories lists and laundry hamper.
   */
  public Wardrobe() {
    this.tops = new ArrayList<>();
    this.bottoms = new ArrayList<>();
    this.jackets = new ArrayList<>();
    this.dresses = new ArrayList<>();
    this.laundryHamper = new ArrayList<>();
  }

  // getters

  /**
   * Returns a copy of all clean tops.
   * 
   * @return ArrayList containing all clean tops
   */
  public ArrayList<ClothingItem> getTops() {
    return new ArrayList<>(tops);
  }

  /**
   * Retruns a copy of all clean bottoms. 
   * 
   * @return ArrayList containing all clean bottoms
   */
  public ArrayList<ClothingItem> getBottoms() {
    return new ArrayList<>(bottoms);
  }

  /**
   * Returns a copy all clean jackets
   * 
   * @return ArrayList containing all clean jackets
   */
  public ArrayList<ClothingItem> getJackets() {
    return new ArrayList<>(jackets);
  }

  /**
   * Retruns a copy of all clean dresses
   * 
   * @return ArrayList of all clean dresses
   */
  public ArrayList<ClothingItem> getDresses() {
    return new ArrayList<>(dresses);
  }

  /**
   * Returns a copy of all dirty clothing items in the laundry 
   * hamper.
   * 
   * @return ArrayList with all dirty clothing items
   */
  public ArrayList<ClothingItem> getLaundryHamper() {
    return new ArrayList<>(laundryHamper);
  }

 /**
  * Adds a clothing item to the correct category list. 
  * If the item si dirty, it is placed into the laundry hamper, 
  * otherwise, the item is put into tops, bottoms, jackets, or 
  * dresses based on its category and name.
  * 
  * @param item the clothing item to add
  */
  public void addClothingItem(ClothingItem item) {

    //dirty items automatically go into the hamper
    if (item.isDirty()) {
      laundryHamper.add(item);

    } else {

      String type = item.getCategory().toLowerCase();
      String name = item.getName().toLowerCase();

      // jackets
      if (type.contains("jacket") || name.contains("puffer") || name.contains("vest")
          || name.contains("coat") || name.contains("hoodie") || name.contains("sweatshirt")) {
        jackets.add(item);

      // bottoms
      } else if (type.contains("bottoms") || name.contains("shorts") || name.contains("pants")
          || name.contains("leggings") || name.contains("skirt") || name.contains("jeans")
          || name.contains("overalls")) {
        bottoms.add(item);

        //tops
      } else if (type.contains("tops") || name.contains("shirt") || name.contains("tank") || name.contains("tee")
          || name.contains("top")) {
        tops.add(item);

        //dresses
      } else if (type.contains("dress") || name.contains("jumpsuit") || name.contains("robe")) {
        dresses.add(item);
      }
    }
  }

  /**
   * Removed a clothing item from the wardrobe. 
   * 
   * Searches every category list and the laundry hamper. 
   * 
   * @param item the clothing item to remove 
   * @return true if the item was removed successfully
   */
  public boolean removeClothingItem(ClothingItem item) {
    return tops.remove(item) || bottoms.remove(item) || jackets.remove(item) || dresses.remove(item)
        || laundryHamper.remove(item);
  }

  /**
   * Retruns all clothing items stored in the wardrobe, including dirty items 
   * in the hamper. 
   * 
   * @return List containig all clothing items
   */
  public List<ClothingItem> getAllClothingItems() {
    ArrayList<ClothingItem> all = new ArrayList<>();
    all.addAll(tops);
    all.addAll(bottoms);
    all.addAll(jackets);
    all.addAll(dresses);
    all.addAll(laundryHamper);
    return all;
  }

  /**
   * Marks a clothing item as worn by increasing its wear count. 
   * 
   * @param item the clothing item being worn
   */
  public void markItemAsWorn(ClothingItem item) {
    //dirty items cannot be worn
    if (item.isDirty()) {
      System.out.println("Cannot wear this! It's in the laundry hamper :(");

    } else {
      item.markWorn();
    }
  }

  /**
   * Returns the total # of times a clothing item has been worn.
   * 
   * @param item the clothing item
   * @return the wear count
   */
  public int getWearCount(ClothingItem item) {
    return item.getWearCount();
  }

  /**
   * Marks a clothing item as dirty and moves it into the 
   * laundry hamper. 
   * 
   * The item is removed from all clean clothing lists. 
   * 
   * @param item the clothing item to mark dirty
   */
  public void markItemDirty(ClothingItem item) {
    tops.remove(item);
    bottoms.remove(item);
    jackets.remove(item);
    dresses.remove(item);

    item.markDirty();
    if (!laundryHamper.contains(item)) {
      laundryHamper.add(item);
    }
  }

  /**
   * Washes a clothing item adn retruns it to the wardrobe.
   * The wear count resets after washing. 
   * 
   * @param item the clothing item to clean
   */
  public void markItemClean(ClothingItem item) {
    if (laundryHamper.remove(item)) {
      item.markClean();
      item.resetWearCount();
      addClothingItem(item);
    }
  }

  /**
   * Determines whether a clothing item is dirty.
   * 
   * @param item the clothing item
   * @retrun true if item is dirty
   */
  public boolean isItemDirty(ClothingItem item) {
    return item.isDirty();
  }

  // ------------------------------------------------------------------------------------
  // compatability checks

  /**
   * Determines whether the texture of two items ar compatiable.
   * Some heavy and thin fabrics are considered incompatiable.
   * 
   * @param item1 the first clothing item
   * @param item2 the second clothing item
   * @return true if compatiable
   */
  public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2) {
    String t1 = item1.getTexture().toLowerCase().trim();
    String t2 = item2.getTexture().toLowerCase().trim();

    // Fashion nono's (avoid double denim)
    if (t1.contains("denim") && t2.contains("denim")) {
      return false;
    }

    // heavy fabrics
    boolean isHeavy = t1.contains("leather") || t1.contains("wool") || t1.contains("fleece");

    //lighter fabrics
    boolean isThin = t2.contains("silk") || t2.contains("spandex") || t2.contains("nylon");

    // avoid heavy + thin combinations
    if ((isHeavy && isThin) || (isThin && isHeavy)) {
      return false;
    }

    // safe bets for any combination (cotton, polyester, and linen)
    return true;
  }

  /**
   * Determines whether two colors are compatiable.
   * Compatiability is based on neutal colors, monochrome looks, 
   * patters, and selected complementary colors. 
   * 
   * @param item1 the first clothing item
   * @param item2 the second clothing item 
   * @return true if compatiable
   */
  public boolean isColorCompatible(ClothingItem item1, ClothingItem item2) {
    String c1 = item1.getColor().toLowerCase().trim();
    String c2 = item2.getColor().toLowerCase().trim();

    // dealing with striped, floral, two colors, etc.
    boolean isPattern1 = c1.contains("/") || c1.contains("striped") || c1.contains("floral") || c1.contains("print")
        || c1.contains("checkered") || c1.contains("tie-dye");

    boolean isPattern2 = c2.contains("/") || c2.contains("striped") || c2.contains("floral") || c2.contains("print")
        || c2.contains("checkered") || c2.contains("tie-dye");

    // if one is a pattern, match with a neutral
    String neutrals = "black white gray grey navy cream beige ivory tan denim charcoal";

    // avoid mixing patterns
    if (isPattern1 && isPattern2) {
      return false;

      // pattern + neutral
    } else if (isPattern1 && neutrals.contains(c2)) {
      return true;

    } else if (isPattern2 && neutrals.contains(c1)) {
      return true;
    }

    // monochrome look 
    if (c1.equals(c2)) {
      return true;
    }

    // neutrals match with everything
    if (neutrals.contains(c1) || neutrals.contains(c2)) {
      return true;
    }

    // earth tones
    // also just other colors that can go together
    String earthTones = "olive burgundy rust brown forest green maroon mustard sage";

    if (earthTones.contains(c1) && earthTones.contains(c2)) {
      return true;

    } else if (c1.contains("blue") && c2.contains("green") || c1.contains("green") && c2.contains("blue")) {
      return true;
      
    } else if (c1.contains("brown") && c2.contains("burgundy") || c1.contains("burgundy") && c2.contains("brown")) {
      return true;

    } else if (c1.contains("pink") && c2.contains("purple") || c1.contains("purple") && c2.contains("pink")) {
      return true;

    }
    return false;
  }

  // implements getMostWord and getLeastWorn for each clothing category

  /**
   * Find the most worn top
   * 
   * @return the most worn top or null if no tops exist
   */
  public ClothingItem getMostWornTop() {
    if (tops == null || tops.isEmpty()) {
      return null;
    }

    ClothingItem mostWorn = tops.get(0);

    for (ClothingItem item : tops) {

      if (item.getWearCount() > mostWorn.getWearCount()) {
        mostWorn = item;
      }
    }
    return mostWorn;
  }

  /**
   * Find the least worn top.
   * 
   * @return the least worn top or null is no top exist
   */
  public ClothingItem getLeastWornTop() {
    if (tops == null || tops.isEmpty()) {
      return null;
    }

    ClothingItem leastWorn = tops.get(0);

    for (ClothingItem item : tops) {

      if (item.getWearCount() < leastWorn.getWearCount()) {
        leastWorn = item;
      }
    }
    return leastWorn;
  }

  /**
   * Find the most worn bottom. 
   * 
   * @return the most worn bottom, or null if there are no bottoms
   */
  public ClothingItem getMostWornBottom() {
    if (bottoms == null || bottoms.isEmpty()) {
      return null;
    }

    ClothingItem mostWorn = bottoms.get(0);

    for (ClothingItem item : bottoms) {

      if (item.getWearCount() > mostWorn.getWearCount()) {
        mostWorn = item;
      }
    }
    return mostWorn;
  }

 /**
  * Find the least worn bottom. 
  * 
  * @return the least worn bottom, or null if there are no bottoms
  */
  public ClothingItem getLeastWornBottom() {
    if (bottoms == null || bottoms.isEmpty()) {
      return null;
    }

    ClothingItem leastWorn = bottoms.get(0);
    for (ClothingItem item : bottoms) {
      if (item.getWearCount() < leastWorn.getWearCount()) {
        leastWorn = item;
      }
    }
    return leastWorn;
  }

  /**
   * Finds and returns the most worn jacket. 
   * 
   * @return the most worn jacket, or null if there are no jackets
   */
  public ClothingItem getMostWornJacket() {
    if (jackets == null || jackets.isEmpty()) {
      return null;
    }

    ClothingItem mostWorn = jackets.get(0);

    for (ClothingItem item : jackets) {

      if (item.getWearCount() > mostWorn.getWearCount()) {
        mostWorn = item;
      }
    }
    return mostWorn;
  }

  /**
   * Find the least worn jacket.
   * 
   * @return the least worn jacket, or null if there are no jackets
   */
  public ClothingItem getLeastWornJacket() {
    if (jackets == null || jackets.isEmpty()) {
      return null;
    }

    ClothingItem leastWorn = jackets.get(0);

    for (ClothingItem item : jackets) {

      if (item.getWearCount() < leastWorn.getWearCount()) {
        leastWorn = item;
      }
    }
    return leastWorn;
  }

  /**
   * Find the most worn dress. 
   * 
   * @return the most worn dress, or null if there are no dresses
   */
  public ClothingItem getMostWornDress() {
    if (dresses == null || dresses.isEmpty()) {
      return null;
    }

    ClothingItem mostWorn = dresses.get(0);

    for (ClothingItem item : dresses) {

      if (item.getWearCount() > mostWorn.getWearCount()) {
        mostWorn = item;
      }
    }
    return mostWorn;
  }

  /**
   * Find the least worn dress.
   * 
   * @return the least worn dress, or null if there are no dresses
   */
  public ClothingItem getLeastWornDress() {
    if (dresses == null || dresses.isEmpty()) {
      return null;
    }

    ClothingItem leastWorn = dresses.get(0);

    for (ClothingItem item : dresses) {
      
      if (item.getWearCount() < leastWorn.getWearCount()) {
        leastWorn = item;
      }
    }
    return leastWorn;
  }
}
