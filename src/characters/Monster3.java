package characters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Monster3 extends Monster {

    private static final int ATTACK_VALUE = 300;
    private static final int HEALTH = 3000;
    private int row;
    private int col;

    public Monster3(int row, int col) {
        super(ATTACK_VALUE, HEALTH, row, col);

        Shape sprite = new Circle(30, Color.GREEN);
        setSprite(sprite);
    }

}
