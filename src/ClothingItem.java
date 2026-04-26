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

    public ClothingItem(String name, String category, String color, String texture, String style) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.texture = texture;
        this.style = style;
    }
}
