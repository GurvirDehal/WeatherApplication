package ui;

import Exceptions.CityNotFoundException;
import InputOutput.ReadWrite;
import Weather.Weather;
import cities.City;
import cities.FavoriteCity;
import cities.ListOfFavoriteCities;
import cities.OtherCity;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class WeatherApplication {
    private JTextField textField;
    private JLabel enterCity;
    private JLabel temp;
    private JLabel humidity;
    private JLabel windSpeed;
    private JLabel description;
    private JPanel weatherView;
    private String fontName;
    private ReadWrite rw;
    private ListOfFavoriteCities loc;

    public static void main(String[] args) {
        JFrame frame = new JFrame("WeatherApplication");
        frame.setContentPane(new WeatherApplication().weatherView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void initializeUIComponents() {
        textField = new JTextField();
        enterCity = new JLabel();
        temp = new JLabel();
        humidity = new JLabel();
        windSpeed = new JLabel();
        description = new JLabel();
        fontName = "Helvetica";
        rw = new ReadWrite("FavoriteCities.txt");
        loc = initializeListOfFavCity();
    }

    private void createUIComponents() {
        initializeUIComponents();
        weatherView = new JPanel();
        weatherView.setPreferredSize(new Dimension(1280,720));

        textField.setPreferredSize(new Dimension(20,50));
        textField.setFont(new Font(fontName, Font.PLAIN, 26));
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    City city = getCityFromUser(textField.getText());
                    city.printData();
                    Weather weather = city.getWeather();
                    temp.setText("Temperature: " + weather.getTemperature() + " °C");
                    humidity.setText("Humidity: " + weather.getHumidity() + " %");
                    windSpeed.setText("Wind Speed: " + weather.getWindSpeed() + " m/s");
                    description.setText("Description: " + weather.getDescription());
                } catch (CityNotFoundException ex) {
                    System.out.println("Sorry not valid");
                }
            }
        });

        enterCity.setFont(new Font(fontName,Font.PLAIN,26));
        temp.setFont(new Font(fontName,Font.PLAIN,26));
        humidity.setFont(new Font(fontName,Font.PLAIN,26));
        description.setFont(new Font(fontName,Font.PLAIN,26));
        windSpeed.setFont(new Font(fontName,Font.PLAIN,26));
        }


    private OtherCity getCityFromUser(String text) throws CityNotFoundException {
            // Search for city, if exists then create it, and pass in id as parameter, as well as name
            String s = searchCity(text);
            if(!s.equals("-1")) {
                 OtherCity city = new OtherCity(text, s);
                 return city;
            } else {
                throw new CityNotFoundException();
            }
    }

    public static String searchCity(String name) {
        File file = new File("src/city.list.json");
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader rd = new BufferedReader(new FileReader(file));
            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String x = result.toString().replaceAll("\\s+","");
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(x);
            JsonArray rootArr = root.getAsJsonArray();
            for(int i = 0; i < rootArr.size(); i++) {
                JsonObject rootObj = rootArr.get(i).getAsJsonObject();
                String s = rootObj.get("name").toString().replace("\"", "");
                //System.out.println(s);
                if(name.equals(s)){
                    System.out.println(rootObj.get("id"));
                    return rootObj.get("id").toString();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public ListOfFavoriteCities initializeListOfFavCity() {
        ListOfFavoriteCities loc = new ListOfFavoriteCities();
        try {
            String[] arr = rw.readFile();
            for(String x: arr) {
                String[] x2 = x.split(",");
                //System.out.println(x2);
                loc.add(new FavoriteCity(x2[0],x2[1]));
            }
            System.out.println(Arrays.toString(loc.getListOfNames()));

        } catch(FileNotFoundException e) {
            //Create new file, ask for favorite cities
            //UIManager.put("OptionPane.minimumSize",new Dimension(1920, 1080));
            boolean validCities = false;

            String[] cityAndId;
            do {
                JLabel label = new JLabel("Please enter your favorite cities");
                label.setFont(new Font("Arial", Font.BOLD, 20));
                UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.BOLD, 32)));
                String s = JOptionPane.showInputDialog(label);
                ArrayList<String> cities= new ArrayList<String>(Arrays.asList(s.split(",")));
                cities.replaceAll( (String x) -> x.replaceAll("\\s+",""));
                //for(String x: cities) x.replaceAll("\\s+","");
                cityAndId = new String[cities.size()];
                for (int i = 0; i < cities.size(); i++) {
                    try {
                        loc.add(new FavoriteCity(getCityFromUser(cities.get(i))));
                        cityAndId[i] = cities.get(i) + "," + loc.getCity(i).getId();
                        validCities = true;
                    } catch (CityNotFoundException ex) {
                        validCities = false;
                        System.out.println("Sorry you have entered an invalid city");
                        //throw new FileNotFoundException();
                    }
                }
            } while(!validCities);
            rw.writeFile(cityAndId);
        }
        return loc;
    }
}
