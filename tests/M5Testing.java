import controllers.GameConfigController;
import controllers.GameController;
import controllers.Main;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.awt.*;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.scene.Node;

public class M5Testing extends ApplicationTest {

    private Main main;

    @Override
    public void start(Stage primaryStage) throws Exception {
        main = new Main();
        main.start(primaryStage);
    }

    @Before
    public void setup() throws IOException {
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testSpriteDisplayMonsterDead() {
        write("Test Monster Dead");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        while (!game.getMonster().isDead()) {
            clickOn("Attack");
        }

        Node sprite = (game.getCurrRoom().getMonsters().get(0).getSprite());
        assertEquals(true, sprite.isVisible());
    }

    @Test
    public void testSpriteDisplayMonsterAlive() {
        write("Test Monster Alive");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        Node sprite = (game.getCurrRoom().getMonsters().get(0).getSprite());
        assertEquals(false, !sprite.isVisible());
    }

    @Test
    public void testInventoryClick() throws InterruptedException {
        write("Enter Inventory");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        //navigating to an exit
        GameController game = GameConfigController.getGameController();

        clickOn("Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnDisplayInventory");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("Back", NodeMatchers.isNotNull());
    }

    @Test
    public void testWeaponSwordDisplay() throws InterruptedException {
        write("Inventory Sword Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        //navigating to an exit
        GameController game = GameConfigController.getGameController();

        clickOn("Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnDisplayInventory");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblWeaponType",
                LabeledMatchers.hasText("Sword"));
    }

    @Test
    public void testWeaponRifleDisplay() throws InterruptedException {
        write("Inventory Rifle Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#rifle");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnDisplayInventory");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblWeaponType",
            LabeledMatchers.hasText("Rifle"));
    }

    @Test
    public void testWeaponBazookaDisplay() throws InterruptedException {
        write("Inventory Bazooka Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnDisplayInventory");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblWeaponType",
            LabeledMatchers.hasText("Bazooka"));
    }


    @Test
    public void testPlayerShieldDisplay() throws InterruptedException {
        write("Player Shield Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        verifyThat("#shieldDisplay",
                LabeledMatchers.hasText("Shield HP: "
                        + game.getPlayer().getShield().getShieldHealth()));

    }

    @Test
    public void testInventoryShieldDisplay() throws InterruptedException {
        write("Inventory Shield Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("#inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnDisplayInventory");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblShieldStatusDisplay",
                LabeledMatchers.hasText("Not Active"));
    }

    @Test
    public void testHPAlert() {

        write("HP Alert");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("#inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Display Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radUseHealthPotion");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("OK", NodeMatchers.isVisible());
    }

    @Test
    public void testAPAlert() {

        write("AP Alert");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("#inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Display Inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radUseAttackPotion");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("OK", NodeMatchers.isVisible());
    }


    @Test
    public void testBackButton() {

        write("Back Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("#inventory");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#btnBack");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat(window(game.getRoot().getScene()), WindowMatchers.isShowing());
    }

}
