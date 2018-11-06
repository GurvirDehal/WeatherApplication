package Test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class WeatherApplicationTest {

    @Test
    public void testStringArray() {
        String [] x = {"Calgary", " Vancouver"};
        System.out.println(Arrays.toString(x));
        for(String i: x) {
            System.out.println(i.replaceAll("\\s+", ""));
        }
    }

    @Test
    public void test2() {
        String x = "Calgary,5913490";
        String[] lol = x.split(",");
        System.out.println(lol.length);
        System.out.println(Arrays.toString(lol));
    }
}
