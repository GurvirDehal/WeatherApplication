package cities;

import Weather.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public abstract class City {
    public String name;
    public Weather weather;

    public Map<String, Object> jsonToMap (String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        return map;
    }

    //REQUIRES: Name for the city
    //MODIFIES: this
    //EFFECTS: Constructs a city object
    public City(String cityName) {
        this.name = cityName;
        this.weather = new Weather(this);
    }

    //EFFECTS: prints out weather for city.
    public void printData() {
        System.out.println("Now Showing Weather Information for: " + this.name);
        System.out.println("Temperature: " + weather.getTemperature());
        System.out.println("Humidity: " + weather.getHumidity());
        System.out.println("Wind Speed: " + weather.getWindSpeed());
        System.out.println("Wind Angle: " + weather.getWindAngle());
        System.out.println();
    }

    public void searchCity() {
        File file = new File("src/cities/city.list.json");
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new FileReader(file));
            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String x = result.toString().replaceAll("\\s+","");
            System.out.println(x.subSequence(0,30));
            //Map<String,Object> y = jsonToMap(x);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
