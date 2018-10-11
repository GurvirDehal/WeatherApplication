package ui;

import InputOutput.ReadWrite;
import cities.City;
import cities.OtherCity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherApplication {
    private JTextField textField;
    private JLabel enterCity;
    private JLabel temp;
    private JLabel humidity;
    private JLabel windSpeed;
    private JLabel windAngle;
    private JPanel weatherView;
    private String fontName;
    private static String[] favoriteCities = {"Calgary", "Vancouver", "Toronto"};

    public static void main(String[] args) {
        JFrame frame = new JFrame("WeatherApplication");
        frame.setContentPane(new WeatherApplication().weatherView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ReadWrite rw = new ReadWrite();
        //rw.saveFile("FavoriteCities.txt",favoriteCities);
        rw.loadFile("FavoriteCities.txt", favoriteCities);
    }

    private void initializeUIComponents() {
        textField = new JTextField();
        enterCity = new JLabel();
        temp = new JLabel();
        humidity = new JLabel();
        windSpeed = new JLabel();
        windAngle = new JLabel();
        fontName = "Helvetica";
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
                String text = textField.getText();
                City city = new OtherCity(text);
                city.printData();
                temp.setText("Temperature: " + city.weather.getTemperature());
                humidity.setText("Humidity: " + city.weather.getHumidity());
                windSpeed.setText("Wind Speed: " + city.weather.getWindSpeed());
                windAngle.setText("Wind Angle: " + city.weather.getWindAngle());
            }
        });

        enterCity.setFont(new Font(fontName,Font.PLAIN,26));
        temp.setFont(new Font(fontName,Font.PLAIN,26));
        humidity.setFont(new Font(fontName,Font.PLAIN,26));
        windAngle.setFont(new Font(fontName,Font.PLAIN,26));
        windSpeed.setFont(new Font(fontName,Font.PLAIN,26));
    }






}
