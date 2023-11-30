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
import java.util.concurrent.ThreadLocalRandom;

public class SunFlower implements Plant{
    private Rectangle sunFlowerHitbox;
    private LinkedList<Zombie> myListOfZombies;
    private int sunFlowerHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    public SunFlower(int X, int Y, Lawn lawn, Pane root, Game myGame){
        System.out.println("SunFlower Created");
        this.myLawn = lawn;
        this.sunFlowerHealth = 2000;
        this.sunFlowerHitbox = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.sunFlowerHitbox.setStroke(Color.BLACK);
        this.sunFlowerHitbox.setFill(Color.LIGHTYELLOW);
        root.getChildren().add(this.sunFlowerHitbox);
        this.setUpSunFlowerTimeline(X, Y, root, myGame);
    }
    private void setUpSunFlowerTimeline(int X, int Y, Pane root, Game myGame){
        KeyFrame kf1 = new KeyFrame(Duration.millis(5000), (ActionEvent e) -> this.generateSun(X, Y, root, myGame));

        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.playTimeline();
    }
    private void generateSun(int X, int Y, Pane root, Game myGame){
        int randX = ThreadLocalRandom.current().nextInt(X, X + Constants.LAWN_WIDTH);
        int randY = ThreadLocalRandom.current().nextInt(Y, Y + Constants.LAWN_WIDTH);
        new Sun(randX, randY, root, myGame);
    }
    @Override
    public void assignCorrespondingListOfZombies(LinkedList<Zombie> ListOfZombies){
        this.myListOfZombies = ListOfZombies;
    }
    @Override
    public void playTimeline(){
        this.timeline1.play();
    }
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
    }
    @Override
    public int getX(){
        return (int) this.sunFlowerHitbox.getX();
    }
    @Override
    public int getY(){
        return (int) this.sunFlowerHitbox.getY();
    }
    @Override
    public void checkHealth(Pane root){
        this.sunFlowerHealth = this.sunFlowerHealth - 10;
        if (this.sunFlowerHealth == 0) {
            this.removeSunFlower(root);
        }
    }
    private void removeSunFlower(Pane root){
        this.stopTimeline();
        root.getChildren().remove(this.sunFlowerHitbox);
        this.myLawn.deletePlant(this);
    }
}
