package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ConeheadZombie extends Zombie {
    public ConeheadZombie(int Y, Pane root, Lawn lawn){
        super(Y, root, lawn);
        super.setZombieColor(Color.ORANGE);
        super.setZombieHealth(8);
    }
}
