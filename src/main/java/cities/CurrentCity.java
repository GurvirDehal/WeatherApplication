package cities;

import Time.Time;
import Weather.Forecast;

public class CurrentCity extends City {
    private Time time;
    private Forecast forecast;

    public CurrentCity(String cityName, String id) {
        super(cityName, id);
    }

}
