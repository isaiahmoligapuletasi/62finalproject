import java.util.ArrayList;
import java.util.List;

public class Wardrobe implements OutfitSelectorInterface {

  //clean tops
  private ArrayList<ClothingItem> tops;
  //clean bottoms
  private ArrayList<ClothingItem> bottoms;
  //clean jackets
  private ArrayList<ClothingItem> jackets;
  //clean dresses
  private ArrayList<ClothingItem> dresses;
  
  //dirty clothes
  private ArrayList<ClothingItem> laundryHamper;

  
  public Wardrobe() {
    this.tops = new ArrayList<>();
    this.bottoms = new ArrayList<>();
    this.jackets = new ArrayList<>();
    this.dresses = new ArrayList<>();
    this.laundryHamper = new ArrayList<>();
  }

//------------------------------------------------------------------------------------
  // getters
  
  public List<ClothingItem> getTops() {
    return new ArrayList<>(tops);
  }
  
  public List<ClothingItem> getBottoms() {
    return new ArrayList<>(bottoms);
  }
  
  public List<ClothingItem> getJackets() {
    return new ArrayList<>(jackets);
  }
  
  public List<ClothingItem> getDresses() {
    return new ArrayList<>(dresses);
  }
  
  public List<ClothingItem> getLaundryHamper() {
    return new ArrayList<>(laundryHamper);
  }

//------------------------------------------------------------------------------------
  //ClothingSelecterInterface methods

  // adds item to correct list based on category
  public void addClothingItem(ClothingItem item) {
    if (item.isDirty()) {
      laundryHamper.add(item);
    } else {
      String category = item.getCategory().toLowerCase();

      if (category.equals("top")) {
        tops.add(item);
      } else if (category.equals("bottom")) {
        bottoms.add(item);
      } else if (category.equals("jacket")) {
        jackets.add(item);
      } else if (category.equals("dress")) {
        dresses.add(item);
      }
    }
  }

  // removes item from respective list
  public boolean removeClothingItem(ClothingItem item) {
    return tops.remove(item) || bottoms.remove(item) || jackets.remove(item) || dresses.remove(item) || laundryHamper.remove(item);
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

//------------------------------------------------------------------------------------
  //compatability checks
  
  public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2) {
    if (item1.getTexture().toLowerCase().equals(item2.getTexture().toLowerCase())) {
     // do we want clothing items to be the same texture?
      // next meeting, we should come up with all the cases for compatibility
      return true;
    } else {
      return false;
    }
  }
    
  
  public boolean isColorCompatible(ClothingItem item1, ClothingItem item2) {
    if (item1.getColor().toLowerCase().equals(item2.getColor().toLowerCase()) {
      // if colors are the same, thats a match (monochrome look)
      return true;
    } else {
      // if they are different colors, we have to decide if they are okay
      // for right now, we'll just say colors don't match unless they are the same
      return false;
    }
  }

//------------------------------------------------------------------------------------
  //implemented getMostWord and getLeastWorn for each clothing category

  // finds most worn top (we can edit this to find multiple most worn items)
  public ClothingItem getMostWornTop() {
    if (tops.isEmpty()) {
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
    if (tops.isEmpty()) {
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
    if (bottoms.isEmpty()) {
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
    if (bottoms.isEmpty()) {
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
    if (jackets.isEmpty()) {
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
    if (jackets.isEmpty()) {
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
    if (dresses.isEmpty()) {
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
    if (dresses.isEmpty()) {
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
 

 


  
  
      
