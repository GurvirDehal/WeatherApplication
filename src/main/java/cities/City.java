package cities;

import Weather.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public abstract class City {
    protected String name;
    protected String id;
    protected Weather weather;

    public static Map<String, Object> jsonToMap (String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }

    //REQUIRES: Name for the city
    //MODIFIES: this
    //EFFECTS: Constructs a city object
    public City(String cityName, String id) {
        this.name = cityName;
        this.id = id;
        this.weather = new Weather(this);
    }

    public City(City c) {
        this.name = c.name;
        this.id = c.id;
        this.weather = c.weather;
    }

    //EFFECTS: prints out weather for city.
    public void printData() {
        System.out.println("Now Showing Weather Information for: " + this.name);
        System.out.println("Temperature: " + weather.getTemperature() + " °C");
        System.out.println("Humidity: " + weather.getHumidity() + " %");
        System.out.println("Wind Speed: " + weather.getWindSpeed() + " m/s");
        System.out.println("Description: " + weather.getDescription());
        System.out.println();
    }



    public Weather getWeather() {
        return weather;
    }
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
