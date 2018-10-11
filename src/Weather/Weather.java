package Weather;

import cities.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class Weather {
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String windAngle;
    protected String weatherData;

    protected Map<String, Object> respMap;
    protected Map<String, Object> mainMap;
    protected Map<String, Object> windMap;

    //REQUIRES:
    //EFFECTS: Gets all the weather data of the specified city and returns it as a string
    private String getWeather(String cityName) {
        String API_KEY = "78eb115e31ea9c537773380d4e7c43d4";
        String LOCATION = cityName + ",CA";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + API_KEY + "&units=metric";
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
        this.weatherData = getWeather(c.name);
        respMap = jsonToMap(weatherData);
        mainMap = jsonToMap(respMap.get("main").toString());
        windMap = jsonToMap(respMap.get("wind").toString());
        this.humidity = setHumidity();
        this.windSpeed = setWindSpeed();
        this.windAngle = setWindAngle();
        this.temperature = setTemp();
    }
    private String setWindAngle() {
        return windMap.get("deg").toString();
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

    public String getWindAngle() {return this.windAngle;}
    public String getHumidity() {return this.humidity;}
    public String getWindSpeed() {return this.windSpeed;}
    public String getTemperature() {return this.temperature;}
}
