import controllers.Main;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;

/**
 * Currently the test methods in Room0Tests must be run separately due to the fact
 * that the primaryStage persists across test cases, rendering sequential testing
 * impossible.
 */
public class Room0Tests extends ApplicationTest {

    private Main main;

    @Override
    public void start(Stage primaryStage) throws Exception {
        main = new Main();
        main.start(primaryStage);
    }

    @Before
    public void initialize() {
        // Requires resetting the stage.

        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();

        write("Room0Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();
    }

    @After
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
    }

    private void selectDiff(String diff) {

        switch (diff.toUpperCase()) {
        case "EASY":
            clickOn("#radEasy");
            break;
        case "MEDIUM":
            clickOn("#radMedium");
            break;
        case "HARD":
            clickOn("#radHard");
            break;
        default:
            System.out.println("Invalid difficulty.");
            break;
        }
        WaitForAsyncUtils.waitForFxEvents();

    }

    @Test
    public void testGoldDisplay() {

        selectDiff("MEDIUM");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblGoldDisplay",
                LabeledMatchers.hasText(String.valueOf(1000)));

    }

    @Test
    public void testEasyGold() {

        selectDiff("EASY");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblGoldDisplay",
                LabeledMatchers.hasText(String.valueOf(10000)));

    }

    @Test
    public void testMediumGold() {

        selectDiff("MEDIUM");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblGoldDisplay",
                LabeledMatchers.hasText(String.valueOf(1000)));
    }

    @Test
    public void testHardGold() {

        selectDiff("HARD");
        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        verifyThat("#lblGoldDisplay",
                LabeledMatchers.hasText(String.valueOf(100)));

    }

}
