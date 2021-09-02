package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button startButton;

    @FXML
    private void startButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameConfig.fxml"));
        Main.getMainInstance().getPrimaryStage().setScene(new Scene(root, 800, 800));
    }
}
