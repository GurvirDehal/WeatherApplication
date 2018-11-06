package Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class GeoLiteCityTest {

    @Test
    public void test() {
        // A File object pointing to your GeoIP2 or GeoLite2 database
        File database = new File("src/main/java/GeoLite2-City.mmdb");

        // This creates the DatabaseReader object. To improve performance, reuse
// the object across lookups. The object is thread-safe.
        DatabaseReader reader = null;

        {
            try {
                reader = new DatabaseReader.Builder(database).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        InetAddress ipAddress = null;

        {
            try {
                URL whatismyip = new URL("http://checkip.amazonaws.com");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));

                String ip = in.readLine(); //you get the IP as a String
                ipAddress = InetAddress.getByName(ip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch(MalformedURLException e2){
                e2.printStackTrace();
            } catch(IOException e3) {
                e3.printStackTrace();
            }
        }
        CityResponse response = null;

        {
            try {
                response = reader.city(ipAddress);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            }
        }

        Country country = response.getCountry();
        City city = response.getCity();
        System.out.println(city.getName()  + ", "  + country.getIsoCode());
    }


}