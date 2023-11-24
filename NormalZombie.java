package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class NormalZombie {
    private Rectangle zombieHitbox;

    public NormalZombie(int Y, Pane root){
        this.zombieHitbox = new Rectangle(Constants.SCENE_WIDTH, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
        this.zombieHitbox.setFill(Color.GRAY);
        root.getChildren().add(this.zombieHitbox);
        this.setUpWalkingTimeline();
    }
    private void setUpWalkingTimeline(){
        KeyFrame kf = new KeyFrame(Duration.millis(20), (ActionEvent e) -> this.walk());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void walk(){
        this.zombieHitbox.setX(this.zombieHitbox.getX() - 2);
    }
}

