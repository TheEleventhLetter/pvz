package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * SunFlower class. Manages all graphical and logical components of SunFlower Plant.
 */

public class SunFlower implements Plant{
    /**
     * Creates 4 instance variables. The SunFlowerHitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. Lawn is set up for association. One timeline is created, as well as an integer to
     * represent the Sunflower's Health.
     */
    private ImageView sunFlowerHitbox;
    private Lawn myLawn;
    private int sunFlowerHealth;
    private Timeline timeline1;

    /**
     * main constructor of SunFlower Class. Graphically instantiates SunFlower and sets up sunFlower timeline.
     * @param X determines X coordinate of sunFlower when generated
     * @param Y determines Y coordinate of sunFlower when generated
     * @param lawn set up for association
     * @param root passed to graphically add sunFlower
     * @param myGame set up for association (passed to sun class)
     */
    public SunFlower(int X, int Y, Lawn lawn, Pane root, Game myGame){
        this.myLawn = lawn;
        this.sunFlowerHealth = Constants.SUNFLOWER_HEALTH;
        this.sunFlowerHitbox = new ImageView(new Image("indy/Sunflower_Sprite.png"));
        this.sunFlowerHitbox.setFitWidth(Constants.LAWN_WIDTH);
        this.sunFlowerHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.sunFlowerHitbox.setX(X);
        this.sunFlowerHitbox.setY(Y);
        root.getChildren().add(this.sunFlowerHitbox);
        this.setUpSunFlowerTimeline(X, Y, root, myGame);
    }

    /**
     * method sets up main timeline in sunFlower class. In intervals of 7 seconds, calls to generateSun.
     * @param X determines x coordinate of sun when generated
     * @param Y determines y coordinate of sun when generated
     * @param root passed to graphically add sun
     * @param myGame passed for association into sun class
     */
    private void setUpSunFlowerTimeline(int X, int Y, Pane root, Game myGame){
        KeyFrame kf1 = new KeyFrame(Duration.seconds(7), (ActionEvent e) -> this.generateSun(X, Y, root, myGame));

        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.playTimeline();
    }

    /**
     * method generates sun. Chooses a random location within the sunFlower's lawn square to generate a sun. Adds the
     * sun logically through the game class.
     * @param X determines x-coordinate of origin of lawn square (top-left corner)
     * @param Y determines y-coordinate of origin of lawn square (top-left corner)
     * @param root passed to graphically add sun
     * @param myGame passed into sun class for association.
     */
    private void generateSun(int X, int Y, Pane root, Game myGame){
        int randX = ThreadLocalRandom.current().nextInt(X, X + Constants.LAWN_WIDTH);
        int randY = ThreadLocalRandom.current().nextInt(Y, Y + Constants.LAWN_WIDTH);
        Sun newSun = new Sun(randX, randY, root, myGame);
        myGame.addToSunList(newSun);
    }

    /**
     * this method is part of the plant interface. Because the sunflower is not an attacking plant, it does nothing
     * with the zombies.
     * @param ListOfZombies totalZombies from lawn class
     */
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        LinkedList<Zombie> myListOfZombies = ListOfZombies.get(this.myLawn.pixelToRow((int) this.sunFlowerHitbox.getY()));
    }
    /**
     * this method is part of the Plant interface. It is meant to play/start the timelines.
     */
    @Override
    public void playTimeline(){
        this.timeline1.play();
    }
    /**
     * this method is part of the Plant interface. It is meant to stop the timelines.
     */
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's X position.
     * @return SunFlowers Plant's X position
     */
    @Override
    public int getX(){
        return (int) this.sunFlowerHitbox.getX();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's Y position.
     * @return SunFlowers Plant's Y position
     */
    @Override
    public int getY(){
        return (int) this.sunFlowerHitbox.getY();
    }
    /**
     * this method is part of the Plant interface. It is meant to check the health of the plant.
     * if the health is 0, the plant is removed.
     * @param root passed to graphically remove plant
     */
    @Override
    public void checkHealth(Pane root){
        this.sunFlowerHealth = this.sunFlowerHealth - 10;
        if (this.sunFlowerHealth == 0) {
            this.removePlant(root);
        }
    }
    /**
     * this method is part of the Plant interface. It is meant to remove the plant. It removes the plant logically and
     * graphically.
     * @param root passed to remove plant graphically
     */
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        root.getChildren().remove(this.sunFlowerHitbox);
        this.myLawn.deletePlant(this);
    }
}
