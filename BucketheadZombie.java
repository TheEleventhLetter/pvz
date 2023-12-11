package indy;

import javafx.scene.layout.Pane;

/**
 * BucketHead Zombie class. Inherits the zombie superclass but changes the image path and health.
 */
public class BucketheadZombie extends Zombie{
    public BucketheadZombie(int Y, Pane root, Lawn lawn){
        super(Y, root, lawn);
        super.setZombieColor("indy/Buckethead_Zombie.png");
        super.setZombieHealth(Constants.BUCKET_HEAD_ZOMBIE_HEALTH);
    }
}
