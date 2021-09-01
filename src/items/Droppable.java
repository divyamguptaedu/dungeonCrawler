package items;

import javafx.scene.Node;

public interface Droppable {

    String getName();

    Node getSprite();

    void pickUp();

    boolean pickedUp();

}
