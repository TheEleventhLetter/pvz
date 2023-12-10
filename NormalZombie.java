package indy;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class NormalZombie extends Zombie{
    public NormalZombie(int Y, Pane root, Lawn lawn){
        super(Y, root, lawn);
        super.setZombieColor(Color.GRAY);
        super.setZombieHealth(Constants.NORMAL_ZOMBIE_HEALTH);
    }


}


