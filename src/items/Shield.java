package items;

import controllers.GameController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class Shield implements Droppable {

    private final String name;
    private int shieldHealth;
    private boolean pickedUp;
    private final Node sprite;
    public static final double DAMAGE_MODIFIER = 0.7;

    public Shield(GameController gc) {
        this.name = "shield";
        ImageView imageView =
            new ImageView(new Image(getClass().getResourceAsStream("/images/shield.png")));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setOnMouseClicked((MouseEvent e) -> {
            try {
                gc.pickUp(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        sprite = imageView;
        this.shieldHealth = 5000;
    }

    public int getShieldHealth() {
        return shieldHealth;
    }

    public void setShieldHealth(int h) {
        shieldHealth = h;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Node getSprite() {
        return sprite;
    }

    @Override
    public void pickUp() {
        pickedUp = true;
    }

    @Override
    public boolean pickedUp() {
        return pickedUp;
    }
}
