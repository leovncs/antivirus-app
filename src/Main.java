import scanner.FileScanner;

public class Main {
    public static void main(String[] args) {
        String rootDirectory = "C:\\"; // Diretório raiz onde a pesquisa começará

        System.out.println("Welcome to the UC Basic Antivirus!");
        System.out.println("Starting the scan...");

        FileScanner scanner = new FileScanner(); // Cria uma instância da classe FileScanner
        scanner.scanDirectory(rootDirectory); // Chama o método scanDirectory para verificar o rootDirectory

        System.out.println("Verification completed.");
    }
}