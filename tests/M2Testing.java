import controllers.GameConfigController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class M2Testing extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameConfig.fxml"));
        primaryStage.setTitle("DungeonCrawler");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    @Test
    public void testGameDifficultyDisplayEasy() {

        clickOn("#radEasy");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#lblDisplayGameDifficulty",
                LabeledMatchers.hasText("Game Difficulty Selected: Easy"));

    }

    @Test
    public void testGameDifficultyDisplayMedium() {

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#lblDisplayGameDifficulty",
                LabeledMatchers.hasText("Game Difficulty Selected: Medium"));

    }

    @Test
    public void testGameDifficultyDisplayHard() {

        clickOn("#radHard");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#lblDisplayGameDifficulty",
                LabeledMatchers.hasText("Game Difficulty Selected: Hard"));

    }

    @Test
    public void testCorrectNameInput() {

        write("alexis");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();
        String name = GameConfigController.getName();
        verifyThat("#lblDisplayPlayerName",
                LabeledMatchers.hasText("Player name: " + name));

    }

    @Test
    public void testWeaponSword() {

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#weaponChoice", LabeledMatchers.hasText("Weapon: Sword"));

    }

    @Test
    public void testWeaponRifle() {

        clickOn("#rifle");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#weaponChoice", LabeledMatchers.hasText("Weapon: Rifle"));

    }

    @Test
    public void testWeaponBazooka() {

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("#weaponChoice", LabeledMatchers.hasText("Weapon: Bazooka"));

    }

    @Test
    public void testNameBackend() {
        write("alexis");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("alexis", GameConfigController.getName());
    }

    @Test
    public void testGameDifficultyBackend() {
        clickOn("#radEasy");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Easy", GameConfigController.getGameDifficulty());

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Medium", GameConfigController.getGameDifficulty());

        clickOn("#radHard");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Hard", GameConfigController.getGameDifficulty());
    }

    @Test
    public void testWeaponBackend() {
        clickOn("#rifle");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Rifle", GameConfigController.getWeapon());

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Sword", GameConfigController.getWeapon());

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Bazooka", GameConfigController.getWeapon());
    }
}
