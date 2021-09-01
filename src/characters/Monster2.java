package characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Monster2 extends Monster {

    private static final int ATTACK_VALUE = 200;
    private static final int HEALTH = 2000;
    private int row;
    private int col;

    public Monster2(int row, int col) {
        super(ATTACK_VALUE, HEALTH, row, col);

        Shape sprite = new Circle(20, Color.BLUE);
        setSprite(sprite);
    }

}
