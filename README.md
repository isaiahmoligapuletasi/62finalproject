# CS62 Final Project: Outfit Generator
Created by: Isaiah Moliga-Puletasi, Joselyn Quinteros, and Lewhat Kahsay 

## Description:
Ever rolled out of bed not sure what to wear? Utilizing user and weather preferences, Outfit Generator combines comparisons and user input to find your next **best** outfit!

The system organizes clothing items, tracks laundry status, and ranks outfit combinations using a scoring algorithm that evaluates color, texture, style, and weather suitability. Our project utilizes file parsing, algorithmic scoring, and real-time API integration.

## Features:
1. Outfit recommendation based on texture, color, style, and weather
2. Laundry hamper that keeps track of dirty clothing items 
3. Least-worn and most-worn counts to identify least/most worn pieces
4. Live weather integration using an external weather API 

## For Users: How to Run the Program
1. Open the project in VSCode.
2. Install Required Library (Gson)
This project uses the Gson library for JSON parsing in the weather service.
Download the library here:
Central Repository: com/google/code/gson/gson/2.10.1 
Download the file: 
Gson-2.10.1.jar
Then add it to your project by navigating to Referenced Libraries and adding the downloaded .jar file. 

## Classes:

### ClothingItem
ClothingItem creates a single clothing item (Java object) that is stored within the `Wardrobe`. All `ClothingItem` objects store the following information: `name (String)`, `category (String)`, `texture (String)`, `style (String)`, `temperature (String)`, `wearCount (int)`, and `isDirty (boolean)`. 

Each `ClothingItem` information is stored within private instance variables within the class and instantiated by the `ClothingItem` constructor.

`wearCount` is always set = 0 & `isDirty` = false, inferring that a user has never worn a `ClothingItem` and that it is not dirty upon instantiating it for the first time. 

The ClothingItem class consists of getter/setter methods, `get[Clothing Information Type]`, for: temperature, name, category, color, texture, style, and wearCount. 

Additionally, the class consists of various other methods as well. `isDirty` checks if a clothing item is dirty and returns true if a clothing item is marked as dirty, false otherwise. `markWorn` increments the `wearCount` of a clothing item by 1 (demarcating that a clothing item has been worn once). `markClean` "cleans” a piece of clothing by setting isDirty = false. `resetWearCount` sets the `wearCount` =  0 (which coincides with its dirtiness: for our project purposes, we say a clothing item that’s no longer dirty has not been worn, and thus we reset it’s wear count). 

(* All methods in `ClothingItem` require no user input, aside from the constructor).

### DataParser2
`DataParser2` parses clothing data from an inputted CSV file, transmitting data row-per-row into a clothingItem object. After a `clothingItem` object has been created, `DataParser2` loads clothing items into a `Wardrobe` object. 

`loadWardrobe(String filePath)` takes a `filePath` in the form of a CSV file and converts each row into a ClothingItem object. ClothingItem objects are then loaded into the `Wardrobe` utilizing the addClothingItem method (formulated in `Wardrobe`). 

### OutfitSelectionInterface
`OutfitSelectionInterface` is the core functionality of outfit generator. Methods prescribed in the interface support clothing storage, outfit generation, wear tracking, and compatibility checks.

Methods: 
`public void addClothingItem(ClothingItem item);`
`public boolean removeClothingItem(ClothingItem item);`
`public List<ClothingItem> getAllClothingItems();`
`public void markItemAsWorn(ClothingItem item);`
`public int getWearCount(ClothingItem item);`
`public void markItemDirty(ClothingItem item);`
`public void markItemClean(ClothingItem item);`
`public boolean isItemDirty(ClothingItem item);`
`public boolean isTextureCompatible(ClothingItem item1, ClothingItem item2);`
`public boolean isColorCompatible(ClothingItem item1, ClothingItem item2);`

### UserInput
`UserInput` is the key class for outfit generation: this class is responsible for handling all user interactions through a console main menu system. The main menu system includes: 

**Generating outfit suggestions**

Outfit generating is done combining user preference and a score (which increments based on a user’s preferences). Specific outfits options are created through an `OutfitOption` class embedded in the `UserInput` class). `OutfitOption(ClothingItem i1, ClothingItem i2, ClothingItem jacket, int s)` combines all `ClothingItem` in assembling an outfit, alongside a `score` for that outfit (what will eventually be used to select the best fit outfit for a user). 

**Viewing the laundry hamper**

Fetches the laundry hamper array list and displays the clothing that has been added to it (denoted when a user marks a clothing item as marked as dirty), `displayHamper(Wardrobe w)`. 

**Marking clothing items as dirty**

Users prompt the clothing item they would like to be marked as dirty and are then moved into the laundry hamper, `completeMarkDirty (Wardrobe w, Scanner reader)`. Searching for clothes is done through iterating through an array list of all clothing items, when multiple matches occur, it will prompt options in a numerical order with clothing items in tandem, and ask if the user identifies any matches. 

**Washing clothing items/marking items as clean**

Users prompt the clothing item they would like to be washed/marked as clean. The `completeItemWash(Wardrobe w, Scanner reader)` iterates through the laundry hamper to find the name of the inputted clothing item, washes it, and removes it from the laundry hamper. If 

**Displaying clothing wear statistics**

Utilizing getMostWorn methods from the `Wardrobe` class, the outfit generator will display, `displayStats(Wardrobe w)` the top least/most worn clothing items: top, bottom, jacket, and dress for the unique user. 

**Specialized Main Menu + Temperature/Weather Information**

Upon prompting the outfit generator, the main menu takes a user’s given name and customizes that main menu to the user. Additionally, the outfit generator will pull temperature from the Claremont/Pomona area.  

**Outfit Comparison**

As mentioned, outfits are determined through user feedback. More specifically, final outfit generation is created through assigning a score to clothing items throughout the user input process (and by comparing multiple outfit options together). 

Outfits consist of a: top, bottom, and jacket (optional), or a dress. 

Ranking preference compatibility based on `ClothingItem` information: 
1. Weather (highest priority): add 20 points to score 
2. Style: add 10 points to score
3. Color & Texture: add 5 points to score
If a user chooses “any” color, there is no criteria for scoring an outfit match based on on user color preference 

### Wardrobe
`Wardrobe` acts as the `ClothingItem` storage for a user implementing all of the `OutfitSelectorInterface`. `Wardrobe` provides: functionality for clothing management, compatibility checks, and clothing wear stats. 

**Getter Methods**
`Wardrobe` provides getter methods for jackets, dresses, the laundry hamper, all clothing items, and wear count: `getTops()`, `getBottoms()`, `getJackets()`, `getDresses()`, `getLaundryHamper()`, `getAllClothingItems()` and `getWearCount(ClothingItem item)`. For tops, bottoms, jackets, and dresses, getter methods return an array list of all clean items. `getLaundryHamper()` returns an array list of all items currently in the laundry hamper. `getWearCount(ClothingItem item)` returns a given `ClothingItem` current `wearCount`. `getAllClothingItems()` returns all clothing items in a user’s `Wardrobe`, including clean and dirty. 

**Add/Remove ClothingItem**

`Wardrobe` provides the ability to add and remove a clothing item from the `Wardrobe`: `addClothingItem(ClothingItem item)` and `removeClothingItem(ClothingItem item)`. 

Users can only add clean clothing items to the `Wardrobe` of wearable clothing items, all dirty clothes are segmented into the laundry hamper. 

**Laundry Hamper Features (Clean/Dirty clothes)**

`Wardrobe` provides the ability to mark items as worn, dirty, clean, or check if an item is dirty: `markItemAsWorn(ClothingItem item)`, `markItemDirty(ClothingItem item)`, `markItemClean(ClothingItem item)`, `markItemClean(ClothingItem item)`, `isItemDirty(ClothingItem item)`. 

Marking clothing items as dirty will send them to the laundry hamper, marking them as clean will take them out the laundry hamper. 

Marking an item as worn or dirty is done through changing boolean statements, setting items to true if they are worn or dirty, and false otherwise. 

**Compatibility Checks**

In order to compare if clothing is compatible, we measured it based on texture and color metrics. 

For texture we looked at: denim and denim (we say this is a fashion no no, so no outfit will contain two denim textured pieces), heavy fabric and light fabrics (we avoid combining heavy and light fabrics — fabrics being synonymous with texture here). 

For color we looked at matching patterns with only neutral colors, as well as matching earth tones together. Neutral colors match everything. We say earth tones go well with each other (other earth tones). 

**Patterns included in our dataset include:**
Striped, floral, print, checkered, tie-dye

**Neutral colors included in our dataset include:**
Black, white, gray, grey, navy, cream, beige, ivory, tan, denim, charcoal 

**Earth tones include:**
Olive, burgundy, rust, brown, forest, green, maroon, mustard, sage

(Patterns and neutral colors are both classified as colors in our dataset)

Comparisons are done through simple if, and, or statements which check true/false statements (if items have compatible texture/colors). 

**getMostWorn**

To acquire statistics on least/most worn clothing items, we utilized getter methods that compare wear count against clothing items in a user’s closet (choosing both the highest and lowest worn items through the wearCount variable). Methods include: `getMostWornTop()`, `getLeastWornTop()`, `getMostWornBottom()`, `getLeastWornBottom()`, `getMostWornJacket()`, `getLeastWornJacket()`, `getMostWornDress()`, `getLeastWornDress()`. None of the methods require any variable input. 

### WeatherService 
WeatherService retrieves current weather data from an external API provided through Open-Meteo. 

`getCurrentTemperature()` sends a request to the external weather API, identifying the temperature in the Claremont/Pomona area specifically and categorizing temperatures as: cold, warm, or hot. 

(At the moment the weatherAPI only gathers temperature based on Claremont/Pomona, temperatures are not pulled from wherever a user is locally)

Temperatures are utilized in generating weather-friendly outfits as well as utilized in the main menu for the outfit generator in `userInput`. 


### Credits: 

Many thanks to: Professor Clark for helping us create the data parser foundation, Open-Meteo for their weather API, and Claude for assisting in data generation for the dataset.





