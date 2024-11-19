package utils;

public class Logger {
    public static void info(String message){
        System.out.println("[INFO] " + message);
    }

    public static void warning(String message){
        System.out.println("[WARNING] " + message);
    }

    public static void error(String message){
        System.out.println("[ERROR] " + message);
    }
}
