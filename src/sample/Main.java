package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("SecLayout.fxml"));



        FXMLLoader loader = new FXMLLoader(getClass().getResource("SecLayout.fxml"));
        Parent root = (Parent)loader.load();

        SecurityInstaller controller = (SecurityInstaller)loader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle("Best Soft: Configuration keys instalation wizard");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 632, 420));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
