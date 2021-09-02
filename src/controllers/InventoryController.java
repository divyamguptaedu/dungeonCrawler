package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.IOException;


public class InventoryController {

    @FXML
    private Label lblHealthPQuantity;
    @FXML
    private Label lblAttackPQuantity;
    @FXML
    private Label lblWeaponType;
    @FXML
    private Label lblShieldPrompt;
    @FXML
    private RadioButton radUseHealthPotion;
    @FXML
    private RadioButton radUseAttackPotion;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblShieldStatusDisplay;

    private static GameController gameController;
    //These 3 seem irrelevant. Can use the gameController's player's getter methods.
    private boolean healthPotionSelected;
    private boolean attackPotionSelected;
    private String weaponType;

    //ADDED AS THERE WAS NO PLACE TO INITIALISE GAMECONTROLLER, FEEL FREE TO CHANGE.
    //Called in the GameController constructor.
    public static void setGameController(GameController temp) {
        gameController = temp;
    }

    @FXML
    private void onDisplayInventoryClicked(ActionEvent actionEvent) {
        lblHealthPQuantity.setText(gameController.getPlayer().getHealthPQuantity() + "");
        lblAttackPQuantity.setText(gameController.getPlayer().getAttackPQuantity() + "");
        lblWeaponType.setText(gameController.getPlayer().getWeapon().getName());

        if (gameController.getPlayer().getShield().getShieldHealth() > 0) {
            lblShieldStatusDisplay.setText("Active");
        } else {
            lblShieldStatusDisplay.setText("Not Active");
        }

    }

    @FXML
    private void onBackClicked(ActionEvent actionEvent) throws IOException {
        gameController.displayRoom(gameController.getCurrRoom());
    }

    @FXML
    private void useHealthPotionSelected(ActionEvent actionEvent) {
        int rem = gameController.getPlayer().getHealthPQuantity();
        if (rem == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You don't have any health potion!");
            alert.show();
            radUseHealthPotion.setSelected(false);
        } else {
            gameController.getPlayer().setUseHealth(true);
            healthPotionSelected = true;
        }
    }

    @FXML
    private void useAttackPotionSelected(ActionEvent actionEvent) {
        int rem = gameController.getPlayer().getAttackPQuantity();
        if (rem == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You don't have any attack potion!");
            alert.show();
            radUseAttackPotion.setSelected(false);
        } else {
            gameController.getPlayer().setUseAttack(true);
            attackPotionSelected = true;
        }
    }

    public boolean isHealthPotionSelected() {
        return healthPotionSelected;
    }

    public boolean isAttackPotionSelected() {
        return attackPotionSelected;
    }

    public String getWeaponType() {
        return weaponType;
    }

}
