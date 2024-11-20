package scanner;

import utils.Logger;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileScanner {
    private final FileDetector fileDetector = new FileDetector();

    // Thread pool to execute tasks
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

        // Submits the first task to the ExecutorService
        executorService.submit(() -> scanRecursive(directory));


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
