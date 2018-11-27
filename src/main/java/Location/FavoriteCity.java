package Location;

import Time.Clock;

public class FavoriteCity extends City {
    private Clock time;
    public FavoriteCity(String cityName, String id) {
        super(cityName, id);
    }

    public FavoriteCity(OtherCity c) {
        super(c);
        // also add a time to this city;
    }
}
