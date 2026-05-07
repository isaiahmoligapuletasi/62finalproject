import java.util.ArrayList;
import java.util.List;

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

  public Wardrobe() {
    this.tops = new ArrayList<>();
    this.bottoms = new ArrayList<>();
    this.jackets = new ArrayList<>();
    this.dresses = new ArrayList<>();
    this.laundryHamper = new ArrayList<>();
  }

  // ------------------------------------------------------------------------------------
  // getters

  public ArrayList<ClothingItem> getTops() {
    return new ArrayList<>(tops);
  }

  public ArrayList<ClothingItem> getBottoms() {
    return new ArrayList<>(bottoms);
  }

  public ArrayList<ClothingItem> getJackets() {
    return new ArrayList<>(jackets);
  }

  public ArrayList<ClothingItem> getDresses() {
    return new ArrayList<>(dresses);
  }

  public ArrayList<ClothingItem> getLaundryHamper() {
    return new ArrayList<>(laundryHamper);
  }

  // ------------------------------------------------------------------------------------
  // ClothingSelecterInterface methods
  // adds item to correct list based on category
  public void addClothingItem(ClothingItem item) {
    if (item.isDirty()) {
      laundryHamper.add(item);
    } else {
      String category = item.getCategory().toLowerCase();

      String type = item.getCategory().toLowerCase();
      String name = item.getName().toLowerCase();

      // changed .equals() to .contains() in case file has extra spaces/capitalization
      if (type.contains("jacket") || name.contains("puffer") || name.contains("vest") 
          || name.contains("coat") || name.contains("hoodie") || name.contains("sweatshirt")) {
          jackets.add(item);

      } else if (type.contains("bottoms") || name.contains("shorts") || name.contains("pants")
          || name.contains("leggings") || name.contains("skirt") || name.contains("jeans")) {
        bottoms.add(item);
      } else if (type.contains("tops") || name.contains("shirt") || name.contains("tank") || name.contains("tee")
          || name.contains("top")) {
            tops.add(item);
        // are overalls pants or a dress? - we would want a shirt so i assume pants but
        // ive left it here for now
      } else if (type.contains("dress") || name.contains("jumpsuit") || name.contains("overalls")
          || name.contains("robe")) {
        dresses.add(item);
      }
    }
  }

  // removes item from respective list
  public boolean removeClothingItem(ClothingItem item) {
    return tops.remove(item) || bottoms.remove(item) || jackets.remove(item) || dresses.remove(item)
        || laundryHamper.remove(item);
  }

  public List<ClothingItem> getAllClothingItems() {
    ArrayList<ClothingItem> all = new ArrayList<>();
    all.addAll(tops);
    all.addAll(bottoms);
    all.addAll(jackets);
    all.addAll(dresses);
    all.addAll(laundryHamper);
    return all;
  }

  // mark item as worn (by incrementing wearCount)
  public void markItemAsWorn(ClothingItem item) {
    if (item.isDirty()) {
      System.out.println("Cannot wear this! It's in the laundry hamper :(");
    } else {
      item.markWorn();
    }
  }

  public int getWearCount(ClothingItem item) {
    return item.getWearCount();
  }

  // removes item from wardrobe and to laundry hamper
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

  // removes an item from the laundry hamper to the wardrobe
  public void markItemClean(ClothingItem item) {
    if (laundryHamper.remove(item)) {
      item.markClean();
      item.resetWearCount();
      addClothingItem(item);
    }
  }

  public boolean isItemDirty(ClothingItem item) {
    return item.isDirty();
  }

  // ------------------------------------------------------------------------------------
  // compatability checks

  public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2) {
    String t1 = item1.getTexture().toLowerCase().trim();
    String t2 = item2.getTexture().toLowerCase().trim();

    // I just searched up fashion nono's
    if (t1.contains("denim") && t2.contains("denim")) {
      return false;
    }

    // we can change these
    boolean isHeavy = t1.contains("leather") || t1.contains("wool") || t1.contains("fleece");
    boolean isThin = t2.contains("silk") || t2.contains("spandex") || t2.contains("nylon");

    if ((isHeavy && isThin) || (isThin && isHeavy)) {
      return false;
    }

    // safe bets for any combination (cotton, polyester, and linen)
    return true;
  }

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

    // not mixing patterns
    if (isPattern1 && isPattern2) {
      return false;
      // pattern + neutral
    } else if (isPattern1 && neutrals.contains(c2)) {
      return true;
    } else if (isPattern2 && neutrals.contains(c1)) {
      return true;
    }

    // monochrome look - do we want true or false?
    if (c1.equals(c2)) {
      return true;
    }

    // neutrals match with everything
    if (neutrals.contains(c1) || neutrals.contains(c2)) {
      return true;
    }

    // earth tones? -idk i just asked claude what color groups we had
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

  // ------------------------------------------------------------------------------------
  // implemented getMostWord and getLeastWorn for each clothing category

  // finds most worn top (we can edit this to find multiple most worn items)
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

  // finds least worn top (we can also edit this one to fine multiple)
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

  // finds least worn top (we can also edit this one to fine multiple)
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

  // finds least worn top (we can also edit this one to fine multiple)
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

  // finds least worn top (we can also edit this one to fine multiple)
  public ClothingItem getLeastWornDress() {
    if (dresses == null || dresses.isEmpty()) {
      return null;
    }
    return result;
  }
}
  
  
      
