//ALL WEAPON OBJECTS ARE CREATED IN THE PLAYER CLASS USING THE STRINGS THAT ARE PASSED
//TO THE SET WEAPON FUNCTION OR THE PLAYER CONSTRUCTOR.
//Change weapon damage values in the constants and nowhere else.
package items;

import controllers.GameController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Weapon implements Droppable {

    private String name;
    private int damageValue;
    private boolean pickedUp;
    private Node sprite;
    private static final int RIFLEDAMAGE = 300;
    private static final int BAZOOKADAMAGE = 500;
    private static final int SWORDDAMAGE = 200;

    private final GameController gameController;

    public Weapon(String name, GameController gc) {
        this.name = name;
        gameController = gc;

        switch (name.toLowerCase()) {
        case "rifle":
            this.damageValue = RIFLEDAMAGE;
            break;
        case "bazooka":
            this.damageValue = BAZOOKADAMAGE;
            break;
        default:
            this.damageValue = SWORDDAMAGE;
        }
        pickedUp = false;

        ImageView imageView =
            new ImageView(new Image(getClass().getResourceAsStream("/images/" + name + ".png")));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked((MouseEvent e) -> {
            try {
                gameController.pickUp(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        sprite = imageView;
    }

    public Weapon(String name, int damageValue, GameController gc) {
        this.name = name;
        this.damageValue = damageValue;
        gameController = gc;
    }

    public String getName() {
        return name;
    }

    public int getDamageValue() {
        return damageValue;
    }

    public void setName(String name) {
        this.name = name;
        switch (name.toLowerCase()) {
        case "rifle":
            this.damageValue = RIFLEDAMAGE;
            break;
        case "bazooka":
            this.damageValue = BAZOOKADAMAGE;
            break;
        default:
            this.damageValue = SWORDDAMAGE;
        }
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    @Override
    public Node getSprite() {
        return sprite;
    }

    @Override
    public boolean pickedUp() {
        return pickedUp;
    }

    public void pickUp() {
        pickedUp = true;
    }

}
