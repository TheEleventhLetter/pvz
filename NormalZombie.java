package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NormalZombie {
    private Rectangle zombieHitbox;

    public NormalZombie(int Y, Pane root){
        this.zombieHitbox = new Rectangle(Constants.SCENE_WIDTH, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
        this.zombieHitbox.setFill(Color.GRAY);
        root.getChildren().add(this.zombieHitbox);
    }
}
