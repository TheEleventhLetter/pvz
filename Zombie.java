package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.LinkedList;

public class Zombie {
    private ImageView zombieHitbox;
    private int zombieHealth;
    private LinkedList<Plant> myListOfPlants;
    private Timeline timeline1;
    private boolean amWalking;
    private Lawn myLawn;

    public Zombie(int Y, Pane root, Lawn lawn){
        this.myLawn = lawn;
        this.amWalking = true;
        this.zombieHitbox = new ImageView(new Image("indy/Normal_Zombie.png"));
        this.zombieHitbox.setFitWidth(Constants.ZOMBIE_WIDTH);
        this.zombieHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.zombieHitbox.setX(Constants.SCENE_WIDTH);
        this.zombieHitbox.setY(Y);
        root.getChildren().add(this.zombieHitbox);
        this.setUpWalkingTimeline(root);
        this.myListOfPlants = new LinkedList<>();
    }
    public void setZombieColor(String path){
        this.zombieHitbox.setImage(new Image(path));
    }
    public void setZombieHealth(int health){
        this.zombieHealth = health;
    }
    private void setUpWalkingTimeline(Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.millis(40), (ActionEvent e) -> this.walk());
        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
    }
    public void assignCorrespondingListOfPlants(LinkedList<Plant> listOfPlants){
        this.myListOfPlants = listOfPlants;
    }
    private void walk(){
        this.zombieHitbox.setX(this.zombieHitbox.getX() - 1);
    }
    public int getY(){
        return (int) this.zombieHitbox.getY();
    }
    public int getX(){
        return (int) this.zombieHitbox.getX();
    }
    public void checkHealth(Pane root, LinkedList<Zombie> ListOfZombies, int damage) {
        this.zombieHealth = this.zombieHealth - damage;
        if (this.zombieHealth <= 0) {
            this.removeZombie(root);
            ListOfZombies.remove(this);
            this.myLawn.addCount(root);
        }
    }

    public LinkedList<Plant> getMyListOfPlants(){
        return this.myListOfPlants;
    }

    private void removeZombie(Pane root) {
        root.getChildren().remove(this.zombieHitbox);
    }
    public boolean didCollide(int X, int Y, int Width, int Height){
        return this.zombieHitbox.intersects(X, Y, Width, Height);
    }
    public void stopWalking(){
        this.timeline1.stop();
        this.amWalking = false;
    }
    public void resumeWalking(){
        if (!this.amWalking){
            this.timeline1.play();
        }
    }
    public void stopTimeline(){
        this.timeline1.stop();
    }
    public void playTimeline(){
        this.timeline1.play();
    }


}

