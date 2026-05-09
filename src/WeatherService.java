import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {
    public static String getCurrentTemperature() {
        try {
            // creating HttpClient
            HttpClient client = HttpClient.newHttpClient();
            // defining the request (Open-Metro API)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://api.open-meteo.com/v1/forecast?latitude=34.06&longitude=-117.75&current=temperature_2m&temperature_unit=fahrenheit"))
                    .build();

            // send request (java treats it as a String)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // covert to JSON object
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            // look to find temperature
            double temp = json.getAsJsonObject("current").get("temperature_2m").getAsDouble();

            // printing current temperature
            System.out.println("Current Temperature: " + temp + "°F");

            // these can be changed
            if (temp < 55)
                return "cold";
            if (temp < 75)
                return "warm";
            return "hot";

        } catch (Exception e) {
            System.out.println("Error fetching weather: " + e.getMessage() + ". Defaulting to 'warm'.");
            return "warm";
        }
    }
}
