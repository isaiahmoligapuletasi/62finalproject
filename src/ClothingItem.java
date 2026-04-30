public class ClothingItem {
    //Label of the item like "Long Sleeve"
    private String name;

    //Labels like "Top" or "Bottom"
    private String category; 

    private String color;
    //Labels like "Cotton", "Denim", etc
    private String texture;

    //Labels like "Casual", "Athletic wear"
    private String style;

    //Tracking features
    int wearCount;
    boolean isDirty;

    /**
     * Constructs a ClothingItem with its needed attributes.
     * Marks our clothing items as clean as the default and 
     * wear count to 0
     * @param name 
     * @param category
     * @param color
     * @param texture
     * @param style
     */
    public ClothingItem(String name, String category, String color, String texture, String style) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.texture = texture;
        this.style = style;

        this.wearCount = 0;
        this.isDirty = false;
    }

    //Getter methods

    /**
     * Returns the name of the clothing item
     * 
     * @return name of clothing item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the category of the item
     * @return category of item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the color of the item
     * @return color of item
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the texture of the item
     * @return texture of item
     */
    public String getTexture() {
        return texture;
    }

    /**
     * Returns the style of the item
     * @return style of item
     */
    public String getStyle() {
        return style;
    }

    /**
     * Returns the wear count of the item
     * @return wear count of item
     */
    public int getWearCount() {
        return wearCount;
    }

    /**
     * Returns a boolean. True if the item is dirty and 
     * false otherwise.
     * @return false or true based on if ths item is dirty
     */
    public boolean isDirty() {
        return isDirty;
    }

    //State update methods

    /**
     * Marks the item as worn once and increments wear count
     */
    public void markWorn(){
        wearCount++;
    }

    /**
     * Marks the item as dirty
     */
    public void markDirty() {
        isDirty = true;
    }

    /**
     * Marks the item as clean which makes it avaliable to wearagain
     */
    public void markClean() {
        isDirty = false;
    }

    public void resetWearCount() {
        wearCount = 0;
    }
}
