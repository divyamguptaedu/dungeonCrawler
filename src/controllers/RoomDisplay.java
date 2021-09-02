package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import rooms.Room;

import java.io.IOException;

public class RoomDisplay {

    @FXML
    private Parent overlay;

    @FXML
    private Overlay overlayController;

    @FXML
    private GridPane grid;

    @FXML
    private void initialize() throws IOException {

    }

    public void loadRoom(Room room) {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        Node[][] nodes = room.getLayout();
        int cols = room.getCols();
        int rows = room.getRows();

        for (int i = 0; i < cols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / cols);
            grid.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            grid.getRowConstraints().add(rowConst);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (nodes[r][c] != null) {
                    grid.add(nodes[r][c], c, r);
                }
            }
        }
    }

    public GridPane getGrid() {
        return grid;
    }

    public Overlay getOverlayController() {
        return overlayController;
    }

}
