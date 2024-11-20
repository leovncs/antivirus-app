package utils;

public class Logger {

    //Método para registrar a mensagem no console com o prefixo "[INFO]"
    public static void info(String message){
        System.out.println("[INFO] " + message);
    }

    //Método para registrar a mensagem no console com o prefixo "[WARNING]"
    public static void warning(String message){
        System.out.println("[WARNING] " + message);
    }

    //Método para registrar a mensagem no console com o prefixo "[ERROR]"
    public static void error(String message){
        System.out.println("[ERROR] " + message);
    }
}
