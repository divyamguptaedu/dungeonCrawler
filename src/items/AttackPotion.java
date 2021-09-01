package items;

import characters.Player;
import controllers.GameController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AttackPotion extends Potion {

    private final int attackBoost = 50;

    public AttackPotion(GameController gc) {
        super("attack", gc);
        ImageView sprite =
            new ImageView(new Image(getClass().getResourceAsStream("/images/ATKPot.png")));
        sprite.setFitHeight(50);
        sprite.setFitWidth(50);
        sprite.setOnMouseClicked((MouseEvent e) -> {
            try {
                gameController.pickUp(this);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        setSprite(sprite);
    }

    @Override
    public void execute() {
        Player player = gameController.getPlayer();
        player.setAttackBoost(attackBoost);
    }

}
