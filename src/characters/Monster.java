package characters;

import items.Shield;
import javafx.scene.Node;

public abstract class Monster {

    private Node sprite;
    private int attackValue;
    private int health;
    private int row;
    private int col;
    private boolean dead;

    public Monster(int attackValue, int health, int row, int col) {
        this.attackValue = attackValue;
        this.health = health;
        this.row = row;
        this.col = col;
        dead = false;
    }

    public void attackMethod(Player playerToAttack) {
        int currPlayerHealth = playerToAttack.getHealth();
        Shield s = playerToAttack.getShield();
        if (s.getShieldHealth() > 0) {
            double dmgModifier = Shield.DAMAGE_MODIFIER;
            int newHealth = s.getShieldHealth() - (int) (attackValue * dmgModifier);
            s.setShieldHealth(newHealth);
        } else {
            playerToAttack.setHealth(currPlayerHealth - attackValue);
        }
    }

    // getters
    public int getAttackValue() {
        return attackValue;
    }

    public int getHealth() {
        return health;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isDead() {
        return dead;
    }

    public Node getSprite() {
        return sprite;
    }

    //setters
    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setSprite(Node sprite) {
        this.sprite = sprite;
    }

}
