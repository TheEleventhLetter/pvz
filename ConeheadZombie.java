package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ConeheadZombie extends Zombie {
    public ConeheadZombie(int Y, Pane root, Lawn lawn){
        super(Y, root, lawn);
        super.setZombieColor("indy/Conehead_Zombie.png");
        super.setZombieHealth(Constants.CONE_HEAD_ZOMBIE_HEALTH);
    }
}
