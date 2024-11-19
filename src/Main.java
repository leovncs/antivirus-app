import scanner.FileScanner;

public class Main {
    public static void main(String[] args) {
        String rootDirectory = "C:\\"; //Root directory where the search will begin

        System.out.println("Welcome to the UC Basic Antivirus!");
        System.out.println("Starting the scan...");

        FileScanner scanner = new FileScanner();
        scanner.scanDirectory(rootDirectory);

        System.out.println("Verification completed.");
    }
}