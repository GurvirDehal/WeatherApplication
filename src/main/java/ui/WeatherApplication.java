package ui;

import Exceptions.CityNotFoundException;
import InputOutput.ReadWrite;
import Location.City;
import Location.FavoriteCity;
import Location.ListOfFavoriteCities;
import Location.OtherCity;
import Time.Clock;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class WeatherApplication implements Observer {
    private JTextField textField;
    private JLabel enterCity;
    private JLabel temp;
    private JLabel humidity;
    private JLabel windSpeed;
    private JLabel description;
    private JPanel weatherView;
    private JLabel time;
    private JLabel iconImg;
    private JButton addFavCitiesButton;
    private JLabel background;
    private Clock clock;
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
        time = new JLabel();
        clock = new Clock();
        iconImg = new JLabel();
        background = new JLabel(new ImageIcon("bg.png"));
        rw = new ReadWrite("FavoriteCities.txt");
        loc = initializeListOfFavCity();
        addFavCitiesButton = new JButton();
    }

    private void createUIComponents() {
        initializeUIComponents();
        //enterCity.setText("Enter City Name: " + '\n' + "Ex: London, UK" );
        //enterCity.setText("asdfasdf");
        weatherView = new JPanel() {
            Image image = requestImage();
            @Override
            protected  void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image,0,0,null);
            }
        };
        weatherView.setPreferredSize(new Dimension(1280, 720));

        List<JLabel> fields = new ArrayList<>();
        fields.add(temp);
        fields.add(humidity);
        fields.add(windSpeed);
        fields.add(description);
        fields.add(enterCity);
        fields.add(time);

        //Font font  = new Font("Helvetica", Font.PLAIN, 26);
        Font font = new Font("Arial", Font.PLAIN,40);

        textField.setPreferredSize(new Dimension(20, 50));
        textField.setFont(font);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    City city = getCityFromUser(textField.getText());
                    city.printData();
                    List<String> weatherReport = city.getWeather().getOrganizedWeatherReport();
//                    temp.setText("Temperature: " + weather.getTemperature() + " °C");
//                    humidity.setText("Humidity: " + weather.getHumidity() + " %");
//                    windSpeed.setText("Wind Speed: " + weather.getWindSpeed() + " m/s");
//                    description.setText("Description: " + weather.getDescription());
                    for (int i = 0; i < weatherReport.size(); i++) {
                        fields.get(i).setText(weatherReport.get(i));
                    }
                    URL url = new URL("http://openweathermap.org/img/w/" + city.getWeather().getIcon() + ".png");
                    BufferedImage c = ImageIO.read(url);
                    ImageIcon i = new ImageIcon(c);
                    iconImg.setIcon(i);
                } catch (CityNotFoundException ex) {
                    System.out.println("Sorry not valid");
                } catch(MalformedURLException mue) {

                } catch(IOException ioe) {

                }
            }
        });
        clock.addObserver(this);
        time.setText("Time: " + clock.getTime());

        addFavCitiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create new file, ask for favorite Location
                //UIManager.put("OptionPane.minimumSize",new Dimension(1920, 1080));
                boolean validCities = false;
                String[] cityAndId;
                do {
                    JLabel label = new JLabel("Please enter your favorite Location");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.BOLD, 32)));
                    String s = JOptionPane.showInputDialog(label);
                    try {
                        ArrayList<String> cities = new ArrayList<String>(Arrays.asList(s.split(",")));
                        cities.replaceAll((String x) -> x.replaceAll("\\s+", ""));
                        //for(String x: Location) x.replaceAll("\\s+","");
                        cityAndId = new String[cities.size()];
                        for (int i = 0; i < cities.size(); i++) {
                            try {
                                FavoriteCity fc = new FavoriteCity(getCityFromUser(cities.get(i)));
                                loc.add(fc);
                                cityAndId[i] = cities.get(i) + "," + fc.getId();
                                rw.writeFile(cityAndId);
                                validCities = true;
                            } catch (CityNotFoundException ex) {
                                validCities = false;
                                System.out.println("Sorry you have entered an invalid city");
                                //throw new FileNotFoundException();
                            }
                        }
                    } catch(NullPointerException error) {
                        System.out.println(error.getMessage());
                        break;
                    }
                } while(!validCities);

            }
        });

        //iconImg.setPreferredSize(new Dimension(50,50));
//
//        enterCity.setFont(new Font(fontName,Font.PLAIN,26));
//        temp.setFont(new Font(fontName,Font.PLAIN,26));
//        humidity.setFont(new Font(fontName,Font.PLAIN,26));
//        description.setFont(new Font(fontName,Font.PLAIN,26));
//        windSpeed.setFont(new Font(fontName,Font.PLAIN,26));

        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setFont(font);
        }
    }

    private Image requestImage() {
        BufferedImage in = null;
        try {
            in = ImageIO.read(new File("bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        //System.out.println("Time: " + arg1);
        time.setText((String) arg1);
    }


    private OtherCity getCityFromUser(String text) throws CityNotFoundException {
        //if loc doesn't have it, then do this:
            String [] s = searchCity(text).split(",");
            // Search for city, if exists then create it, and pass in id as parameter, as well as name
            //String s = searchCity(text);
            if(!s[0].equals("-1")) {
                 OtherCity city = new OtherCity(s[1], s[0], s[2]);
                 return city;
            } else {
                throw new CityNotFoundException();
            }
    }

    public static String searchCity(String name) {
        String[] y = name.split(",");
        File file = new File("src/main/java/city.list.json");
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
                String countryCode = rootObj.get("country").toString().replace("\"", "");
                //System.out.println(s);
                try {
                    if (y[0].equals(s) && y[1].equals(countryCode)) {
                        System.out.println(rootObj.get("id"));
                        return rootObj.get("id").toString() + "," + s + "," + countryCode;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {
                    if(y[0].equals(s)) {
                        System.out.println(rootObj.get("id"));
                        return rootObj.get("id").toString() + "," + s + "," + countryCode;
                    }
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
            //Create new file, ask for favorite Location
            //UIManager.put("OptionPane.minimumSize",new Dimension(1920, 1080));
            boolean validCities = false;

            String[] cityAndId;
            do {
                JLabel label = new JLabel("Please enter your favorite Location");
                label.setFont(new Font("Arial", Font.BOLD, 20));
                UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.BOLD, 32)));
                String s = JOptionPane.showInputDialog(label);
                ArrayList<String> cities= new ArrayList<String>(Arrays.asList(s.split(",")));
                cities.replaceAll( (String x) -> x.replaceAll("\\s+",""));
                //for(String x: Location) x.replaceAll("\\s+","");
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

    private String getCurrentLocation() {
        String ip = getIPAddress();
        String location = ReadWrite.searchDataBaseForIP("src/main/java/GeoLite2-City.mmdb", ip);
        return location;
    }

    private String getIPAddress() {
        return ReadWrite.readUrl("http://checkip.amazonaws.com");
    }
}
