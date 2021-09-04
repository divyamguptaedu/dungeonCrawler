import characters.Player;
import controllers.GameConfigController;
import controllers.GameController;
import controllers.Main;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.util.WaitForAsyncUtils;
import rooms.*;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class M4Testing extends ApplicationTest {

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
    public void testNoFightMovementEast() {
        write("Testing East Movement without Fighting");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        // going to the North room from room 0;
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates before invalid move;
        int h1 = game.getCurrHeight();
        int r1 = game.getCurrRow();
        int c1 = game.getCurrCol();
        // going to the next room i.e. East room without fighting;
        clickOn("East Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates after invalid move;
        int h2 = game.getCurrHeight();
        int r2 = game.getCurrRow();
        int c2 = game.getCurrCol();
        // getting room name labels;
        String nameBeforeInValidMovement = "Room0 (" + h1 + ", " + r1 + ", " + c1 + ")";
        String nameAfterInValidMovement = "Room0 (" + h2 + ", " + r2 + ", " + c2 + ")";
        // comparing labels before and after movement - should be same;
        assertEquals(nameBeforeInValidMovement, nameAfterInValidMovement);
    }

    @Test
    public void testNoFightMovementWest() {
        write("Testing West Movement without Fighting");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        // going to the North room from room 0;
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates before invalid move;
        int h1 = game.getCurrHeight();
        int r1 = game.getCurrRow();
        int c1 = game.getCurrCol();
        // going to the next room i.e. West room without fighting;
        clickOn("West Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates after invalid move;
        int h2 = game.getCurrHeight();
        int r2 = game.getCurrRow();
        int c2 = game.getCurrCol();
        // getting room name labels;
        String nameBeforeInValidMovement = "Room0 (" + h1 + ", " + r1 + ", " + c1 + ")";
        String nameAfterInValidMovement = "Room0 (" + h2 + ", " + r2 + ", " + c2 + ")";
        // comparing labels before and after movement - should be same;
        assertEquals(nameBeforeInValidMovement, nameAfterInValidMovement);
    }

    @Test
    public void testNoFightMovementNorth() {
        write("Testing North Movement without Fighting");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        // going to the North room from room 0;
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates before invalid move;
        int h1 = game.getCurrHeight();
        int r1 = game.getCurrRow();
        int c1 = game.getCurrCol();
        // going to the next room i.e. North room without fighting;
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates after invalid move;
        int h2 = game.getCurrHeight();
        int r2 = game.getCurrRow();
        int c2 = game.getCurrCol();
        // getting room name labels;
        String nameBeforeInValidMovement = "Room0 (" + h1 + ", " + r1 + ", " + c1 + ")";
        String nameAfterInValidMovement = "Room0 (" + h2 + ", " + r2 + ", " + c2 + ")";
        // comparing labels before and after movement - should be same;
        assertEquals(nameBeforeInValidMovement, nameAfterInValidMovement);
    }

    @Test
    public void testNoFightMovementSouth() {
        write("Testing South Movement without Fighting");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        // going to the North room from room 0;
        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates before invalid move;
        int h1 = game.getCurrHeight();
        int r1 = game.getCurrRow();
        int c1 = game.getCurrCol();
        // going to the previous room i.e. South room without fighting;
        clickOn("South Exit");
        WaitForAsyncUtils.waitForFxEvents();
        // saving coordinates after invalid move;
        int h2 = game.getCurrHeight();
        int r2 = game.getCurrRow();
        int c2 = game.getCurrCol();
        // getting room name labels;
        String nameBeforeInValidMovement = "Room0 (" + h1 + ", " + r1 + ", " + c1 + ")";
        String nameAfterInValidMovement = "Room0 (" + h2 + ", " + (r2 - 1) + ", " + c2 + ")";
        // comparing labels before and after movement - should be same;
        assertEquals(nameBeforeInValidMovement, nameAfterInValidMovement);
    }

    @Test
    public void playerHealthDecreasedTest() {
        write("Player Health Decreases");
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

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        int initialPlayerHealth = game.getPlayer().getHealth();

        clickOn("Attack");
        WaitForAsyncUtils.waitForFxEvents();

        int newPlayerHealth = game.getPlayer().getHealth();

        boolean healthDecreased;

        if (newPlayerHealth < initialPlayerHealth) {
            healthDecreased = true;
        } else {
            healthDecreased = false;
        }

        assertEquals(true, healthDecreased);
    }


    @Test
    public void monsterHealthDecreasedTest() {

        write("Monster Health Decreases");
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

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        int initialMonsterHealth = game.getMonster().getHealth();

        clickOn("Attack");
        WaitForAsyncUtils.waitForFxEvents();

        int newMonsterHealth = game.getMonster().getHealth();

        boolean healthDecreased;

        if (newMonsterHealth < initialMonsterHealth) {
            healthDecreased = true;
        } else {
            healthDecreased = false;
        }

        assertEquals(true, healthDecreased);
    }

    @Test
    public void healthsAfterAttackTest() {

        write("One Character Dead");
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

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Attack");
        WaitForAsyncUtils.waitForFxEvents();

        boolean characterDead;

        if (game.getPlayer().getHealth() <= 0 || game.getMonster().getHealth() <= 0) {
            characterDead = true;
        } else {
            characterDead = false;
        }

        assertEquals(true, characterDead);

    }

    @Test
    public void testMonsterKilled() {
        write("Killing A Monster");
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

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        int monsterHealth = game.getMonster().getHealth();

        clickOn("Attack");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(true, game.getMonster().isDead());
    }

    @Test
    public void testDamagedMonsterNotDead() {
        write("Damaging a Monster Doesn't Kill It");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radHard");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        //navigating to an exit
        GameController game = GameConfigController.getGameController();

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        game.getPlayer().attackMethod(game.getMonster());
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(false, game.getMonster().isDead());
    }

    @Test
    public void testMonsterGen() throws IOException {
        write("Test Monster Generation");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radHard");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController gc = GameConfigController.getGameController();

        Room[][][] dungeon = gc.getDungeon();

        boolean hasMonsters = true;
        int startRow = gc.getCurrRow();
        int startCol = gc.getCurrCol();
        int startHeight = gc.getCurrHeight();

        for (int h = 0; h < dungeon.length; h++) {
            for (int r = 0; r < dungeon[h].length; r++) {
                for (int c = 0; c < dungeon[h][r].length; c++) {
                    if (dungeon[h][r][c] != null) {
                        boolean empty = dungeon[h][r][c].getMonsters().isEmpty();
                        if (empty && r != startRow && c != startCol && h != startHeight) {
                            hasMonsters = false;
                        }
                    }
                }
            }
        }

        assertEquals(true, hasMonsters);

    }

    @Test
    public void testPlayerDeath() throws IOException {
        write("Test Player Death");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radHard");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController gc = GameConfigController.getGameController();

        Player player = gc.getPlayer();
        player.setHealth(1);

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("Attack");
        WaitForAsyncUtils.waitForFxEvents();

        Scene restart = Main.getMainInstance().getPrimaryStage().getScene();
        FxAssert.verifyThat(window(restart), WindowMatchers.isShowing());
    }
}
