import controllers.GameConfigController;
import controllers.GameController;
import controllers.Main;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
import rooms.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;

public class M3Testing extends ApplicationTest {

    private Main main;

    //private GameController game;

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
    public void testDungeonSize() throws IOException {

        write("SizeTest");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = new GameController();

        boolean checkRows = false;
        boolean checkCols = false;
        boolean checkHeight = false;

        System.out.println("Map rows: " + game.getMap().length);
        System.out.println("Dungeon rows: " + game.getDungeon().length);
        if (game.getMap().length == game.getDungeon().length) {
            checkRows = true;
        }

        System.out.println("Map rows: " + game.getMap()[0].length);
        System.out.println("Dungeon rows: " + game.getDungeon()[0].length);
        if (game.getMap()[0].length == game.getDungeon()[0].length) {
            checkCols = true;
        }

        System.out.println("Map rows: " + game.getMap()[0][0].length);
        System.out.println("Dungeon rows: " + game.getDungeon()[0][0].length);
        if (game.getMap()[0][0].length == game.getDungeon()[0][0].length) {
            checkHeight = true;
        }

        assertEquals(true, checkRows);
        assertEquals(true, checkCols);
        assertEquals(true, checkHeight);

    }

    @Test
    public void testMoveToDescend() throws IOException {

        write("Testing moveTo()");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        Platform.runLater(new Runnable() {
            @Override public void run() {
                try {
                    game.moveTo(0, 0, 6);
                    WaitForAsyncUtils.waitForFxEvents();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        verifyThat("Descend", NodeMatchers.isNotNull());

    }

    @Test
    public void testDescendButton() throws IOException {

        //starting a game

        write("DescendTest");
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

        int[] exitCoordinates = findExitCoordinates(game);
        int exitRow = exitCoordinates[0];
        int exitCol = exitCoordinates[1];

        Platform.runLater(new Runnable() {
            @Override public void run() {
                try {
                    game.moveTo(0, exitCoordinates[0], exitCoordinates[1]);
                    WaitForAsyncUtils.waitForFxEvents();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        verifyThat("Descend", NodeMatchers.isNotNull());

        //clicking descend button
        clickOn("Descend");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, game.getCurrHeight());
        System.out.println("test concluded");
    }

    private static int[] findExitCoordinates(GameController game) {
        for (int i = 0; i < game.getMap().length; i++) {
            for (int j = 0; j < game.getMap()[0].length; j++) {
                if (game.getMap()[0][i][j] == '!') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }


    @Test
    public void testRoomCreation() throws IOException {
        write("Room Creation Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = new GameController();

        char[][][] map = game.getMap();
        Room[][][] dungeon = game.getDungeon();

        boolean matching = true;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                for (int k = 0; k < map[i][j].length; k++) {
                    if (map[i][j][k] == '\0') {
                        matching = dungeon[i][j][k] == null;
                    } else if (map[i][j][k] == '*') {
                        matching = dungeon[i][j][k] instanceof Room0;
                    } else if (i == map.length - 1 && map[i][j][k] == '!') {
                        matching = dungeon[i][j][k] instanceof BossRoom;
                    } else {
                        matching = dungeon[i][j][k] instanceof ChallengeRoom;
                    }
                }
            }
        }

        assertEquals(true, matching);
    }

    @Test
    public void testExitDistance() throws IOException {
        write("Exit Distance Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        char[][][] map = game.getMap();

        int exitDist = Integer.MAX_VALUE;
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map[0][i].length; j++) {
                if (map[0][i][j] == '!') {
                    exitDist = 2 * Math.min(exitDist,
                            Math.abs(game.getCurrRow() - i) + Math.abs(game.getCurrCol() - j));
                }
            }
        }

    }

    @Test
    public void testNorth() {
        write("North Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();

        boolean valid = isValidIndex(game, h, r - 1, c);

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();

        if (valid) {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r - 1, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        } else {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        }
    }

    @Test
    public void testSouth() {
        write("South Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();

        boolean valid = isValidIndex(game, h, r + 1, c);

        clickOn("South Exit");
        WaitForAsyncUtils.waitForFxEvents();

        if (valid) {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r + 1, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        } else {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        }
    }

    @Test
    public void testWest() {
        write("West Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();

        boolean valid = isValidIndex(game, h, r, c - 1);

        clickOn("West Exit");
        WaitForAsyncUtils.waitForFxEvents();

        if (valid) {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c - 1, game.getCurrCol());
        } else {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        }
    }

    @Test
    public void testEast() {
        write("East Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();

        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();

        clickOn("East Exit");
        WaitForAsyncUtils.waitForFxEvents();

        boolean valid = isValidIndex(game, h, r, c + 1);
        if (valid) {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c + 1, game.getCurrCol());
        } else {
            assertEquals(h, game.getCurrHeight());
            assertEquals(r, game.getCurrRow());
            assertEquals(c, game.getCurrCol());
        }
    }

    private boolean isValidIndex(GameController game, int h, int r, int c) {
        char[][][] map = game.getMap();
        if (h < map.length && r < map[0].length && c < map[0][0].length) {
            return map[h][r][c] != '.';
        }
        return false;
    }

    @Test
    public void testEastLabel() {
        write("East Label Test ");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();
        String nameAfterValidMovement = "Room0 (" + h + ", " + r + ", " + (c + 1) + ")";
        String nameAfterInValidMovement = "Room0 (" + h + ", " + r + ", " + c + ")";

        clickOn("East Exit");
        WaitForAsyncUtils.waitForFxEvents();
        h = game.getCurrHeight();
        r = game.getCurrRow();
        c = game.getCurrCol();
        Room[][][] room = GameConfigController.getGameController().getDungeon();
        String name = room[h][r][c].getName();

        boolean valid = isValidIndex(game, h, r, c + 1);
        System.out.println(name);
        if (valid) {
            assertEquals(name, nameAfterValidMovement);
        } else {
            assertEquals(name, nameAfterInValidMovement);
        }
    }

    @Test
    public void testNorthLabel() {
        write("North Label Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();
        String nameAfterValidMovement = "Room0 (" + h + ", " + (r - 1) + ", " + c + ")";
        String nameAfterInValidMovement = "Room0 (" + h + ", " + r + ", " + c + ")";

        clickOn("North Exit");
        WaitForAsyncUtils.waitForFxEvents();
        h = game.getCurrHeight();
        r = game.getCurrRow();
        c = game.getCurrCol();
        Room[][][] room = GameConfigController.getGameController().getDungeon();
        String name = room[h][r][c].getName();

        boolean valid = isValidIndex(game, h, r - 1, c);
        System.out.println(name);
        if (valid) {
            assertEquals(name, nameAfterValidMovement);
        } else {
            assertEquals(name, nameAfterInValidMovement);
        }
    }

    @Test
    public void testSouthLabel() {
        write("South Label Test");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();
        String nameAfterValidMovement = "Room0 (" + h + ", " + (r + 1) + ", " + c + ")";
        String nameAfterInValidMovement = "Room0 (" + h + ", " + r + ", " + c + ")";

        clickOn("South Exit");
        WaitForAsyncUtils.waitForFxEvents();
        h = game.getCurrHeight();
        r = game.getCurrRow();
        c = game.getCurrCol();
        Room[][][] room = GameConfigController.getGameController().getDungeon();
        String name = room[h][r][c].getName();

        boolean valid = isValidIndex(game, h, r + 1, c);
        System.out.println(name);
        if (valid) {
            assertEquals(name, nameAfterValidMovement);
        } else {
            assertEquals(name, nameAfterInValidMovement);
        }
    }

    @Test
    public void testWestLabel() {
        write("West Label Test ");
        press(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#radMedium");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#sword");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#startGame");
        WaitForAsyncUtils.waitForFxEvents();

        GameController game = GameConfigController.getGameController();
        int h = game.getCurrHeight();
        int r = game.getCurrRow();
        int c = game.getCurrCol();
        String nameAfterValidMovement = "Room0 (" + h + ", " + r + ", " + (c - 1) + ")";
        String nameAfterInValidMovement = "Room0 (" + h + ", " + r + ", " + c + ")";

        clickOn("West Exit");
        WaitForAsyncUtils.waitForFxEvents();
        h = game.getCurrHeight();
        r = game.getCurrRow();
        c = game.getCurrCol();
        Room[][][] room = GameConfigController.getGameController().getDungeon();
        String name = room[h][r][c].getName();

        boolean valid = isValidIndex(game, h, r, c - 1);
        System.out.println(name);
        if (valid) {
            assertEquals(name, nameAfterValidMovement);
        } else {
            assertEquals(name, nameAfterInValidMovement);
        }
    }

}
