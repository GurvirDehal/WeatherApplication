package Location;

import Time.Clock;
import Weather.Forecast;

public class CurrentCity extends City {
    private Clock time;
    private Forecast forecast;

    public CurrentCity(String cityName, String id) {
        super(cityName, id);
    }


}

