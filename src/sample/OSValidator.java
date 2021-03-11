package sample;

public class OSValidator {

    private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    public static boolean IS_MAC = (OS.indexOf("mac") >= 0);
    public static boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    public static boolean IS_SOLARIS = (OS.indexOf("sunos") >= 0);


    public static boolean isIsWindows() {
        return IS_WINDOWS;
    }

    public static boolean isIsMac() {
        return IS_MAC;
    }

    public static boolean isIsUnix() {
        return IS_UNIX;
    }

    public static boolean isIsSolaris() {
        return IS_SOLARIS;
    }
}
