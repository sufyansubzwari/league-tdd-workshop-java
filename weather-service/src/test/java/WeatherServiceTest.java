package league-tdd-workshop-java.weather-service.src.test.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WeatherServiceTest {

    @Test
    public void testGetWeather() {
        WeatherService service = new WeatherService();
        WeatherData data = service.getWeather("New York");
        assertNotNull(data);
        assertNotNull(data.getTemperature());
        assertNotNull(data.getHumidity());
    }

    @Test
    public void testGetWeatherCache() {
    }

    @Test
    public void testHandleApiError() {
    }
}
