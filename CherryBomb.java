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
import java.util.LinkedList;

public class CherryBomb implements Plant{
    private Rectangle cherryBombHitbox;
    private Lawn myLawn;
    private Timeline timeline1;
    private int explodingCountDown;
    private ArrayList<LinkedList<Zombie>> myListsOfZombies;
    public CherryBomb(int X, int Y, Lawn lawn, Pane root){
        this.myLawn = lawn;
        this.explodingCountDown = 2;
        this.cherryBombHitbox = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.cherryBombHitbox.setStroke(Color.BLACK);
        this.cherryBombHitbox.setFill(Color.DARKRED);
        root.getChildren().add(this.cherryBombHitbox);
        this.setUpExplodingTimeline(root);
    }
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
    private void setUpExplodingTimeline(Pane root){
        KeyFrame kf1 = new KeyFrame(Duration.seconds(1), (ActionEvent e) -> this.checkTimer(root));
        this.timeline1 = new Timeline(kf1);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.playTimeline();
    }
    @Override
    public void playTimeline(){
        this.timeline1.play();
    }
    @Override
    public void stopTimeline(){
        this.timeline1.stop();
    }
    @Override
    public int getX(){
        return (int) this.cherryBombHitbox.getX();
    }
    @Override
    public int getY(){
        return (int) this.cherryBombHitbox.getY();
    }
    @Override
    public void checkHealth(Pane root){
        //infinite health so don't decrease health
    }
    private void checkTimer(Pane root){
        this.explodingCountDown--;
        if (this.explodingCountDown == 0){
            this.explode(root);
            this.removePlant(root);
        }
    }
    @Override
    public void removePlant(Pane root) {
        this.stopTimeline();
        root.getChildren().remove(this.cherryBombHitbox);
        this.myLawn.deletePlant(this);
    }
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
