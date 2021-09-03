package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EndScreenController {

    @FXML private Label lblCongratulations;
    @FXML private Label lblYouWon;
    @FXML private Label lblPlayerStatisticsHeader;
    @FXML private Label lblMonstersKilledPrompt;
    @FXML private Label lblTotalDamageDonePrompt;
    @FXML private Label lblRoomsVisitedPrompt;

    @FXML private Label lblMonstersKilled;
    @FXML private Label lblDamageDone;
    @FXML private Label lblRoomsVisited;

    @FXML private Button btnViewPlayerStats;
    @FXML private Button btnPlayAgain;
    @FXML private Button btnQuit;

    private int monstersKilled;
    private int damageDone;
    private int roomsVisited;

    private static GameController game;

    public static void setGC(GameController temp)
    {
        game=temp;
    }


    @FXML
    private void onViewPlayerStatsClicked(ActionEvent actionEvent) throws IOException {

        lblPlayerStatisticsHeader.setVisible(true);
        lblMonstersKilledPrompt.setVisible(true);
        lblTotalDamageDonePrompt.setVisible(true);
        lblRoomsVisitedPrompt.setVisible(true);

        monstersKilled = game.getMonstersKilled();
        damageDone = game.getDamageDone();
        roomsVisited = game.getRoomsVisited();

        lblMonstersKilled.setText("" + monstersKilled);
        lblDamageDone.setText("" + damageDone);
        lblRoomsVisited.setText("" + roomsVisited);

        lblMonstersKilled.setVisible(true);
        lblDamageDone.setVisible(true);
        lblRoomsVisited.setVisible(true);
    }

    @FXML
    private void onPlayAgainClicked(ActionEvent actionEvent) throws IOException {
        GameConfigController.reset();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GameConfig.fxml"));
        Main.getMainInstance().getPrimaryStage().setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void onQuitClicked(ActionEvent actionEvent) {

        //Main.getMainInstance().getPrimaryStage().getScene().getWindow();
        Stage currStage = (Stage) btnQuit.getScene().getWindow();
        currStage.close();

    }

}
