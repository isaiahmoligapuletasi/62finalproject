import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * WeatherService retrieves current weather data from an external API 
 * and converts it into a temperature category for the outfit selector.
 * This class maps the real-time temperature data and maps it to cold, 
 * warm, or hot. 
 * 
 * These actegories are used to help generate weather appropriate outfits.
 * 
 * @author Lewhat Kahsay
 * @author Joselyn Quinteros
 * @author Isaiah Moliga-Puletasi
 */
public class WeatherService {

    /**
     * Gets the current temperature from the Open-Meteo API and 
     * converts it to a weather category. If the API call fails, 
     * the method defaults to "warm". 
     * 
     * @return a weather category String ("cold", "warm", or "hot")
     */
    public static String getCurrentTemperature() {
        try {
            // creating HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // building the request to Open-Metro API (Claremont/Pomona area coordinates)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.open-meteo.com/v1/forecast?latitude=34.06&longitude=-117.75&current=temperature_2m&temperature_unit=fahrenheit"))
                    .build();

            // send request (java treats it as a String)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // parse JSON response
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // Get temperature from JSON
            double temp = json.getAsJsonObject("current").get("temperature_2m").getAsDouble();

            // printing current temperature (for debugging)
            System.out.println("Current Temperature: " + temp + "°F");

            // Convert numeric temp into category
            if (temp < 55)
                return "cold";
            if (temp < 75)
                return "warm";
            return "hot";

        } catch (Exception e) {

            //If API fails (assume warm)
            System.out.println("Error fetching weather: " + e.getMessage() + ". Defaulting to 'warm'.");
            return "warm";
        }
    }
}
