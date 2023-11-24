package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;

public class Lawn {
    private LawnSquare[][] lawnGraphic;
    private PeaShooter[][] plantBoard;
    private ArrayList<LinkedList<NormalZombie>> totalZombies;

    public Lawn(Pane gamepane){
        this.createZombieArrayList();
        this.lawnGraphic = new LawnSquare[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.plantBoard = new PeaShooter[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
        this.setUpZombieTimeline(gamepane);
    }
    private void createZombieArrayList(){
        this.totalZombies = new ArrayList<LinkedList<NormalZombie>>(Constants.LAWN_ROWS);
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            this.totalZombies.add(i, new LinkedList<NormalZombie>());
        }
    }

    private void setUpLawn(Pane gamepane) {
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            for (int j = 0; j < Constants.LAWN_COLUMN; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        this.lawnGraphic[i][j] = new LawnSquare(j * Constants.LAWN_WIDTH, (i + 1) * Constants.LAWN_WIDTH, Color.GREEN, gamepane);
                    } else {
                        this.lawnGraphic[i][j] = new LawnSquare(j * Constants.LAWN_WIDTH, (i + 1) * Constants.LAWN_WIDTH, Color.LIGHTGREEN, gamepane);
                    }
                } else {
                    if (j % 2 == 0) {
                        this.lawnGraphic[i][j] = new LawnSquare(j * Constants.LAWN_WIDTH, (i + 1) * Constants.LAWN_WIDTH, Color.LIGHTGREEN, gamepane);
                    } else {
                        this.lawnGraphic[i][j] = new LawnSquare(j * Constants.LAWN_WIDTH, (i + 1) * Constants.LAWN_WIDTH, Color.GREEN, gamepane);
                    }
                }

            }
        }

    }
    private void setUpZombieTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(2000), (ActionEvent e) -> this.generateZombies(root));
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    private int calculateNearestXPosition(double X){
        int nearestX = (int) (Math.floor(X/100.0)) * 100;
        return nearestX;
    }

    private int calculateNearestYPosition(double Y){
        int nearestY = (int) (Math.floor(Y/100.0)) * 100;
        return nearestY;
    }

    public int pixelToRow(int Y){
        int row = (Y / Constants.LAWN_WIDTH) - 1;
        return row;
    }

    public int pixelToColumn(int X){
        int column = X / Constants.LAWN_WIDTH;
        return column;
    }

    public void addPlant(double MouseX, double MouseY, Pane gamepane) {
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        PeaShooter newPeaShooter = new PeaShooter(plantX, plantY, gamepane);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] = newPeaShooter;
            newPeaShooter.assignArrayRowCol(this.pixelToRow(plantY), this.pixelToColumn(plantX));
        }

    }
    public boolean checkValid(double MouseX, double MouseY){
        boolean spotOpen = false;
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            spotOpen = true;
        }
        return spotOpen;
    }
    private void generateZombies(Pane root){
        int randY = this.randomYpixel();
        NormalZombie newZombie = new NormalZombie(randY, root);
        this.totalZombies.get(this.pixelToRow(randY)).add(newZombie);
    }
    private int randomYpixel(){
        int randY = ((int)(Math.random() * 5) + 1) * Constants.LAWN_WIDTH;
        return randY;
    }

}
