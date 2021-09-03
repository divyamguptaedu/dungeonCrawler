package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class PlayerDeadController {

    @FXML
    private Button btnRestart;
    @FXML
    private Button btnEnd;

    @FXML
    private void restartClicked() throws IOException {
        GameConfigController.reset();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameConfig.fxml"));
        Main.getMainInstance().getPrimaryStage().setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void endClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameOver.fxml"));
        Main.getMainInstance().getPrimaryStage().setScene(new Scene(root, 800, 800));
    }

}
