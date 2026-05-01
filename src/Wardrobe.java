import java.util.ArrayList;
import java.util.List;

public class Wardrobe implements OutfitSelectorInterface {

  //clean tops
  private ArrayList<ClothingItem> tops;
  //clean bottoms
  private ArrayList<ClothingItem> bottoms;

  //Some of our items are labeled as jackets in the data so should we have that seperately?
 // private ArrayList<ClothingItem> jackets;

  private ArrayList<ClothingItem> laundryHamper;

  // We are no longer doing automatic removal (recommendation from Prof Li) - Joselyn
  //private static final int maxWearsBeforeDirty = 3;

  //empty wardrobe - might not need these but we'll see after we build the parser
  //we could have the parser class automatically load this wardrobe 
  public Wardrobe() {
    tops = new ArrayList<>();
    bottoms = new ArrayList<>();
    // jackets = new ArrayList<>();
    laundryHamper = new ArrayList<>();
  }

  //i know we have this in clothingItem but i haven't extended any classes
  //but we should discuss exactly where each method is going before building more
  public List<ClothingItem> getTops() {
    return new ArrayList<>(tops);
  }

  public List<ClothingItem> getBottoms() {
    return new ArrayList<>(bottoms);
  }

  //public List<ClothingItem> getJackets() {
  //  return new ArrayList<>(jackets);
 // }

  //all items in laundry hamper
  public List<ClothingItem> getLaundryHamper() {
    return new ArrayList<>(laundryHamper);
  }

  public List<ClothingItem> getAllClothingItems() {
    ArrayList<ClothingItem> allItems = new ArrayList<>();
    allItems.addAll(tops);
    allItems.addAll(bottoms);
    //allItems.addAll(jackets);
    allItems.addAll(laundryHamper);

    return allItems;
  }


  //also is already in clothingItem
  public int getWearCount(ClothingItem item) {
    return item.getWearCount(); //I changed this to the get that exists- J
  }

  public void markItemAsWorn(ClothingItem item) {
    if (item.isDirty()) {
      throw new IllegalStateException("Item is dirty!");
    }
    item.markWorn();
   // if (item.getWearCount() >= maxWearsBeforeDirty) {
    //  sendToHamper(item);
  }

  public void markItemDirty(ClothingItem item) {
    sendToHamper(item);
  }

  //haven't made markClean yet -> will implement after we set which methods are in each class
  public void markItemClean(ClothingItem item) {
    laundryHamper.remove(item);
    item.markClean();
    item.resetWearCount();
    //we need to decide if we are automatically using upper or lowercase
    if (item.getCategory().equals("Top")) {
      tops.add(item);
    } else if (item.getCategory().equals("Bottom")) {
      bottoms.add(item);
    } 
  }

  //same note: already in clothingItem
  public boolean isItemDirty(ClothingItem item) {
    return item.isDirty();
  }

  private void sendToHamper(ClothingItem item) {
    tops.remove(item);
    bottoms.remove(item);
    //jackets.remove(item);

    item.markDirty();
    if (!laundryHamper.contains(item)) {
      laundryHamper.add(item);
    }
  }

  public List<ClothingItem> getMostWornTops() {
    ArrayList<ClothingItem> result = new ArrayList<>();
    ArrayList<ClothingItem> remaining = new ArrayList<>(tops);

    //we haven't discussed how many items we want to return for mostWorn (i just did 3)
    //right now this is traversing all tops for the top 3 mostWorn - do we want to make and use a separate ArrayList?
    for (int i=0; i<3 && !remaining.isEmpty(); i++) {
      ClothingItem mostWorn = remaining.get(0);
      for (ClothingItem item : remaining) {
        if (item.getWearCount() > mostWorn.getWearCount()) {
          mostWorn = item;
        }
      }
      result.add(mostWorn);
      remaining.remove(mostWorn);
    }
    return result;
  }


  public List<ClothingItem> getMostWornBottoms() {
    ArrayList<ClothingItem> result = new ArrayList<>();
    ArrayList<ClothingItem> remaining = new ArrayList<>(bottoms);

    //we haven't discussed how many items we want to return for mostWorn (i just did 3)
    //right now this is traversing all tops for the top 3 mostWorn - do we want to make and use a separate ArrayList?
    for (int i=0; i<3 && !remaining.isEmpty(); i++) {
      ClothingItem mostWorn = remaining.get(0);
      for (ClothingItem item : remaining) {
        if (item.getWearCount() > mostWorn.getWearCount()) {
          mostWorn = item;
        }
      }
      result.add(mostWorn);
      remaining.remove(mostWorn);
    }
    return result;
  }

  //rewrite
  public List<ClothingItem> getLeastWornTops() {
    ArrayList<ClothingItem> result = new ArrayList<>();
    ArrayList<ClothingItem> remaining = new ArrayList<>(tops);

    //we haven't discussed how many items we want to return for mostWorn (i just did 3)
    //right now this is traversing all tops for the top 3 mostWorn - do we want to make and use a separate ArrayList?
    for (int i=0; i<3 && !remaining.isEmpty(); i++) {
      ClothingItem leastWorn = remaining.get(0);
      for (ClothingItem item : remaining) {
        if (item.getWearCount() < leastWorn.getWearCount()) {
          leastWorn = item;
        }
      }
      result.add(leastWorn);
      remaining.remove(leastWorn);
    }
    return result;
  }

    public List<ClothingItem> getLeastWornBottoms() {
    ArrayList<ClothingItem> result = new ArrayList<>();
    ArrayList<ClothingItem> remaining = new ArrayList<>(bottoms);

    //we haven't discussed how many items we want to return for mostWorn (i just did 3)
    //right now this is traversing all tops for the top 3 mostWorn - do we want to make and use a separate ArrayList?
    for (int i=0; i<3 && !remaining.isEmpty(); i++) {
      ClothingItem leastWorn = remaining.get(0);
      for (ClothingItem item : remaining) {
        if (item.getWearCount() < leastWorn.getWearCount()) {
          leastWorn = item;
        }
      }
      result.add(leastWorn);
      remaining.remove(leastWorn);
    }
    return result;
  }

    public void addClothingItem(ClothingItem item) {
      if (item.isDirty()) {
        laundryHamper.add(item);
      } else if (item.getCategory().equalsIgnoreCase("Tops")) {
         tops.add(item);
      } else if (item.getCategory().equalsIgnoreCase("Bottoms")) {
          bottoms.add(item);
      } else {
        throw new IllegalArgumentException("Unknown item: " + item.getCategory());
      }
    }

    /**
     * Attempts to remove the given ClothingItem from the wardrobe.
     * The item may exists within tops, bottom, or laundry hamper. 
     * 
     * @param item the ClothingItem we want removed
     * @return true if item was found and removed from at least one list, otherwise false
     */
    public boolean removeClothingItem(ClothingItem item) {
      return tops.remove(item) || bottoms.remove(item) || laundryHamper.remove(item);
    }

    public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isTextureCompatible'");
    }

    public boolean isColorCompatible(ClothingItem item1, ClothingItem item2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isColorCompatible'");
    }
}
  
  
      
