//package Test;
//import cities.City;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//
//public class CityTest {
//
//    City city = new City("Toronto");
//    String s = "{\"coord\":{\"lon\":-79.39,\"lat\":43.65},\"weather\":[{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50n\"}],\"base\":\"stations\",\"main\":{\"temp\":13.76,\"pressure\":1016,\"humidity\":93,\"temp_min\":11,\"temp_max\":17},\"visibility\":14484,\"wind\":{\"speed\":3.6,\"deg\":350},\"clouds\":{\"all\":90},\"dt\":1538524380,\"sys\":{\"type\":1,\"id\":3721,\"message\":0.0039,\"country\":\"CA\",\"sunrise\":1538565447,\"sunset\":1538607294},\"id\":6167865,\"name\":\"Toronto\",\"cod\":200}";
//
//    public static Map<String, Object> jsonToMap (String str) {
//        Map<String, Object> map = new Gson().fromJson(
//                str, new TypeToken<HashMap<String, Object>>() {}.getType()
//        );
//        return map;
//    }
//
//    @Test
//    public void testGetWeather() {
//        assertFalse(city.getWeather().equals(null));
//    }
//    @Test
//    public void testGetTemp() {
//        System.out.println(city.getTemperature());
//    }
//    @Test
//    public void testGetHumidity() {
//        System.out.println(city.getHumidity());
//    }
//    @Test
//    public void testGetWindSpeed() {
//        System.out.println(city.getWindSpeeds());
//    }
//    @Test
//    public void testGetWindAngle() {
//        System.out.println(city.getWindAngle());
//    }
//    @Test
//    public void testSearchCity() {
//        city.searchCity();
//    }
//    @Test
//    public void testCityMapConstructor() {
//        City c = new City(jsonToMap(s));
//        System.out.println(c.name);
//        System.out.println(c.getTemperature());
//        System.out.println(c.weatherData);
//    }
//}
