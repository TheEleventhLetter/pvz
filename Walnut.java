package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Walnut implements Plant{
    private Rectangle walnutHitbox;
    private LinkedList<Zombie> myListOfZombies;
    private int walnutHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    public Walnut(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.walnutHealth = 10000;
        this.walnutHitbox = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.walnutHitbox.setStroke(Color.BLACK);
        this.walnutHitbox.setFill(Color.WHEAT);
        root.getChildren().add(this.walnutHitbox);
    }
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        this.myListOfZombies = ListOfZombies.get(this.myLawn.pixelToRow((int) this.walnutHitbox.getY()));
    }
    @Override
    public void playTimeline(){
    }
    @Override
    public void stopTimeline(){
    }
    @Override
    public int getX(){
        return (int) this.walnutHitbox.getX();
    }
    @Override
    public int getY(){
        return (int) this.walnutHitbox.getY();
    }
    @Override
    public void checkHealth(Pane root){
        this.walnutHealth = this.walnutHealth - 10;
        if (this.walnutHealth == 0) {
            this.removePlant(root);
        }
    }
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        root.getChildren().remove(this.walnutHitbox);
        this.myLawn.deletePlant(this);
    }
}
