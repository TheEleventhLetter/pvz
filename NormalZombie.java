package indy;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class NormalZombie extends Zombie{
    public NormalZombie(int Y, Pane root){
        super(Y, root);
        super.setZombieColor(Color.GRAY);
        super.setZombieHealth(4);
    }


}


