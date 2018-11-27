package InputOutput;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ReadWrite implements Load, Save{

    private String fileName;

    public ReadWrite(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public String[] readFile() throws FileNotFoundException {
        BufferedReader inputStream = null;
        ArrayList<String> s = new ArrayList<String>();
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = inputStream.readLine()) != null) {
                s.add(line);
            }
            return s.toArray(new String[s.size()]);
        } catch(FileNotFoundException ex) {
            throw new FileNotFoundException();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void writeFile(String [] values) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileWriter(fileName, true));
            for(int i = 0; i < values.length; i++) {
                outputStream.append((values[i]+ '\n'));
            }
        } catch(IOException ex) {
            System.out.println(ex);
        }
        if(outputStream != null) {
            outputStream.close();
        }
    }

    public static String readUrl(String urlName) {
        String result = null;
        try {
            URL url = new URL(urlName);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));

            result = in.readLine(); //you get the IP as a String
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch(MalformedURLException e2){
            e2.printStackTrace();
        } catch(IOException e3) {
            e3.printStackTrace();
        }
        return result;
    }

    public static String searchDataBaseForIP(String databaseLocation, String ip) {
        File database = new File(databaseLocation);
        DatabaseReader reader = null;

        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InetAddress ipAddress = null;

        try {
            ipAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch(IOException e2) {
            e2.printStackTrace();
        }

        CityResponse response = null;

        try {
            response = reader.city(ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        String countryCode = response.getCountry().getIsoCode();
        City city = response.getCity();
        return city.getName()  + ", "  + countryCode;

    }


}

