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

import java.util.ArrayList;
import java.util.LinkedList;

public class PeaShooter implements Plant {
    private ImageView peaShooterHitbox;
    private LinkedList<PeaProjectile> listOfPeas;
    private LinkedList<Zombie> myListOfZombies;
    private int peaShooterHealth;
    private Lawn myLawn;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
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
        this.myListOfZombies = ListOfZombies.get(this.myLawn.pixelToRow((int) this.peaShooterHitbox.getY()));
    }

    private void generatePea(int X, int Y, Pane root){
        if (!this.myListOfZombies.isEmpty()) {
            PeaProjectile pea = new PeaProjectile(X + Constants.LAWN_WIDTH + Constants.PEA_RADIUS, Y + (Constants.LAWN_WIDTH / 2), root);
            this.listOfPeas.add(pea);
        }

    }
    private void movePeas(Pane root){
        for (PeaProjectile currentPea : this.listOfPeas){
            currentPea.move(2);
        }
        this.checkPeaZombieIntersection(root);
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
    @Override
    public int getX(){
        return (int) this.peaShooterHitbox.getX();
    }
    @Override
    public int getY(){
        return (int) this.peaShooterHitbox.getY();
    }

    @Override
    public void checkHealth(Pane root){
        this.peaShooterHealth = this.peaShooterHealth - 10;
        if (this.peaShooterHealth == 0) {
            this.removePlant(root);
        }
    }
    @Override
    public void removePlant(Pane root){
        this.stopTimeline();
        for (int i = 0; i < this.listOfPeas.size(); i++) {
            this.listOfPeas.get(i).removeGraphic(root);
        }
        root.getChildren().remove(this.peaShooterHitbox);
        this.myLawn.deletePlant(this);
    }
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
