package Weather;

import Location.City;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather {
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String description;
    private City city;
    private String icon;
    protected String weatherData;
    protected Map<String, Object> respMap;
    protected Map<String, Object> mainMap;
    protected Map<String, Object> windMap;

    //REQUIRES:
    //EFFECTS: Gets all the weather data of the specified city and returns it as a string
    private String getWeather() {
        String API_KEY = "78eb115e31ea9c537773380d4e7c43d4";
        //String LOCATION = cityName + ",CA";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?id=" + city.getId() + "&appid=" + API_KEY + "&units=metric";
        try{
            StringBuilder result = new StringBuilder();
            URL url  = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            //System.out.println(result);
            rd.close();
            return result.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> jsonToMap (String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }

    public Weather(City c) {
        this.city = c;
        this.weatherData = getWeather();
        respMap = jsonToMap(weatherData);
        mainMap = jsonToMap(respMap.get("main").toString());
        windMap = jsonToMap(respMap.get("wind").toString());
        this.humidity = setHumidity();
        this.windSpeed = setWindSpeed();
        this.description = setDescription();
        this.temperature = setTemp();
        this.icon = setIcon();
    }
    private String setDescription() {
        JsonElement jelement = new JsonParser().parse(weatherData);
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("weather");
        jobject = jarray.get(0).getAsJsonObject();
        jelement = jobject.get("description");
        return jelement.toString().replace("\"", "");
    }
    private String setIcon() {
        JsonElement jelement = new JsonParser().parse(weatherData);
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray("weather");
        jobject = jarray.get(0).getAsJsonObject();
        jelement = jobject.get("icon");
        return jelement.toString().replace("\"", "");
    }
    //EFFECTS: returns humidity as double
    private String setHumidity() {
        return mainMap.get("humidity").toString();
    }
    //EFFECTS: returns wind speeds as double
    private String setWindSpeed() {
        return windMap.get("speed").toString();
    }
    //EFFECTS: returns temperature as double
    private String setTemp() {
        return mainMap.get("temp").toString();
    }

    public String getDescription() {return this.description;}
    public String getHumidity() {return this.humidity;}
    public String getWindSpeed() {return this.windSpeed;}
    public String getTemperature() {return this.temperature;}
    public String getIcon() {return icon;}

    public List<String> getOrganizedWeatherReport() {
        List<String> weatherData = new ArrayList<>();
        weatherData.add("Temperature: " + this.getTemperature() + " °C");
        weatherData.add("Humidity: " + this.getHumidity() + " %");
        weatherData.add("Wind Speed: " + this.getWindSpeed() + " m/s");
        weatherData.add("Description: " + this.getDescription());
        return weatherData;
    }
}
