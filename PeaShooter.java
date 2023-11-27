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
    private LinkedList<NormalZombie> myListOfZombies;
    private int myCost;

    public PeaShooter(int X, int Y, Pane root){
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

        Timeline timeline1 = new Timeline(kf1);
        Timeline timeline2 = new Timeline(kf2);
        Timeline timeline3 = new Timeline(kf3);
        timeline1.setCycleCount(Animation.INDEFINITE);
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline3.setCycleCount(Animation.INDEFINITE);
        timeline1.play();
        timeline2.play();
        timeline3.play();
    }
    public void assignCorrespondingListOfZombies(LinkedList<NormalZombie> ListOfZombies){
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

    public LinkedList<PeaProjectile> getPeaList(){
        return this.listOfPeas;
    }
    public int getMyCost(){
        return this.myCost;
    }
}
