package sample;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("nogui")) {
            System.out.println("NOGUI SELECTED");
            SecurityInstallerNoGUI controller = new SecurityInstallerNoGUI();
            controller.initialize();
            controller.startInstallation();

        } else {
            NewMain.launch(NewMain.class, args);
        }
    }
}