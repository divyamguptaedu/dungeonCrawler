package rooms;

import controllers.Direction;
import controllers.GameController;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Room0 extends Room {

    private static final int ROWS = 9;
    private static final int COLS = 9;

    public Room0(String name, int spawn, GameController gc) {
        super(ROWS, COLS, name, spawn, gc);
        super.setGameController(gc);

        init();
    }

    @Override
    public void init() {
        clearLayout();

        Button exitN = new Button("North Exit");
        exitN.setOnAction(e -> {
            gameController.exitRoom(Direction.NORTH);
        });

        Button exitE = new Button("East Exit");
        exitE.setOnAction(e -> {
            gameController.exitRoom(Direction.EAST);
        });

        Button exitS = new Button("South Exit");
        exitS.setOnAction(e -> {
            gameController.exitRoom(Direction.SOUTH);
        });

        Button exitW = new Button("West Exit");
        exitW.setOnAction(e -> {
            gameController.exitRoom(Direction.WEST);
        });

        layout[4][0] = exitW;
        layout[8][4] = exitS;
        layout[4][8] = exitE;
        layout[0][4] = exitN;
        layout[4][4] = new Text(getName());

        update();
    }

}
