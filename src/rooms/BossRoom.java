package rooms;

import characters.Monster;
import characters.Monster1;
import characters.Monster2;
import characters.Monster3;
import characters.Monsters;
import controllers.Direction;
import controllers.GameController;
import controllers.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class BossRoom extends Room {

    private static final int ROWS = 9;
    private static final int COLS = 9;
    private static Button endGame;

    public BossRoom(String name, int spawn, GameController gc) {
        super(ROWS, COLS, name, spawn, gc);
        super.setGameController(gc);
        init();
    }

    @Override
    public void init() {
        clearLayout();

//        Button win = new Button("Beat the boss.");
//        win.setOnAction(e -> {
//            Stage winScreen = new Stage();
//            StackPane root = new StackPane();
//            root.getChildren().add(new Text("You beat the boss!"));
//            winScreen.setTitle("You win!");
//            winScreen.setScene(new Scene(root, 400, 400));
//            winScreen.show();
//        });
//
//        layout[4][4] = win;
//        layout[4][0] = new Text(getName());

        endGame = new Button("End Game");
        endGame.setVisible(false);
        endGame.setOnAction(e -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/EndScreen.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Main.getMainInstance().getPrimaryStage().getScene().setRoot(root);
        });

        for (Monster m : monsters) {
            if (!m.isDead()) {
                layout[3][4] = m.getSprite();
            }
        }
        update();
    }

    public static void endGameVisible(Boolean visibility) {
        endGame.setVisible(true);
    }

}
