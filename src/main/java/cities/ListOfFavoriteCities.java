package cities;

import java.util.ArrayList;
import java.util.List;

public class ListOfFavoriteCities {
    private List<FavoriteCity> loc;

    public ListOfFavoriteCities() {
        loc = new ArrayList<FavoriteCity>();
    }

    public void add(FavoriteCity c) {
        if(!loc.contains(c)) {
            loc.add(c);
        }
    }

    public void remove(FavoriteCity c) {
        loc.remove(c);
    }

    public int size() {
        return loc.size();
    }

    public String[] getListOfNames() {
        List<String> x = new ArrayList<String>();
        for(int i =0 ; i< loc.size(); i++) {
            x.add(loc.get(i).name);
        }
        String[] y = new String[x.size()];
        return x.toArray(y);
    }

    public City getCity(int i) {
        return loc.get(i);
    }

}
