package Test;

import InputOutput.ReadWrite;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GeoLiteCityTest {

    @Test
    public void test() {
        // A File object pointing to your GeoIP2 or GeoLite2 database
        File database = new File("src/main/java/GeoLite2-City.mmdb");

        // This creates the DatabaseReader object. To improve performance, reuse
// the object across lookups. The object is thread-safe.
        DatabaseReader reader = null;

        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }



        InetAddress ipAddress = null;


        try {
            String ip = ReadWrite.readUrl("http://checkip.amazonaws.com");
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch(IOException e2) {
            e2.printStackTrace();
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

        String countryCode = response.getCountry().getIsoCode();
        City city = response.getCity();
        System.out.println(city.getName()  + ","  + countryCode);
    }

    @Test
    public void test2() {
        String s = ReadWrite.readUrl("http://checkip.amazonaws.com");
        System.out.println(s);
        String x = ReadWrite.searchDataBaseForIP("src/main/java/GeoLite2-City.mmdb", s);
        System.out.println(x);
    }


}