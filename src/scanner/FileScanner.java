package scanner;

import utils.Logger;

import java.io.File;

public class FileScanner {
    private final FileDetector fileDetector = new FileDetector();

    public void scanDirectory(String rootDirectory){
        File directory = new File(rootDirectory);

        if (!directory.exists()) {
            Logger.error("The specific repository does not exist: " + rootDirectory);
            return;
        }

        if (!directory.isDirectory()){
            Logger.error("The specified path is not a directory: " + rootDirectory);
            return;
        }

        Logger.info("Scanning the directory: " + rootDirectory);
        scanRecursive(directory);
    }

    private void scanRecursive(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                // Check if the file is suspicious
                if (fileDetector.isTargetFile(file)) {
                    Logger.warning("File detected: " + file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                // Scan subdirectories recursively
                scanRecursive(file);
            }
        }
    }
}
