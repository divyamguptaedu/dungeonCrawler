package items;

import controllers.GameController;
import javafx.scene.Node;

public abstract class Potion implements Droppable {

    private final String name;
    private Node sprite;
    private boolean pickedUp;
    protected GameController gameController;

    public Potion(String name, GameController gc) {
        this.name = name;
        pickedUp = false;
        gameController = gc;
    }

    abstract void execute();

    public String getName() {
        return name;
    }

    public void setSprite(Node sprite) {
        this.sprite = sprite;
    }

    public Node getSprite() {
        return sprite;
    }

    public boolean pickedUp() {
        return pickedUp;
    }

    public void pickUp() {
        pickedUp = true;
    }

}
