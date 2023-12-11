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
 * Cattail Class. Manages all logical and graphical components of the cattail plant.
 */

public class CatTail implements Plant{
    /**
     * Creates 9 instance variables. The Cattail Hitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. A LinkedList of ThornProjectiles is created to keep track of all thorns. Additionally.
     * an ArrayList of LinkedLIst of Zombies is created to keep track of all zombies. CatTailHealth represents the health
     * of plant. Lawn is set up for association. Three different timelines are created, and a zombie is kept to keep
     * track of the closest zombie.
     */
    private ImageView catTailHitbox;
    private LinkedList<ThornProjectile> listOfThorns;
    private ArrayList<LinkedList<Zombie>> totalZombies;
    private int catTailHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
    private Zombie lastClosestZombie;

    /**
     * Main constructor of the Cattail Plant. Sets up the plant graphically, as well as instantiating the plant's health
     * and list of ThornProjectiles, the Cattail Plant's method of attacking. Additionally, we set up a timeline to
     * control the Cattail's interval of shooting out Thorns.
     * @param X integer that determines X position of Cattail
     * @param Y integer that determines Y position of Cattail
     * @param lawn passed for association
     * @param root passed for graphical representation.
     */
    public CatTail(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.catTailHealth = Constants.CATTAIL_HEALTH;
        this.catTailHitbox = new ImageView(new Image("indy/andy.png"));
        this.catTailHitbox.setFitWidth(Constants.LAWN_WIDTH);
        this.catTailHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.catTailHitbox.setX(X);
        this.catTailHitbox.setY(Y);
        root.getChildren().add(this.catTailHitbox);
        this.listOfThorns = new LinkedList<>();
        this.setUpThornShootingTimeline(X, Y, root);
    }

    /**
     * This method sets up the timelines for the Cattail Plant. There are three different timelines, all with different
     * intervals of time to run certain methods. One generates the thorn, one checks for thorns out of bounds, and one
     * is responsible for moving the thorns.
     * @param X integer that determines the X position of generated Thorn.
     * @param Y integer that determines the Y position of generated Thorn
     * @param root passed for graphical representation.
     */
    private void setUpThornShootingTimeline(int X, int Y, Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.millis(1000), (ActionEvent e) -> this.generateThorn(X, Y, root));
        KeyFrame kf2 = new KeyFrame(Duration.millis(20), (ActionEvent e) -> this.deleteThornsOutOfBounds(root));
        KeyFrame kf3 = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.moveThorns(root));

        this.timeline1 = new Timeline(kf1);
        this.timeline2 = new Timeline(kf2);
        this.timeline3 = new Timeline(kf3);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline3.setCycleCount(Animation.INDEFINITE);
        this.playTimeline();
    }

    /**
     * this method is part of the Plant interface. It is meant to stop the timelines.
     */
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
        this.timeline2.stop();
        this.timeline3.stop();
    }
    /**
     * this method is part of the Plant interface. It is meant to play/start the timelines.
     */
    @Override
    public void playTimeline(){
        this.timeline1.play();
        this.timeline2.play();
        this.timeline3.play();
    }

    /**
     * this method is part of the Plant interface. It is meant to assign the plant with its respective list of zombies.
     * Since the cattail targets all zombies, it needs to know about all zombies, and therefore utilizes the entire
     * arraylist of linked lists of zombies.
     * @param ListOfZombies ArrayList of linked list of zombies passed from Lawn.
     */
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        this.totalZombies = ListOfZombies;
    }

    /**
     * this method is responsible for generating the thorns. If there are zombies present, it will instantiate a new
     * thorn and add it to the plants List of thorns.
     * @param X Cattail's X coordinate. Determines initial X position of generated Thorn.
     * @param Y Cattail's Y coordinate. Determines initial Y position of generated Thorn.
     * @param root passed to graphically handle Thorn.
     */
    private void generateThorn(int X, int Y, Pane root) {
        if (this.zombiePresent()) {
            ThornProjectile thorn = new ThornProjectile(X + Constants.LAWN_WIDTH + Constants.PEA_RADIUS, Y + (Constants.LAWN_WIDTH / 2), root);
            this.listOfThorns.add(thorn);
        }
    }

    /**
     * method determines whether there are any zombies present on the screen right now.
     * @return boolean for whether there are any zombies present on screen.
     */
    private boolean zombiePresent(){
        boolean zombiePresent = false;
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            if (!this.totalZombies.get(i).isEmpty()){
                zombiePresent = true;
            }
        }
        return zombiePresent;
    }

    /**
     * this method is responsible for moving all thorns. For every thorn in the list of thorns, the method calls for
     * the closest zombie, and passes in the zombie's X and Y position to the Thorn. Responsible for resetting
     * the angles of thorns after a zombie has been defeated. Calls for intersection method.
     * @param root passed for graphical handling of thorns.
     */
    private void moveThorns(Pane root){
        for (ThornProjectile currentThorn : this.listOfThorns){
            if (this.findClosestZombie() != null){
                if (this.lastClosestZombie != this.findClosestZombie()){
                    currentThorn.resetAngle();
                }
                currentThorn.home(this.findClosestZombie().getX(), this.findClosestZombie().getY());
            } else {
                currentThorn.move();
            }

        }
        this.lastClosestZombie = this.findClosestZombie();
        this.checkThornZombieIntersection(root);
    }

    /**
     * method computes the closest Zombie and returns it. Compares zombies X position to find closest zombie. Similar
     * to Selection Sort Algorithm.
     * @return Zombie that is closest to House.
     */
    private Zombie findClosestZombie(){
        Zombie closestZombie = null;
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            for (int j = 0; j < this.totalZombies.get(i).size(); j++) {
                Zombie currentZombie = this.totalZombies.get(i).get(j);
                if (closestZombie == null) {
                    closestZombie = currentZombie;
                } else if (currentZombie.getX() < closestZombie.getX()) {
                    closestZombie = currentZombie;
                }
            }
        }
        return closestZombie;
    }

    /**
     * mathod deletes Thorns that are out of bounds from the scene. Iterates through list of thorns and checks if they
     * are out of bounds. If so, removes thorn graphically and logically.
     * @param root passed to graphically handle removal of thorn.
     */
    private void deleteThornsOutOfBounds(Pane root) {
        if (!this.listOfThorns.isEmpty()) {
            for (int i = 0; i < this.listOfThorns.size(); i++){
                ThornProjectile currentThorn = this.listOfThorns.get(i);
                if (currentThorn.checkOutOfBounds()) {
                    currentThorn.removeGraphic(root);
                    this.listOfThorns.remove(currentThorn);
            }
            }
        }
    }

    /**
     * this method is part of the Plant interface. It is meant to return the plant's X position.
     * @return Cattail Plant's X position
     */
    @Override
    public int getX(){
        return (int) this.catTailHitbox.getX();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's Y position.
     * @return Cattail Plant's Y position
     */
    @Override
    public int getY(){
        return (int) this.catTailHitbox.getY();
    }

    /**
     * this method is part of the Plant interface. It is meant to check the health of the plant.
     * if the health is 0, the plant is removed.
     */
    @Override
    public void checkHealth(Pane root){
        this.catTailHealth = this.catTailHealth - 10;
        if (this.catTailHealth == 0) {
            this.removePlant(root);
        }
    }
    /**
     * this method is part of the Plant interface. It is meant to remove the plant. It will remove every single thorn
     * graphically and logically. It then removes the plant logically and graphically.
     */
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        for (int i = 0; i < this.listOfThorns.size(); i++) {
            this.listOfThorns.get(i).removeGraphic(root);
        }
        root.getChildren().remove(this.catTailHitbox);
        this.myLawn.deletePlant(this);
    }

    /**
     * this method is responsible for checking if any thorns have intersected with any zombies. the method iterates
     * through every single thorn in the thorn list and checks if it has collided with any of the zombies on screen.
     * If there was a collision detected, the method tells the zombie hit to check its health and removes the thorn
     * graphically and logically.
     * @param root passed to handle graphical removal of thorn.
     */
    private void checkThornZombieIntersection(Pane root) {
        if (!this.listOfThorns.isEmpty()) {
            if (this.zombiePresent()) {
                for (ThornProjectile currentThorn : new LinkedList<>(this.listOfThorns)) {
                    for (int y = 0; y < Constants.LAWN_ROWS; y++) {
                        for (Zombie currentZombie : new LinkedList<>(this.totalZombies.get(y))) {
                            if (currentThorn.didCollide(currentZombie.getX(), currentZombie.getY())) {
                                currentThorn.removeGraphic(root);
                                this.listOfThorns.remove(currentThorn);
                                currentZombie.checkHealth(root, this.totalZombies.get(y), 1);
                                if (this.listOfThorns.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

