package scanner;

import utils.Logger;

import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileScanner {

    // Instância da classe FileDetector para identificar arquivos 'alvo'
    private final FileDetector fileDetector = new FileDetector();

    // Thread pool para gerenciar tarefas de varredura
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Conjunto sincronizado para rastrear os arquivos detectados
    private final Set<String> detectedFiles = ConcurrentHashMap.newKeySet();

    // Variável para indicar se todos os arquivos 'alvo' foram encontrados
    private final AtomicBoolean allFilesDetected = new AtomicBoolean(false);

    // Método principal para escanear um diretório especificado
    public void scanDirectory(String rootDirectory) {
        File directory = new File(rootDirectory);

        // Verifica se o diretório existe
        if (!directory.exists()) {
            Logger.error("The specific repository does not exist: " + rootDirectory);
            return;
        }

        // Verifica se o caminho especificado é realmente um diretório
        if (!directory.isDirectory()) {
            Logger.error("The specified path is not a directory: " + rootDirectory);
            return;
        }

        Logger.info("Scanning the directory: " + rootDirectory);

        // Envia a tarefa inicial para escanear o diretório ao ExecutorService
        executorService.submit(() -> scanRecursive(directory));

        // Aguarda a conclusão das tarefas ou a detecção de todos os arquivos 'alvo'
        try {
            while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                if (allFilesDetected.get()) {
                    Logger.info("All target files detected. Stopping scan.");
                    executorService.shutdownNow(); // Interrompe tarefas em andamento
                    break;
                }
            }
        } catch (InterruptedException e) {
            Logger.error("Scan interrupted: " + e.getMessage());
        }

        Logger.info("Verification completed.");
        promptFileDeletion(); // Chama o método para solicitar exclusão dos arquivos
    }

    // Método recursivo para escanear diretórios e subdiretórios
    private void scanRecursive(File directory) {
        File[] files = directory.listFiles();

        // Se o diretório estiver vazio ou inacessível, retorna
        if (files == null || files.length == 0) {
            return;
        }

        // Itera sobre os arquivos e subdiretórios
        for (File file : files) {
            try {
                // Interrompe a varredura se todos os arquivos 'alvo' forem detectados
                if (allFilesDetected.get()) {
                    return;
                }

                // Verifica se o arquivo é um arquivo comum
                if (file.isFile()) {
                    // Verifica se o arquivo é um dos "alvos"
                    if (fileDetector.isTargetFile(file)) {
                        String absolutePath = file.getAbsolutePath();
                        Logger.warning("File detected: " + absolutePath);
                        detectedFiles.add(absolutePath);

                        // Verifica se todos os arquivos 'alvos' foram detectados
                        if (detectedFiles.size() == fileDetector.getTargetFilesCount()) {
                            allFilesDetected.set(true);
                            return;
                        }
                    }
                    // Se o arquivo for um diretório, envia uma nova tarefa para escanear seu conteúdo
                } else if (file.isDirectory()) {
                    executorService.submit(() -> scanRecursive(file));
                }
            } catch (SecurityException e) {
                Logger.error("Permission denied to access: " + file.getAbsolutePath());
            }
        }
    }

    // Método para deletar arquivos e diretórios de forma recursiva.
    private boolean deleteRecursive(File file) {
        try {
            // Se for um diretório, exclui os arquivos e subdiretórios primeiro
            if (file.isDirectory()) {
                File[] files = file.listFiles(); // Lista os conteúdos do diretório
                if (files != null) {
                    for (File f : files) {
                        deleteRecursive(f); // Chamada recursiva para cada item
                    }
                }
            }

            // Tenta excluir o arquivo ou diretório vazio e retorna o resultado
            if (file.delete()) {
                Logger.info("Deleted: " + file.getAbsolutePath());
                return true;
            } else {
                Logger.error("Failed to delete: " + file.getAbsolutePath());
                return false;
            }
        } catch (SecurityException e) {
            Logger.error("Permission denied to delete: " + file.getAbsolutePath() + " - " + e.getMessage());
            return false;
        }
    }

    // Método para solicitar ao usuário a permissão para deletar os arquivos detectados
    private void promptFileDeletion() {

        // Se não houver arquivos detectados, exibe uma mensagem e encerra
        if (detectedFiles.isEmpty()) {
            Logger.info("No target files were detected. No action required.");
            return;
        }

        System.out.println("Do you want to delete all detected files? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String userResponse = scanner.nextLine().trim().toLowerCase();

        // Se o usuário confirmar com "yes", tenta deletar todos os arquivos
        if (userResponse.equals("yes")) {
            for (String filePath : detectedFiles) {
                File file = new File(filePath);
                deleteRecursive(file); // Chama o método de exclusão recursiva
            }
        } else {
            // Caso contrário, informa que a exclusão foi cancelada
            Logger.info("File deletion canceled by user. No files were deleted.");
        }

        scanner.close();
    }
}