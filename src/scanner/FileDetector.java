package scanner;

import java.io.File;

public class FileDetector {

    // Array contendo os nomes dos arquivos 'alvos' que o programa deve identificar
    private static final String[] TARGET_FILES = {
            "execucao-aula-teste.exe",
            "execucao-aula.exe",
            "script-aula-teste.bat",
            "script-aula.bat"
    };

    // Método que verifica se um arquivo é um dos 'alvos' especificados
    public boolean isTargetFile(File file){
        String fileName = file.getName().toLowerCase(); // Obtém o nome do arquivo em letras minúsculas

        // Percorre os arquivos 'alvos' para verificar se há uma correspondência
        for (String target : TARGET_FILES){
            if (fileName.equalsIgnoreCase(target.toLowerCase())) // Compara o nome do arquivo com os nome no Array TARGET_FILES
                return true; // Retorna verdadeiro se o arquivo for um 'alvo'
            }
        return false; // Retorna falso se nenhum nome coincidir com os 'alvos'
    }

    // Método que retorna o número de arquivos 'alvos' definidos no Array TARGET_FILES
    public int getTargetFilesCount() {
        return TARGET_FILES.length; // Retorna o tamanho do array TARGET_FILES
    }
}
