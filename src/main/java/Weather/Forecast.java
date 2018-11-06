package Weather;

public class Forecast {
    private Weather[] weathers;

    //REQUIRES: i to be between 0 and 6
    public Weather getWeather(int i) {
        return weathers[i];
    }

}
