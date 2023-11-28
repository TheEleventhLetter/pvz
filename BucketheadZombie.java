package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BucketheadZombie extends Zombie{
    public BucketheadZombie(int Y, Pane root){
        super(Y, root);
        super.setZombieColor(Color.SILVER);
        super.setZombieHealth(8);
    }
}
