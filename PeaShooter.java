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

public class PeaShooter {
    private Rectangle peaShooterHitbox;
    private LinkedList<PeaProjectile> listOfPeas;
    private LinkedList<Zombie> myListOfZombies;
    private int peaShooterHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
    public PeaShooter(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.peaShooterHealth = 4000;
        this.peaShooterHitbox = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.peaShooterHitbox.setStroke(Color.BLACK);
        this.peaShooterHitbox.setFill(Color.RED);
        root.getChildren().add(this.peaShooterHitbox);
        this.listOfPeas = new LinkedList<>();
        this.setUpPeaShootingTimeline(X, Y, root);
    }

    private void setUpPeaShootingTimeline(int X, int Y, Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.millis(1000), (ActionEvent e) -> this.generatePea(X, Y, root));
        KeyFrame kf2 = new KeyFrame(Duration.millis(20), (ActionEvent e) -> this.deletePeasOutOfBounds(root));
        KeyFrame kf3 = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.movePeas());

        this.timeline1 = new Timeline(kf1);
        this.timeline2 = new Timeline(kf2);
        this.timeline3 = new Timeline(kf3);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline3.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
        this.timeline2.play();
        this.timeline3.play();
    }
    public void assignCorrespondingListOfZombies(LinkedList<Zombie> ListOfZombies){
        this.myListOfZombies = ListOfZombies;
    }

    private void generatePea(int X, int Y, Pane root){
        if (!this.myListOfZombies.isEmpty()) {
            PeaProjectile pea = new PeaProjectile(X + Constants.LAWN_WIDTH + Constants.PEA_RADIUS, Y + (Constants.LAWN_WIDTH / 2), root);
            this.listOfPeas.add(pea);
        }

    }
    private void movePeas(){
        for (PeaProjectile currentPea : this.listOfPeas){
            currentPea.move(2);
        }
    }
    private void deletePeasOutOfBounds(Pane root) {
        if (!this.listOfPeas.isEmpty()) {
            PeaProjectile furthestPea = this.listOfPeas.getFirst();
            if (furthestPea.checkOutOfBounds()) {
                furthestPea.removeGraphic(root);
                this.listOfPeas.remove(furthestPea);
            }
        }
    }
    public int getX(){
        return (int) this.peaShooterHitbox.getX();
    }
    public int getY(){
        return (int) this.peaShooterHitbox.getY();
    }

    public LinkedList<PeaProjectile> getPeaList(){
        return this.listOfPeas;
    }
    public void checkHealth(Pane root){
        System.out.println(this.peaShooterHealth);
        this.peaShooterHealth = this.peaShooterHealth - 10;
        if (this.peaShooterHealth == 0) {
            this.removePeaShooter(root);
        }
    }
    private void removePeaShooter(Pane root){
        this.timeline1.stop();
        this.timeline2.stop();
        this.timeline3.stop();
        root.getChildren().remove(this.peaShooterHitbox);
        this.myLawn.deletePlant(this);
    }

}
