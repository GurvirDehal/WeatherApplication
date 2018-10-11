package InputOutput;

import java.io.*;

public class ReadWrite implements Load, Save{
    @Override
    public String loadFile(String fileName, String[] names) {
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            String last = "", line;

            while ((line = inputStream.readLine()) != null) {
                last = line;
            }
            return last;
        } catch(FileNotFoundException ex) {
            //System.out.println(ex);
            // if file not created then create the file
            System.out.println("Creating file...");
            saveFile(fileName,names);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void saveFile(String fileName, String[] names) {
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(new FileWriter(fileName, true));
            for(int i = 0; i < names.length; i++) {
                outputStream.append(names[i] + '\n');
            }
        } catch(IOException ex) {
            System.out.println(ex);
        }
        if(outputStream != null) {
            outputStream.close();
        }
    }
}

