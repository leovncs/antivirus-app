package scanner;

import utils.Logger;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileScanner {
    private final FileDetector fileDetector = new FileDetector();

    // Thread pool to execute tasks
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Synchronized set to track detected files
    private final Set<String> detectedFiles = ConcurrentHashMap.newKeySet();

    // Variable to track if all files are found
    private final AtomicBoolean allFilesDetected = new AtomicBoolean(false);

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

        // Wait for the tasks to complete or all files to be detected
        try {
            while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                if (allFilesDetected.get()) {
                    Logger.info("All target files detected. Stopping scan.");
                    executorService.shutdownNow(); // Interrupt ongoing tasks
                    break;
                }
            }
        } catch (InterruptedException e) {
            Logger.error("Scan interrupted: " + e.getMessage());
        }

        Logger.info("Verification completed.");
    }

    private void scanRecursive(File directory) {
        File[] files = directory.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        for (File file : files) {
            try {
                if (allFilesDetected.get()) {
                    return; // Stop further processing if all files are detected
                }

                if (file.isFile()) {
                    if (fileDetector.isTargetFile(file)) {
                        Logger.warning("File detected: " + file.getAbsolutePath());
                        detectedFiles.add(file.getName().toLowerCase());

                        // Check if all target files are detected
                        if (detectedFiles.size() == fileDetector.getTargetFilesCount()) {
                            allFilesDetected.set(true);
                            return;
                        }
                    }
                } else if (file.isDirectory()) {
                    executorService.submit(() -> scanRecursive(file));
                }
            } catch (SecurityException e) {
                Logger.error("Permission denied to access: " + file.getAbsolutePath());
            }
        }
    }
}
