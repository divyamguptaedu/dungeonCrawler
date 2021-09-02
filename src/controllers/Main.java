package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private static Main mainInstance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainInstance = new Main();
        mainInstance.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        primaryStage.setTitle("Dungeon Crawler");

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void changeScene(Scene scene) {
        getMainInstance().getPrimaryStage().setScene(scene);
    }

    public Stage getPrimaryStage() {
        return mainInstance.primaryStage;
    }

    public static Main getMainInstance() {
        return mainInstance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
