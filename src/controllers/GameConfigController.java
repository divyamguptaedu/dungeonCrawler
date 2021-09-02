package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class GameConfigController {

    @FXML
    private Label lblSceneTitle;
    @FXML
    private Label lblNamePrompt;
    @FXML
    private Label lblGameDifficulty;
    @FXML
    private Label lblDisplayPlayerName;
    @FXML
    private Label lblDisplayGameDifficulty;

    @FXML
    private TextField txtName;

    @FXML
    private RadioButton radEasy;
    @FXML
    private RadioButton radMedium;
    @FXML
    private RadioButton radHard;

    private static String name = null;
    private static String gameDifficulty = null;
    private static String weapon = null;
    private static boolean enterPressed = false;

    private static GameController gameController;

    @FXML
    private Label weaponChoice;

    @FXML
    private void startGameClicked() throws IOException {
        if (name == null || name.equals("") || containsOnlyWhitespace()) {
            checkNameInput();
        } else if (gameDifficulty == null) {
            Alert empty = new Alert(Alert.AlertType.WARNING);
            empty.setTitle("Warning");
            empty.setHeaderText("Empty Field");
            empty.setContentText("You did not select a game difficulty");
            empty.showAndWait();
        } else if (weapon == null) {
            Alert empty = new Alert(Alert.AlertType.WARNING);
            empty.setTitle("Warning");
            empty.setHeaderText("Empty Field");
            empty.setContentText("You did not select a weapon");
            empty.showAndWait();
        } else {
            gameController = new GameController();
            gameController.start();
        }
    }

    @FXML
    private void rifleSelected() {
        weapon = "Rifle";
        weaponChoice.setText("Weapon: " + weapon);
    }

    @FXML
    private void swordSelected() {
        weapon = "Sword";
        weaponChoice.setText("Weapon: " + weapon);
    }

    @FXML
    private void bazookaSelected() {
        weapon = "Bazooka";
        weaponChoice.setText("Weapon: " + weapon);
    }

    @FXML
    private void onNameEntered(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            enterPressed = true;
            name = txtName.getText();
            checkNameInput();
            lblDisplayPlayerName.setText("Player name: " + name);
        }
    }

    @FXML
    private void onRadEasyClicked(ActionEvent actionEvent) {
        gameDifficulty = "Easy";

        radMedium.setSelected(false);
        radHard.setSelected(false);

        lblDisplayGameDifficulty.setText("Game Difficulty Selected: " + gameDifficulty);

    }

    @FXML
    private void onRadMediumClicked(ActionEvent actionEvent) {
        gameDifficulty = "Medium";
        radEasy.setSelected(false);
        radHard.setSelected(false);
        lblDisplayGameDifficulty.setText("Game Difficulty Selected: " + gameDifficulty);

    }

    @FXML
    private void onRadHardClicked(ActionEvent actionEvent) {
        gameDifficulty = "Hard";
        radEasy.setSelected(false);
        radMedium.setSelected(false);
        lblDisplayGameDifficulty.setText("Game Difficulty Selected: " + gameDifficulty);

    }

    @FXML
    private void checkNameInput() {

        String name = txtName.getText();

        if (!enterPressed) {
            Alert empty = new Alert(Alert.AlertType.ERROR);
            empty.setTitle("Error");
            empty.setHeaderText("Empty Field");
            empty.setContentText("You must press the 'enter' key to save the name.");
            empty.showAndWait();
        } else {
            if (name == null || name.equals("") || containsOnlyWhitespace()) {
                Alert empty = new Alert(Alert.AlertType.ERROR);
                empty.setTitle("Error");
                empty.setHeaderText("Empty Field");
                empty.setContentText("Please enter a valid name. You must press the 'enter' "
                    + "key to save the name.");
                empty.showAndWait();
            }
        }
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static String getName() {
        return name;
    }

    public static String getGameDifficulty() {
        return gameDifficulty;
    }

    public static String getWeapon() {
        return weapon;
    }

    private boolean containsOnlyWhitespace() {

        int counter = 0;

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                counter++;
            }
        }

        return counter == name.length();

    }

    public static void reset() {
        name = null;
        gameDifficulty = null;
        weapon = null;
        enterPressed = false;
    }

}
