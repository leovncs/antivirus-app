package scanner;

import java.io.File;

public class FileDetector {

    private static final String[] TARGET_FILES = {
            "execucao-aula-teste.exe",
            "execucao-aula.exe",
            "script-aula-teste.bat",
            "script-aula.bat"
    };

    public boolean isTargetFile(File file){
        String fileName = file.getName().toLowerCase();

        for (String target : TARGET_FILES){
            if (fileName.equalsIgnoreCase(target))
                return true;
            }
        return false;
        }
    }
}
