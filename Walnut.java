package indy;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Walnut class. Manages all graphical and logical components of Walnut Plant.
 */
public class Walnut implements Plant{
    /**
     * Creates 3 instance variables. The WalnutHitbox is a Javafx ImageView that passes in a relative url path to
     * present a graphical image. Lawn is set up for association, as well as an integer to represent the Walnut's Health.
     */
    private ImageView walnutHitbox;
    private int walnutHealth;
    private Lawn myLawn;

    /**
     * main constructor of Walnut Class. Graphically instantiates Walnut.
     * @param X determines X position of walnut when generated
     * @param Y determines Y position of walnut when generated
     * @param lawn set up for association
     * @param root passed to graphically add walnut
     */
    public Walnut(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.walnutHealth = Constants.WALNUT_HEALTH;
        this.walnutHitbox = new ImageView(new Image("indy/Walnut_Sprite.png"));
        this.walnutHitbox.setFitWidth(Constants.LAWN_WIDTH);
        this.walnutHitbox.setFitHeight(Constants.LAWN_WIDTH);
        this.walnutHitbox.setX(X);
        this.walnutHitbox.setY(Y);
        root.getChildren().add(this.walnutHitbox);
    }

    /**
     * this method is part of the plant interface. Because the walnut is not an attacking plant, it does nothing
     * with the zombies.
     * @param ListOfZombies totalZombies from lawn class
     */
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        LinkedList<Zombie> myListOfZombies = ListOfZombies.get(this.myLawn.pixelToRow((int) this.walnutHitbox.getY()));
    }
    /**
     * this method is part of the Plant interface. It is meant to play/start the timelines. No timeline in walnut class
     */
    @Override
    public void playTimeline(){
    }
    /**
     * this method is part of the Plant interface. It is meant to stop the timelines. No timeline in walnut class
     */
    @Override
    public void stopTimeline(){
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's X position.
     * @return Walnut Plant's X position
     */
    @Override
    public int getX(){
        return (int) this.walnutHitbox.getX();
    }
    /**
     * this method is part of the Plant interface. It is meant to return the plant's Y position.
     * @return Walnut Plant's Y position
     */
    @Override
    public int getY(){
        return (int) this.walnutHitbox.getY();
    }
    /**
     * this method is part of the Plant interface. It is meant to check the health of the plant.
     * if the health is 0, the plant is removed.
     * @param root passed to graphically remove plant
     */
    @Override
    public void checkHealth(Pane root){
        this.walnutHealth = this.walnutHealth - 10;
        if (this.walnutHealth == 0) {
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
        root.getChildren().remove(this.walnutHitbox);
        this.myLawn.deletePlant(this);
    }
}
