package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controller for the Overlay fxml file.
 */
public class Overlay {

    private static GameController gameController;

    @FXML
    private Label lblGoldDisplay;
    @FXML
    private Label shieldDisplay;
    @FXML
    private Label healthDisplay;
    @FXML
    private Button attack;
    @FXML
    private Button inventory;

    private static final String DIFF = GameConfigController.getGameDifficulty();
    private static int gold;
    private static int health;
    private static int shield;

    @FXML
    private void initialize() {
        switch (DIFF.toUpperCase()) {
        case "EASY":
            gold = 10000;
            health = 40000;
            shield = 0;
            break;
        case "MEDIUM":
            gold = 1000;
            health = 40000;
            shield = 0;
            break;
        case "HARD":
            gold = 100;
            health = 40000;
            shield = 0;
            break;
        default:
            System.out.println("Invalid difficulty.");
            break;
        }
        lblGoldDisplay.setText(String.valueOf(gold));
        healthDisplay.setText("Player HP: " + health);
        shieldDisplay.setText("Shield HP: " + shield);
    }

    public void updateGold(int gold) {
        Overlay.gold = gold;
        lblGoldDisplay.setText(String.valueOf(gold));
    }

    public void updateStats() {
        health = gameController.getPlayer().getHealth();
        shield = gameController.getPlayer().getShield().getShieldHealth();
        healthDisplay.setText("Player HP: " + health);
        shieldDisplay.setText("Shield HP: " + Math.max(0, shield));
    }

    public void setGameController(GameController gc) {
        gameController = gc;
    }

    @FXML
    private void onAttackClicked() throws IOException {
        gameController.attack();
    }

    @FXML
    private void onInventoryClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Inventory.fxml"));
        Main.getMainInstance().getPrimaryStage().getScene().setRoot(root);
    }

}