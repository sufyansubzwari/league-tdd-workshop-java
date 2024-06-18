package league-tdd-workshop-java.weather-service.src.main.java;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class WeatherService {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private Map<String, CacheEntry> cache = new HashMap<>();
    private static final long CACHE_DURATION = 600_000;  // cache duration in milliseconds (10 minutes)

    private class CacheEntry {
        WeatherData data;
        long timestamp;

        CacheEntry(WeatherData data, long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
    }

    public WeatherData getWeather(String city) {
        long currentTime = System.currentTimeMillis();

        // Check cache first
        if (cache.containsKey(city)) {
            CacheEntry entry = cache.get(city);
            if (currentTime - entry.timestamp < CACHE_DURATION) {
                return entry.data;
            }
        }

        // Fetch new data
        try {
            URL url = new URL(API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            if (conn.getResponseCode() != 200) {
                return null;
            }

            Scanner scanner = new Scanner(url.openStream());
            String inline = "";
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();

            JSONObject json = new JSONObject(inline);
            JSONObject main = json.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double humidity = main.getDouble("humidity");

            WeatherData weatherData = new WeatherData(temperature, humidity);

            // Update cache
            cache.put(city, new CacheEntry(weatherData, currentTime));

            return weatherData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
