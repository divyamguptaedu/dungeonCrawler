package rooms;

import characters.Monster;
import controllers.Direction;
import controllers.GameController;
import items.Droppable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChallengeRoom extends Room {

    private static final int ROWS = 9;
    private static final int COLS = 9;

    private boolean challenged;

    public ChallengeRoom(String name, int spawn, GameController gc) {
        super(ROWS, COLS, name, spawn, gc);
        super.setGameController(gc);

        challenged = false;

        init();
    }

    @Override
    public void init() {
        clearLayout();

        Button exitN = new Button("North Exit");
        exitN.setOnAction(e -> gameController.exitRoom(Direction.NORTH));

        Button exitE = new Button("East Exit");
        exitE.setOnAction(e -> gameController.exitRoom(Direction.EAST));

        Button exitS = new Button("South Exit");
        exitS.setOnAction(e -> gameController.exitRoom(Direction.SOUTH));

        Button exitW = new Button("West Exit");
        exitW.setOnAction(e -> gameController.exitRoom(Direction.WEST));

        Button exitD = new Button("Descend");
        exitD.setOnAction(e -> gameController.exitRoom(Direction.DOWN));

        Button chal = new Button("Challenge");
        chal.setOnAction(e -> {
            challenged = true;
            try {
                gameController.displayRoom(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        layout[4][0] = exitW;
        layout[8][4] = exitS;
        layout[4][8] = exitE;
        layout[0][4] = exitN;
        layout[4][4] = exitD;
        layout[5][4] = chal;
    }

    public void update() {
        StackPane spane = new StackPane();
        if (challenged) {
            // Jank code. Fix if there is time.
            while (monsters.size() > 0 && monsters.get(0).isDead())
                monsters.remove(0);

            if (monsters.size() > 0) {
                Monster m = monsters.get(0);
                spane.getChildren().addAll(m.getSprite(), new Text("HP: " + m.getHealth()));
            } else {
                // Below line was a temporary drop which did nothing
                // spane.getChildren().add(new Rectangle(20, 20, Color.CYAN));

                // Reset Player health + provide extra health
                gameController.getPlayer().setHealth(50000);
            }
        }
        layout[3][4] = spane;
    }

    public boolean isChallenged() {
        return challenged;
    }

}
