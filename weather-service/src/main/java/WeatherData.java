package league-tdd-workshop-java.weather-service.src.main;
import java.util.Objects;

public class WeatherData {
    private Double temperature;
    private Double humidity;

    public WeatherData(Double temperature, Double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherData that = (WeatherData) o;
        return Objects.equals(temperature, that.temperature) && Objects.equals(humidity, that.humidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, humidity);
    }
}
