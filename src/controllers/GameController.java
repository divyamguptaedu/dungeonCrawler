package controllers;

import characters.Monster;
import characters.Player;
import items.Droppable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import rooms.BossRoom;
import rooms.Room;
import rooms.Room0;
import rooms.ChallengeRoom;

import java.io.IOException;

public class GameController {

    public static final int START_ROW = 4;
    public static final int START_COL = 4;
    public static final int DUNGEON_ROWS = 9;
    public static final int DUNGEON_COLS = 9;
    public static final int DUNGEON_HEIGHT = 2;

    private char[][][] map;
    private final Room[][][] dungeon;
    private Position curr;
    private Player player;

    private final Parent root;
    private final DunGen dungeonGen;
    private final RoomDisplay roomDisplay;
    private final Overlay overlay;

    private int roomsVisited;
    private int monstersKilled;
    private int damageDone;

    public GameController() throws IOException {
        map = new char[DUNGEON_HEIGHT][DUNGEON_ROWS][DUNGEON_COLS];
        dungeon = new Room[DUNGEON_HEIGHT + 1][DUNGEON_ROWS][DUNGEON_COLS];

        int seed = (int) (Math.random() * 1000);
        seed = 65; // 723
        System.out.println("Seed: " + seed);

        dungeonGen = new DunGen(DUNGEON_HEIGHT, DUNGEON_ROWS, DUNGEON_COLS,
            START_ROW, START_COL, seed);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Room.fxml"));
        root = loader.load();
        roomDisplay = loader.getController();
        overlay = roomDisplay.getOverlayController();
        overlay.setGameController(this);
        InventoryController.setGameController(this);
        EndScreenController.setGC(this);
        createPlayer(GameConfigController.getGameDifficulty());

        curr = new Position(0, START_ROW, START_COL);

        map = dungeonGen.generateDungeon();
        dungeonGen.printDungeon();

        createDungeon();
    }

    private void createPlayer(String diff) {
        switch (diff) {
        case "EASY":
            break;
        case "MEDIUM":
            break;
        case "HARD":
            break;
        default:
            player =
                new Player(40000, START_ROW, START_COL, GameConfigController.getWeapon(), this);
        }
    }

    /*
    Modify as new rooms get created.
     */
    private void createDungeon() {
        for (int h = 0; h < DUNGEON_HEIGHT; h++) {
            for (int r = 0; r < DUNGEON_ROWS; r++) {
                for (int c = 0; c < DUNGEON_COLS; c++) {
                    if (map[h][r][c] != '\0') {
                        if (map[h][r][c] == '#') {
                            dungeon[h][r][c] = new ChallengeRoom("Challenge Room", 4, this);
                            dungeon[h+1][r][c] = new BossRoom("Boss Room", 1, this);
                        } else if (map[h][r][c] == '!') {
                            dungeon[h][r][c] = new ChallengeRoom("Challenge Room", 4, this);
                        } else {
                            dungeon[h][r][c] = new Room0("Room (" + h + ", " + r
                                + ", " + c + ")", 1, this);
                        }
                    }
                }
            }
        }
        dungeon[0][START_ROW][START_COL] = new Room0("Starting Room", 0, this);
    }

    public void start() throws IOException {
        Main.getMainInstance().getPrimaryStage().setX(300);
        Main.getMainInstance().getPrimaryStage().setY(60);
        displayRoom(dungeon[curr.h][curr.r][curr.c]);
    }

    public void displayRoom(Room room) throws IOException {
        room.update();
        roomDisplay.loadRoom(room);
        if (room.isVisited() != true) {
            roomsVisited++;
        }
        room.visit();
        //Parent root1 = Main.getMainInstance().getPrimaryStage().getScene().getRoot();
        Main.getMainInstance().getPrimaryStage().getScene().setRoot(root);
    }

    /*
     * Get monsters from Rooms (dungeon[h][r][c].getMonsters());
     */
    public void attack() throws IOException {
        Room room = dungeon[curr.h][curr.r][curr.c];
        if (!room.hasLiveMonsters() || (room instanceof ChallengeRoom && !((ChallengeRoom) room).isChallenged())) {
            return;
        }

        Monster monster = room.getMonsters().get(0);

        //System.out.println("Monster Starting Health: " + monster.getHealth());
        //System.out.println("Player Starting Health: " + player.getHealth());
        //System.out.println("**********************************************");

        //while loop used to be here
        player.attackMethod(monster);
        damageDone += player.getDamageDone();
        //System.out.println("New monster health: " + monster.getHealth());
        monster.attackMethod(player);
        //System.out.println("New player health: " + player.getHealth());

        if (monster.getHealth() <= 0) {
            monster.setDead(true);
            monstersKilled++;
            //System.out.println("Monster is dead");
            if (room instanceof BossRoom) {
                //BossRoom.endGameVisible(true);
                Parent origin = FXMLLoader.load(getClass().getResource("/fxml/EndScreen.fxml"));
                Scene endScreen = new Scene(origin);
                Main.changeScene(endScreen);
                /*Stage stage = Main.getMainInstance().getPrimaryStage();
                stage.setScene(endScreen);
                stage.show();*/
                return;
            }
        }

        if (player.getHealth() <= 0) {
            System.out.println("Player is dead");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PlayerDead.fxml"));
            Main.getMainInstance().getPrimaryStage().setScene(new Scene(root, 800, 800));
        } else {
            room.update();
            overlay.updateStats();
            displayRoom(room);
        }
    }

    public void pickUp(Droppable d) throws IOException {
        Room room = dungeon[curr.h][curr.r][curr.c];
        d.pickUp();
        player.pickUpdate(d);
        overlay.updateStats();
        displayRoom(room);
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return dungeon[curr.h][curr.r][curr.c].getMonsters().get(0);
    }

    public boolean exitRoom(Direction dir) {
        Room room = dungeon[curr.h][curr.r][curr.c];

        boolean encounter = room.hasLiveMonsters();
        if (room instanceof ChallengeRoom && encounter)
            encounter = ((ChallengeRoom) room).isChallenged();

        Position next = null;
        switch (dir) {
        case NORTH:
            next = curr.moveNorth();
            break;
        case EAST:
            next = curr.moveEast();
            break;
        case SOUTH:
            next = curr.moveSouth();
            break;
        case WEST:
            next = curr.moveWest();
            break;
        case DOWN:
            next = curr.moveDown();
            break;
        default:
//            System.out.println("Invalid direction.");
            break;
        }

        if (!next.isValid() || dungeon[next.h][next.r][next.c] == null
            || (encounter && !dungeon[next.h][next.r][next.c].isVisited())) {
//            System.out.println("Cannot move in there");
            return false;
        }
//        System.out.println("Moving " + dir);

        try {
            displayRoom(dungeon[next.h][next.r][next.c]);
        } catch (IOException io) {
            System.out.println("IO Exception thrown");
        }
        curr = next;

        printMap(curr.h);
        return true;
    }

    public void moveTo(int h, int r, int c) throws IOException {
        Position next = new Position(h, r, c);
        System.out.println(curr.h + ", " + curr.r + ", " + curr.c);
        System.out.println(next.h + ", " + next.r + ", " + next.c);
        if (next.isValid() && dungeon[h][r][c] != null) {
            curr = next;
            displayRoom(dungeon[h][r][c]);
        }
    }

    public void printMap(int h) {
        System.out.println("Level " + h);
        for (int r = 0; r < DUNGEON_ROWS; r++) {
            for (int c = 0; c < DUNGEON_COLS; c++) {
                if (h == curr.h && r == curr.r && c == curr.c) {
                    System.out.print("@ ");
                } else if (h < DUNGEON_HEIGHT) {
                    if (map[h][r][c] == '\0') {
                        System.out.print(". ");
                    } else {
                        System.out.print(map[h][r][c] + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public char[][][] getMap() {
        return map;
    }

    public Room[][][] getDungeon() {
        return dungeon;
    }

    public int getCurrRow() {
        return curr.r;
    }

    public int getCurrCol() {
        return curr.c;
    }

    public int getCurrHeight() {
        return curr.h;
    }

    public Room getCurrRoom() {
        return dungeon[curr.h][curr.r][curr.c];
    }

    public Parent getRoot() {
        return root;
    }

    public int getRoomsVisited() {
        return roomsVisited;
    }

    public int getMonstersKilled() {
        return monstersKilled;
    }

    public int getDamageDone() {
        return damageDone;
    }

    static class Position {

        private final int h;
        private final int r;
        private final int c;

        public Position(int h, int r, int c) {
            this.h = h;
            this.r = r;
            this.c = c;
        }

        public boolean isValid() {
            return h >= 0 && r >= 0 && c >= 0
                && h <= DUNGEON_HEIGHT && r < DUNGEON_ROWS && c < DUNGEON_COLS;
        }

        public Position moveNorth() {
            return new Position(h, r - 1, c);
        }

        public Position moveEast() {
            return new Position(h, r, c + 1);
        }

        public Position moveSouth() {
            return new Position(h, r + 1, c);
        }

        public Position moveWest() {
            return new Position(h, r, c - 1);
        }

        public Position moveDown() {
            return new Position(h + 1, r, c);
        }

    }

}