package scanner;

import java.io.File;

public class FileScanner {
    private final FileDetector fileDetector = new FileDetector();

    public void scanDirectory(String rootDirectory){
        File directory = new File(rootDirectory);

        if (!directory.exists()) {

        }
    }
}
