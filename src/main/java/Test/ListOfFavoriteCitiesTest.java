package Test;

import cities.FavoriteCity;
import cities.ListOfFavoriteCities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListOfFavoriteCitiesTest {
    ListOfFavoriteCities loc;
    @BeforeEach
    public void init() {
        loc = new ListOfFavoriteCities();
        loc.add(new FavoriteCity("Calgary","5913490" ));
    }

    @Test
    public void testAdd() {
        loc.add(new FavoriteCity("Calgary", "5913490"));
        assertTrue(loc.size()== 1);
        loc.add(new FavoriteCity("Vancouver","6173331"));
        assertTrue(loc.size() == 2);
    }
}
