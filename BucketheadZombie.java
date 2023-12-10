package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class BucketheadZombie extends Zombie{
    public BucketheadZombie(int Y, Pane root, Lawn lawn){
        super(Y, root, lawn);
        super.setZombieColor("indy/Buckethead_Zombie.png");
        super.setZombieHealth(Constants.BUCKET_HEAD_ZOMBIE_HEALTH);
    }
}
