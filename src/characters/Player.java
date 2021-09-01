package characters;

import controllers.GameController;
import items.AttackPotion;
import items.Droppable;
import items.HealthPotion;
import items.Shield;
import items.Weapon;

public class Player {

    private int health;
    private final int row;
    private final int col;
    private int attackBoost;
    private Weapon weapon;
    //set these values temporarily for testing inventory processes, change as needed
    private int healthPQuantity = 0;
    private int attackPQuantity = 0;
    private final AttackPotion aPotion;
    private final HealthPotion hPotion;
    private final Shield shield;
    private boolean useAttack;
    private boolean useHealth;

    private int damageDone;

    private final GameController gameController;

    public Player(int health, int row, int col, String weapon, GameController gc) {
        this.health = health;
        this.row = row;
        this.col = col;
        this.gameController = gc;
        this.weapon = new Weapon(weapon, gameController);
        aPotion = new AttackPotion(gameController);
        hPotion = new HealthPotion(gameController);
        shield = new Shield(gameController);
        shield.setShieldHealth(0);
    }

    public void pickUpdate(Droppable d) {
        if (d instanceof Weapon) {
            weapon.setName(d.getName());
        } else if (d instanceof AttackPotion) {
            attackPQuantity++;
        } else if (d instanceof HealthPotion) {
            healthPQuantity++;
        } else {
            shield.setShieldHealth(((Shield) d).getShieldHealth());
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int val) {
        health = val;
    }

    public boolean getUseAttack() {
        return useAttack;
    }

    public void setUseAttack(boolean val) {
        useAttack = val;
    }

    public boolean getUseHealth() {
        return useHealth;
    }

    public void setUseHealth(boolean val) {
        useHealth = val;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = new Weapon(weapon, gameController);
    }

    public int getAttackBoost() {
        return attackBoost;
    }

    public void setAttackBoost(int nv) {
        attackBoost = nv;
    }

    public int attackMethod(Monster target) { //add parameters boolean ap, boolean hp
        if (useAttack) {
            attackPQuantity--;
            aPotion.execute();
        }
        if (useHealth) {
            healthPQuantity--;
            hPotion.execute();
        }
        int nval = target.getHealth() - weapon.getDamageValue() - attackBoost;
        damageDone = weapon.getDamageValue() + attackBoost;
        attackBoost = 0;
        target.setHealth(nval);
        useHealth = false;
        useAttack = false;
        return damageDone;
    }

    public int getDamageDone() {
        return damageDone;
    }

    public int getHealthPQuantity() {
        return healthPQuantity;
    }

    public int getAttackPQuantity() {
        return attackPQuantity;
    }

    public Shield getShield() {
        return shield;
    }

}
