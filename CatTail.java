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
import java.util.Iterator;
import java.util.LinkedList;


public class CatTail implements Plant{
    private Rectangle catTailHitbox;
    private LinkedList<ThornProjectile> listOfThorns;
    private ArrayList<LinkedList<Zombie>> totalZombies;
    private int catTailHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
    private boolean isSameZombie;
    private Zombie lastClosestZombie;
    public CatTail(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.catTailHealth = Constants.CATTAIL_HEALTH;
        this.isSameZombie = false;
        this.catTailHitbox = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.catTailHitbox.setStroke(Color.BLACK);
        this.catTailHitbox.setFill(Color.PINK);
        root.getChildren().add(this.catTailHitbox);
        this.listOfThorns = new LinkedList<>();
        this.setUpThornShootingTimeline(X, Y, root);
    }

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
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
        this.timeline2.stop();
        this.timeline3.stop();
    }
    @Override
    public void playTimeline(){
        this.timeline1.play();
        this.timeline2.play();
        this.timeline3.play();
    }
    @Override
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies){
        this.totalZombies = ListOfZombies;
    }

    private void generateThorn(int X, int Y, Pane root) {
        if (this.zombiePresent()) {
            ThornProjectile thorn = new ThornProjectile(X + Constants.LAWN_WIDTH + Constants.PEA_RADIUS, Y + (Constants.LAWN_WIDTH / 2), root);
            this.listOfThorns.add(thorn);
        }
    }
    private boolean zombiePresent(){
        boolean zombiePresent = false;
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            if (!this.totalZombies.get(i).isEmpty()){
                zombiePresent = true;
            }
        }
        return zombiePresent;
    }

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

    public boolean getIsSameZombie(){
        return this.isSameZombie;
    }
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
    @Override
    public int getX(){
        return (int) this.catTailHitbox.getX();
    }
    @Override
    public int getY(){
        return (int) this.catTailHitbox.getY();
    }

    @Override
    public void checkHealth(Pane root){
        this.catTailHealth = this.catTailHealth - 10;
        if (this.catTailHealth == 0) {
            this.removePlant(root);
        }
    }
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        for (int i = 0; i < this.listOfThorns.size(); i++) {
            this.listOfThorns.get(i).removeGraphic(root);
        }
        root.getChildren().remove(this.catTailHitbox);
        this.myLawn.deletePlant(this);
    }
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

/**
 * Iterator<ThornProjectile> thorns = this.listOfThorns.iterator();
 *                 if (thorns.hasNext()) {
 *                     ThornProjectile currentThorn = thorns.next();
 */
