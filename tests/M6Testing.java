import controllers.GameConfigController;
import controllers.GameController;
import controllers.Main;
import items.Shield;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;
import rooms.BossRoom;
import rooms.Room;

import java.io.IOException;
import java.nio.file.Watchable;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class M6Testing extends ApplicationTest {

    private Main main;
    private boolean setShield;

    @Override
    public void start(Stage primaryStage) throws Exception {
        main = new Main();
        main.start(primaryStage);
        setShield=false;
    }

    @Before
    public void setup() throws IOException {
        clickOn("#startButton");
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testEndScreen() throws IOException {
        write("Test End Screen Display");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        moveToBoss(game);
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat("View Player Stats", NodeMatchers.isNotNull());

    }

    @Test
    public void testRoomsVisited() throws IOException {
        testEndScreen();
        clickOn("View Player Stats");
        verifyThat("#lblRoomsVisited", LabeledMatchers.hasText("15"));
    }

    @Test
    public void testShieldIneffective() throws IOException {
        write("Shield ineffective");
        setShield=true;
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        moveToBoss(game);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("Attack");
        setShield = false;
        assertEquals(game.getPlayer().getShield().getShieldHealth(), 0);
    }

    @Test
    public void testSpriteDisplayBossMonsterAlive() {
        write("Test Boss Sprite");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        moveToBoss(game);
        WaitForAsyncUtils.waitForFxEvents();

        Node sprite = (game.getCurrRoom().getMonsters().get(0).getSprite());
        assertEquals(true, sprite.isVisible());
    }

    @Test
    public void testSpriteDisplayBossMonsterDead() {
        write("Test Boss Dead");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        moveToBoss(game);
        WaitForAsyncUtils.waitForFxEvents();

        while (!game.getMonster().isDead()) {
            clickOn("Attack");
        }

        Node sprite = (game.getCurrRoom().getMonsters().get(0).getSprite());
        assertEquals(true, sprite.isVisible());
    }

    @Test
    public void testBossHealth() {
        write("Test Boss Health");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        moveToBoss(game);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(10000, game.getCurrRoom().getMonsters().get(0).getHealth());
    }

    @Test
    public void testChallengeLock() {
        write("Test Challenge Lock");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if(i<3) {
                playRoom(game);
            }
        }

        clickOn("Challenge");
        WaitForAsyncUtils.waitForFxEvents();

        Room curr = game.getCurrRoom();

        clickOn("Descend");
        WaitForAsyncUtils.waitForFxEvents();

        Room next = game.getCurrRoom();

        assertSame(curr, next);
    }

    @Test
    public void testChallengeMidLock() {
        write("Test Challenge Mid Lock");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if(i<3) {
                playRoom(game);
            }
        }

        Room curr = game.getCurrRoom();

        clickOn("Challenge");
        WaitForAsyncUtils.waitForFxEvents();

        for (int i = 0; i <= Math.random() * 6; i++)
            clickOn("Attack");

        clickOn("Descend");
        WaitForAsyncUtils.waitForFxEvents();

        Room next = game.getCurrRoom();

        assertSame(curr, next);
    }

    @Test
    public void testChallengeUnlock() {
        write("Test Challenge Unlock");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if(i<3) {
                playRoom(game);
            }
        }

        clickOn("Challenge");
        WaitForAsyncUtils.waitForFxEvents();

        Room curr = game.getCurrRoom();

        playRoom(game);

        clickOn("Descend");
        WaitForAsyncUtils.waitForFxEvents();

        Room next = game.getCurrRoom();

        assertNotSame(curr, next);
    }

    @Test
    public void testChallengeWin() {
        write("Test Challenge Win");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if(i<3) {
                playRoom(game);
            }
        }

        clickOn("Challenge");
        WaitForAsyncUtils.waitForFxEvents();

        playRoom(game);

        assertEquals(game.getPlayer().getHealth(), 50000);
    }

    @Test
    public void testChallengeLoss() {
        write("Test Challenge Loss");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#bazooka");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if (i < 3) {
                playRoom(game);
            }
        }

        clickOn("Challenge");
        WaitForAsyncUtils.waitForFxEvents();

        game.getPlayer().setHealth(1000);

        playRoom(game);

        WaitForAsyncUtils.waitForFxEvents();


        assertTrue(game.getPlayer().getHealth() <= 0);
    }


        private void playRoom(GameController game) {
        while (game.getCurrRoom().hasLiveMonsters() && game.getPlayer().getHealth() > 0) {
            clickOn("Attack");
        }
    }

    private void moveToBoss(GameController game) {
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 4; i++) {
            clickOn("East Exit");
            WaitForAsyncUtils.waitForFxEvents();
            if(i<3) {
                playRoom(game);
            }
        }

        clickOn("Descend");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);
        int j = 69;

        clickOn("West Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        playRoom(game);

        for (int i = 0; i < 3; i++) {
            clickOn("West Exit");
            WaitForAsyncUtils.waitForFxEvents();
            playRoom(game);
        }

        if(setShield)
        {
            game.getPlayer().getShield().setShieldHealth(5000);
        }
        if (game.getPlayer().getHealth() > 0) {
            clickOn("South Exit");
            WaitForAsyncUtils.waitForFxEvents();
            playRoom(game);
        }

        clickOn("South Exit");
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("Descend");
    }

}
