package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.LinkedList;

/**
 * Zombie superclass. Is inherited by all zombie classes. Manages graphical and logical components of Zombies.
 */
public class Zombie {
    /**
     * 6 instance variables are declared. zombieHitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. zombieHealth represents the health of the zombie. myListOfPlants is a LinkedList of
     * Plants that is assigned to each zombie, so it can know of its existence. One timeline is declared, and a boolean
     * amWalking is declared. Lawn is declared for association.
     */
    private ImageView zombieHitbox;
    private int zombieHealth;
    private LinkedList<Plant> myListOfPlants;
    private Timeline timeline1;
    private boolean amWalking;
    private Lawn myLawn;

    /**
     * Main constructor of Zombie. Sets boolean amWalking to true. Graphically instantiates zombie and sets up the
     * zombie walking timeline. Also instantiates myListOfPlants.
     * @param Y Y coordinate at which the zombie will be generated
     * @param root passed to graphically add zombie
     * @param lawn passed for association.
     */
    public Zombie(int Y, Pane root, Lawn lawn){
        this.myLawn = lawn;
        this.amWalking = true;
        this.zombieHitbox = new ImageView(new Image("indy/Normal_Zombie.png"));
        this.zombieHitbox.setFitWidth(Constants.ZOMBIE_WIDTH);
        this.zombieHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.zombieHitbox.setX(Constants.SCENE_WIDTH);
        this.zombieHitbox.setY(Y);
        root.getChildren().add(this.zombieHitbox);
        this.setUpWalkingTimeline();
        this.myListOfPlants = new LinkedList<>();
    }

    /**
     * This method changes the zombieHitbox image based on a string path.
     * @param path string path
     */
    public void setZombieColor(String path){
        this.zombieHitbox.setImage(new Image(path));
    }

    /**
     * this method changes the zombieHealth based on integer
     * @param health desired initial zombie health
     */
    public void setZombieHealth(int health){
        this.zombieHealth = health;
    }

    /**
     * this method sets up the zombie walking timeline.
     */
    private void setUpWalkingTimeline(){
        KeyFrame kf1 = new KeyFrame(Duration.millis(40), (ActionEvent e) -> this.walk());
        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
    }

    /**
     * this method assigns the zombie with its corresponding list of plants.
     * @param listOfPlants all plants that are on zombie's row.
     */
    public void assignCorrespondingListOfPlants(LinkedList<Plant> listOfPlants){
        this.myListOfPlants = listOfPlants;
    }

    /**
     * this method moves the zombie.
     */
    private void walk(){
        this.zombieHitbox.setX(this.zombieHitbox.getX() - 1);
    }

    /**
     * getter/accessor method. Returns Zombie's Y position.
     * @return Zombie's y coordinate
     */
    public int getY(){
        return (int) this.zombieHitbox.getY();
    }

    /**
     * getter/accessor method. Returns Zombie's X position
     * @return Zombie's x coordinate
     */
    public int getX(){
        return (int) this.zombieHitbox.getX();
    }

    /**
     * this method checks the zombie's health. If the zombie's health is depleted, it will be removed logically and
     * graphically.
     * @param root passed to graphically remove zombie
     * @param ListOfZombies passed to logically remove zombie
     * @param damage damage inflicted on zombie
     */
    public void checkHealth(Pane root, LinkedList<Zombie> ListOfZombies, int damage) {
        this.zombieHealth = this.zombieHealth - damage;
        if (this.zombieHealth <= 0) {
            this.removeZombie(root);
            ListOfZombies.remove(this);
            this.myLawn.addCount(root);
        }
    }

    /**
     * getter/accessor method that returns a zombie's list of plants.
     * @return myListOfPlants
     */
    public LinkedList<Plant> getMyListOfPlants(){
        return this.myListOfPlants;
    }

    /**
     * this method removes the zombie graphically
     * @param root passed to remove zombie graphically
     */
    private void removeZombie(Pane root) {
        root.getChildren().remove(this.zombieHitbox);
    }

    /**
     * this method checks if the zombie collided.
     * @param X x coordinate of colliding node
     * @param Y y coordinate of colliding node
     * @param Width width of colliding node
     * @param Height height of colliding node
     * @return true for collision detected. False for no collision
     */
    public boolean didCollide(int X, int Y, int Width, int Height){
        return this.zombieHitbox.intersects(X, Y, Width, Height);
    }

    /**
     * this method stops the zombie from walking
     */
    public void stopWalking(){
        this.timeline1.stop();
        this.amWalking = false;
    }

    /**
     * this method resumes the zombie walking
     */
    public void resumeWalking(){
        if (!this.amWalking){
            this.timeline1.play();
        }
    }

    /**
     * this method stops the timeline.
     */
    public void stopTimeline(){
        this.timeline1.stop();
    }

    /**
     * this method resumes the timeline.
     */
    public void playTimeline(){
        this.timeline1.play();
    }


}

