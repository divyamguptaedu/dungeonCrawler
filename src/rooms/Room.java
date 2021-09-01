package rooms;

import characters.FinalMonster;
import characters.Monster;
import characters.Monster1;
import characters.Monster2;
import characters.Monster3;
import characters.Monsters;
import controllers.GameController;
import items.AttackPotion;
import items.Droppable;
import items.HealthPotion;
import items.Shield;
import items.Weapon;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {

    private final int rows;
    private final int cols;
    private final String name;
    private boolean visited;

    protected GameController gameController;
    protected Node[][] layout;
    protected List<Monster> monsters;
    protected List<Droppable> drops;

    protected Room(int r, int c, String name, int spawn, GameController gc) {
        this.rows = r;
        this.cols = c;
        this.name = name;
        visited = false;
        layout = new Node[rows][cols];
        gameController = gc;

        monsters = new ArrayList<>();
        drops = new ArrayList<>();

        for (int i = 0; i < spawn; i++) {
            spawn();
            drop();
        }
    }

    private void spawn() {
        if(name.equalsIgnoreCase("Boss room"))
        {
            monsters.add(new FinalMonster(0, 0));
            return;
        }
        Monsters[] list = Monsters.values();
        Monsters rand = list[(int) (Math.random() * list.length)];

        switch (rand) {
        case MONSTER1:
            monsters.add(new Monster1(0, 0));
            break;
        case MONSTER2:
            monsters.add(new Monster2(0, 0));
            break;
        case MONSTER3:
            monsters.add(new Monster3(0, 0));
            break;
        default:
            break;
        }
    }

    private void drop() {
        String[] list = {"attack", "health", "shield", "sword", "rifle", "bazooka"};
        String rand = list[(int) (Math.random() * list.length)];

        Droppable drop;
        switch (rand) {
        case "attack":
            drop = new AttackPotion(gameController);
            break;
        case "health":
            drop = new HealthPotion(gameController);
            break;
        case "shield":
            drop = new Shield(gameController);
            break;
        default:
            drop = new Weapon(rand, gameController);
            break;
        }

        drops.add(drop);
    }

    public boolean hasLiveMonsters() {
        for (Monster m : monsters) {
            if (!m.isDead())
                return true;
        }
        return false;
    }

    public void update() {
        StackPane spane = new StackPane();
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            Droppable d = drops.get(i);
            if (!m.isDead()) {
                spane.getChildren().addAll(m.getSprite(), new Text("HP: " + m.getHealth()));
            } else if (!d.pickedUp() && !(this instanceof BossRoom)) {
                spane.getChildren().add(d.getSprite());
            }
        }
        layout[3][4] = spane;
    }

    /**
     * Add unique room elements
     */
    public abstract void init();

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public Node[][] getLayout() {
        return layout;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void visit() {
        visited = true;
    }

    public void setGameController(GameController gc) {
        this.gameController = gc;
    }

    protected void clearLayout() {
        layout = new Node[rows][cols];
    }

}
