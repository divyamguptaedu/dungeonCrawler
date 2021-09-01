package characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Monster1 extends Monster {

    private static final int ATTACK_VALUE = 100;
    private static final int HEALTH = 1000;
    private int row;
    private int col;

    public Monster1(int row, int col) {
        super(ATTACK_VALUE, HEALTH, row, col);

        Shape sprite = new Circle(10, Color.RED);
        setSprite(sprite);
    }

}
