package cities;

import Weather.Forecast;

public class CurrentTown extends City {
    private String time;
    private Forecast forecast;

    public CurrentTown(String cityName) {
        super(cityName);
    }

}
