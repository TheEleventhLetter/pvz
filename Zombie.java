package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;

public class Zombie {
    private Rectangle zombieHitbox;
    private int zombieHealth;

    public Zombie(int Y, Pane root){
        this.zombieHitbox = new Rectangle(Constants.SCENE_WIDTH, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
        root.getChildren().add(this.zombieHitbox);
        this.setUpWalkingTimeline();
    }
    public void setZombieColor(Color color){
        this.zombieHitbox.setFill(color);
    }
    public void setZombieHealth(int health){
        this.zombieHealth = health;
    }
    private void setUpWalkingTimeline(){
        KeyFrame kf = new KeyFrame(Duration.millis(40), (ActionEvent e) -> this.walk());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void walk(){
        this.zombieHitbox.setX(this.zombieHitbox.getX() - 2);
    }
    public int getY(){
        return (int) this.zombieHitbox.getY();
    }
    public int getX(){
        return (int) this.zombieHitbox.getX();
    }
    public void checkHealth(Pane root, LinkedList<Zombie> ListOfZombies) {
        this.zombieHealth = this.zombieHealth - 1;
        if (this.zombieHealth == 0) {
            this.removeZombie(root);
            ListOfZombies.remove(this);
        }
    }

    private void removeZombie(Pane root) {
        root.getChildren().remove(this.zombieHitbox);
    }
}

