package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ConeheadZombie extends Zombie {
    public ConeheadZombie(int Y, Pane root){
        super(Y, root);
        super.setZombieColor(Color.ORANGE);
        super.setZombieHealth(8);
    }
}
