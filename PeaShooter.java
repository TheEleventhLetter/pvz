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
 * PeaShooter Class. Manages all logical and graphical components of the PeaShooter plant.
 */

public class PeaShooter implements Plant {
    /**
     * Creates 8 instance variables. The PeaShooter Hitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. An LinkedLIst of Zombies is created to keep track of all zombies on the PeaShooter's
     * row. An integer is declared to represent the PeaShooter's health. Lawn is set up for association. Three timelines
     * are created as well.
     */
    private ImageView peaShooterHitbox;
    private LinkedList<PeaProjectile> listOfPeas;
    private LinkedList<Zombie> myListOfZombies;
    private int peaShooterHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;

    /**
     * main constructor of peaShooter class. Sets up peaShooter graphically as well as instantiating plant health.
     * Additionally, instantiates LinkedList listOfPeas to logically keep track of all peas. Finally, sets up timeline
     * to shoot peas.
     * @param X X position of plant when generated
     * @param Y Y position of plant when generated
     * @param lawn passed for association
     * @param root passed to graphically add PeaShooter.
     */
    public PeaShooter(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.peaShooterHealth = Constants.PEASHOOTER_HEALTH;
        this.peaShooterHitbox = new ImageView(new Image("indy/Peashooter_Sprite.png"));
        this.peaShooterHitbox.setFitWidth(Constants.LAWN_WIDTH);
        this.peaShooterHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.peaShooterHitbox.setX(X);
        this.peaShooterHitbox.setY(Y);
        root.getChildren().add(this.peaShooterHitbox);
        this.listOfPeas = new LinkedList<>();
        this.setUpPeaShootingTimeline(X, Y, root);
    }

    /**
     * This method sets up the timelines for the PeaShooter Plant. There are three different timelines, all with different
     * intervals of time to run certain methods. One generates the pea, one checks for peas out of bounds, and one
     * is responsible for moving the peas.
     * @param X determines X position of generated pea
     * @param Y determines Y position of generated pea
     * @param root passed to graphically add or remove pea.
     */
    private void setUpPeaShootingTimeline(int X, int Y, Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.millis(1000), (ActionEvent e) -> this.generatePea(X, Y, root));
        KeyFrame kf2 = new KeyFrame(Duration.millis(20), (ActionEvent e) -> this.deletePeasOutOfBounds(root));
        KeyFrame kf3 = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.movePeas(root));

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
     * Since the peaShooter targets only zombies in its row, it only needs to know about zombies in its respective row
     * and therefore extracts only the LinkedList of Zombies of its row to use as its own list of zombies.
     * @param ListOfZombies ArrayList of linked list of zombies passed from Lawn.
     */
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        this.myListOfZombies = ListOfZombies.get(this.myLawn.pixelToRow((int) this.peaShooterHitbox.getY()));
    }

    /**
     * Method is responsible for generating pea. Only generates pea if plant's row of zombies contains zombie. If no
     * zombies are present, no peas are generated.
     * @param X determines X position of generated pea
     * @param Y determines Y position of generated pea
     * @param root passed to graphically add pea.
     */
    private void generatePea(int X, int Y, Pane root){
        if (!this.myListOfZombies.isEmpty()) {
            PeaProjectile pea = new PeaProjectile(X + Constants.LAWN_WIDTH + Constants.PEA_RADIUS, Y + (Constants.LAWN_WIDTH / 2), root);
            this.listOfPeas.add(pea);
        }
    }

    /**
     * method moves pea. For every pea that is in the list of peas, the pea is moved 2 pixels to the right. It then
     * checks if there were any intersections/collisions with the peas and zombies.
     * @param root passed to graphically handle elements
     */
    private void movePeas(Pane root){
        for (PeaProjectile currentPea : this.listOfPeas){
            currentPea.move(2);
        }
        this.checkPeaZombieIntersection(root);
    }

    /**
     * Method deletes any peas that are out of bounds. It will ask every pea if it is out of bounds. If so, the method
     * deletes the pea graphically and logically
     * @param root passed to graphically remove pea.
     */
    private void deletePeasOutOfBounds(Pane root) {
        if (!this.listOfPeas.isEmpty()) {
            PeaProjectile furthestPea = this.listOfPeas.getFirst();
            if (furthestPea.checkOutOfBounds()) {
                furthestPea.removeGraphic(root);
                this.listOfPeas.remove(furthestPea);
            }
        }
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's X position.
     * @return PeaShooter Plant's X position
     */
    @Override
    public int getX(){
        return (int) this.peaShooterHitbox.getX();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's Y position.
     * @return PeaShooter Plant's Y position
     */
    @Override
    public int getY(){
        return (int) this.peaShooterHitbox.getY();
    }

    /**
     * this method is part of the Plant interface. It is meant to check the health of the plant.
     * if the health is 0, the plant is removed.
     * @param root passed to graphically remove plant
     */
    @Override
    public void checkHealth(Pane root){
        this.peaShooterHealth = this.peaShooterHealth - 10;
        if (this.peaShooterHealth == 0) {
            this.removePlant(root);
        }
    }

    /**
     * this method is part of the Plant interface. It is meant to remove the plant. It will remove every single pea
     * graphically and logically. It then removes the plant logically and graphically.
     * @param root passed to remove peas and plant graphically
     */
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        for (int i = 0; i < this.listOfPeas.size(); i++) {
            this.listOfPeas.get(i).removeGraphic(root);
        }
        root.getChildren().remove(this.peaShooterHitbox);
        this.myLawn.deletePlant(this);
    }

    /**
     * Method checks if any peas have intersected with any zombies. For every pea in the list of peas, the method will
     * check if the pea has collided with a zombie that is part of the plant's list of zombies. If so, the method will
     * remove the pea logically and graphically, while telling the hit zombie to check its health with a certain damage
     * inflicted from the pea. The method will only run if there exists a pea and a zombie.
     * @param root passed to graphically remove pea.
     */
    private void checkPeaZombieIntersection(Pane root) {
        if (!this.listOfPeas.isEmpty()) {
            if (!this.myListOfZombies.isEmpty()) {
                for (int k = 0; k < this.listOfPeas.size(); k++) {
                    for (int z = 0; z < this.myListOfZombies.size(); z++) {
                        PeaProjectile currentPea = this.listOfPeas.get(k);
                        Zombie currentZombie = this.myListOfZombies.get(z);
                        if (currentPea.didCollide(currentZombie.getX(), currentZombie.getY())) {
                            currentPea.removeGraphic(root);
                            this.listOfPeas.remove(currentPea);
                            currentZombie.checkHealth(root, this.myListOfZombies, 1);
                            if (this.listOfPeas.isEmpty()) {
                                break;
                            }
                        }
                    }

                }
            }
        }
    }




}
