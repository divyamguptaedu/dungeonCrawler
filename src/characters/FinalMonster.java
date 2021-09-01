package characters;

import items.Shield;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class FinalMonster extends Monster{

    private static final int ATTACK_VALUE = 1000;
    private static final int HEALTH = 10000;            //Can change to smaller value for testing.
    private int row;
    private int col;

    public FinalMonster(int row, int col) {
        super(ATTACK_VALUE, HEALTH, row, col);
        Shape sprite = new Circle(50, Color.DARKGREY);
        setSprite(sprite);
    }
    @Override
    public void attackMethod(Player playerToAttack) {
        playerToAttack.getShield().setShieldHealth(0);
        int currPlayerHealth = playerToAttack.getHealth();
        playerToAttack.setHealth(currPlayerHealth - ATTACK_VALUE);
    }
}
