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

/**
 * CherryBomb Class. Manages all logical and graphical components of the cherryBomb plant.
 */

public class CherryBomb implements Plant{
    /**
     * creates 5 instance variables. The CherryBomb Hitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. Lawn is set up for association. One timeline is created, as well as an integer to
     * represent the countdown. Finally, an ArrayList of LinkedLIst of Zombies is created to keep track of all zombies
     * in the three (or two) rows of the cherryBomb.
     */
    private ImageView cherryBombHitbox;
    private Lawn myLawn;
    private Timeline timeline1;
    private int explodingCountDown;
    private ArrayList<LinkedList<Zombie>> myListsOfZombies;

    /**
     * Main constructor of the CherryBomb Plant. Sets up the plant graphically, as well as instantiating the plant's health
     * and explosion countdown, the CherryBomb Plant's method of attacking. Additionally, we set up a timeline to
     * control the CheeryBomb countdown.
     * @param X integer that determines the X position of CherryBomb
     * @param Y integer that determines the y position of CherryBomb
     * @param lawn passed for association
     * @param root passed for graphical representation.
     */
    public CherryBomb(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.explodingCountDown = 2;
        this.cherryBombHitbox = new ImageView(new Image("indy/Cherrybomb_Sprite.png"));
        this.cherryBombHitbox.setFitWidth(Constants.LAWN_WIDTH);
        this.cherryBombHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.cherryBombHitbox.setX(X);
        this.cherryBombHitbox.setY(Y);
        root.getChildren().add(this.cherryBombHitbox);
        this.setUpExplodingTimeline(root);
    }
    /**
     * this method is part of the Plant interface. It is meant to assign the plant with its respective list of zombies.
     * Since the CherryBomb can only target zombies in a 3x3 range, it only needs to know about the three rows on, on top,
     * and below. Assigns those rows as plant's lists of zombies.
     * @param ListOfZombies ArrayList of linked list of zombies passed from Lawn.
     */
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        this.myListsOfZombies = new ArrayList<LinkedList<Zombie>>(Constants.LAWN_ROWS);
        int myRow = this.myLawn.pixelToRow((int) this.cherryBombHitbox.getY());
        this.myListsOfZombies.add(ListOfZombies.get(myRow));
        if (myRow - 1 >= 0){
            this.myListsOfZombies.add(ListOfZombies.get(myRow - 1));
        if (myRow + 1 < 5){
            this.myListsOfZombies.add(ListOfZombies.get(myRow + 1));
        }
        }
    }

    /**
     * this method sets up the timeline that counts down the cherryBomb until its explosion.
     * @param root passed to handle graphics of cherryBomb.
     */
    private void setUpExplodingTimeline(Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.seconds(1), (ActionEvent e) -> this.checkTimer(root));
        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.playTimeline();
    }
    /**
     * this method is part of the Plant interface. It is meant to stop the timelines.
     */
    @Override
    public void playTimeline(){
        this.timeline1.play();
    }
    /**
     * this method is part of the Plant interface. It is meant to play/start the timelines.
     */
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's X position.
     * @return Cattail Plant's X position
     */
    @Override
    public int getX(){
        return (int) this.cherryBombHitbox.getX();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's Y position.
     * @return Cattail Plant's Y position
     */
    @Override
    public int getY(){
        return (int) this.cherryBombHitbox.getY();
    }

    /**
     * this method is part of the Plant interface. It is meant to check the health of the plant.
     * however, since CherryBomb has infinite health, nothing happens.
     * @param root part of the interface but never used.
     */
    @Override
    public void checkHealth(Pane root){
        //infinite health so don't decrease health
    }

    /**
     * this method checks the timer. Once the time has reached zero, the plant will explode and be removed.
     * @param root passed to handle graphical removal of CherryBomb.
     */
    private void checkTimer(Pane root){
        this.explodingCountDown--;
        if (this.explodingCountDown == 0){
            this.explode(root);
            this.removePlant(root);
        }
    }

    /**
     * this method is part of the Plant interface. It is meant to remove the plant. It removes the plant logically and graphically.
     * @param root passed to graphically remove Plant.
     */
    @Override
    public void removePlant(Pane root) {
        this.stopTimeline();
        root.getChildren().remove(this.cherryBombHitbox);
        this.myLawn.deletePlant(this);
    }

    /**
     * this method is responsible for explosion and the main attacking method of CherryBomb. First, it checks if any
     * zombies are present to prevent a NullPointerException. Then it will check if any zombies were in its 3x3 explosion
     * radius. If so, those zombies will receive 20 damage.
     * @param root passed to handle the graphical removal of zombies.
     */
    private void explode(Pane root){
        boolean zombiesPresent = false;
        for (int i = 0; i < this.myListsOfZombies.size(); i++){
            if (!this.myListsOfZombies.get(i).isEmpty()){
                zombiesPresent = true;
            }
        if (zombiesPresent){
            for (int j = 0; j < this.myListsOfZombies.size(); j++){
                for (int k = 0; k < this.myListsOfZombies.get(j).size(); k++){
                    Zombie currentZombie = this.myListsOfZombies.get(j).get(k);
                    if (currentZombie.didCollide((int) this.cherryBombHitbox.getX() - Constants.LAWN_WIDTH,
                            (int) this.cherryBombHitbox.getY() - Constants.LAWN_WIDTH, Constants.LAWN_WIDTH*3, Constants.LAWN_WIDTH*3)){
                        currentZombie.checkHealth(root, this.myListsOfZombies.get(j), 20);
                    }

                }
            }
        }

        }
    }
}
