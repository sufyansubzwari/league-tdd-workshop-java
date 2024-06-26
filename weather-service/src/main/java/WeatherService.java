package league-tdd-workshop-java.weather-service.src.main.java;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class WeatherService {
    private static final String API_KEY = "4523628bd365a0c61f43c9ba0c9cd1ff";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private Map<String, CacheEntry> cache = new HashMap<>();
    private static final long CACHE_DURATION = 600_000;  // cache duration in milliseconds (10 minutes)

    private class CacheEntry {
        WeatherData data;
        long timestamp;

        CacheEntry(WeatherData data, long timestamp) {
        }
    }

    public WeatherData getWeather(String city) {
        long currentTime = System.currentTimeMillis();

        // Check cache first
        }

        // Fetch new data
        try {
            URL url = new URL(API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=imperial");
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
