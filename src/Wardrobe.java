import java.util.ArrayList;
import java.util.List;

public class Wardrobe {

  //clean tops
  private ArrayList<ClothingItem> tops;
  //clean bottoms
  private ArrayList<ClothingItem> bottoms;

  private ArrayList<ClothingItem> laundryHamper;

  private static final int maxWearsBeforeDirty = 3;

  //empty wardrobe - might not need these but we'll see after we build the parser
  //we could have the parser class automatically load this wardrobe 
  public Wardrobe() {
    tops = new ArrayList<>();
    bottoms = new ArrayList<>();
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

  //all items in laundry hamper
  public List<ClothingItem> getLaundryHamper() {
    return new ArrayList<>(laundryHamper);
  }

  //also is already in clothingItem
  public int getWearCount(ClothingItem item) {
    return item.wearCount();
  }

  public void markItemAsWorn(ClothingItem item) {
    item.markWorn();
    item.wearCount += 1;
    if (item.wearCount() >= maxWearsBeforeDirty) {
      sendToHamper(item);
    }
  }

  public void markItemDirty(ClothingItem item) {
    item.isDirty = true;
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
    item.markItemDirty();
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

  // remaining 
  public List<ClothingItem> getLeastWornTops() {
    // linear runtime, copying ArrayList over  
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
}
  
  
      
