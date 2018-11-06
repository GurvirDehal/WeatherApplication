package InputOutput;

import java.io.*;
import java.util.ArrayList;

public class ReadWrite implements Load, Save{

    String fileName;

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
                outputStream.append(values[i]+ '\n');
            }
        } catch(IOException ex) {
            System.out.println(ex);
        }
        if(outputStream != null) {
            outputStream.close();
        }
    }
}

