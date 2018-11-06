package InputOutput;

import java.io.FileNotFoundException;

public interface Load {
    String fileName = null;
    String[] readFile() throws FileNotFoundException;
}
