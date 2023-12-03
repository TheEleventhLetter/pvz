package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Lawn {
    private LawnSquare[][] lawnGraphic;
    private Plant[][] plantBoard;
    private ArrayList<LinkedList<Zombie>> totalZombies;
    private ArrayList<LinkedList<Plant>> totalPlants;

    public Lawn(Pane gamepane){
        this.createZombieArrayList();
        this.createPlantArrayList();
        this.lawnGraphic = new LawnSquare[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.plantBoard = new Plant[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
        this.setUpZombieTimeline(gamepane);
        this.setUpTimeLine(gamepane);
    }
    public void setUpTimeLine(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkIntersection(root));
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void createZombieArrayList(){
        this.totalZombies = new ArrayList<LinkedList<Zombie>>(Constants.LAWN_ROWS);
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            this.totalZombies.add(i, new LinkedList<Zombie>());
        }
    }
    private void createPlantArrayList(){
        this.totalPlants = new ArrayList<LinkedList<Plant>>(Constants.LAWN_ROWS);
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            this.totalPlants.add(i, new LinkedList<Plant>());
        }
    }
    private void checkIntersection(Pane root){
        this.checkZombiePlantIntersection(root);
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
        KeyFrame kf = new KeyFrame(Duration.millis(3000), (ActionEvent e) -> this.generateZombies(root));
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

    public void addPlant(double MouseX, double MouseY, Pane gamepane, Game myGame, int plantNumber) {
        
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        Plant newPlant = this.evaluateNumber(plantX, plantY, gamepane, plantNumber, myGame);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] = newPlant;
            newPlant.assignCorrespondingListOfZombies(this.totalZombies);
            this.totalPlants.get(this.pixelToRow(plantY)).add(newPlant);
            if (!this.totalZombies.get(this.pixelToRow(plantY)).isEmpty()) {
                for (int i = 0; i < this.totalZombies.get(this.pixelToRow(plantY)).size(); i++) {
                    this.totalZombies.get(this.pixelToRow(plantY)).get(i).assignCorrespondingListOfPlants(this.totalPlants.get(this.pixelToRow(plantY)));
                }
            }
        }

    }
    private Plant evaluateNumber(int X, int Y, Pane root, int number, Game myGame){
        int Pnum = number;
        Plant newPlant = null;
        switch (Pnum){
            case 1:
                newPlant = new PeaShooter(X, Y, this, root);
                break;
            case 2:
                newPlant = new SunFlower(X, Y, this, root, myGame);
                break;
            case 3:
                newPlant = new CherryBomb(X, Y, this, root);
                break;
            case 4:
                newPlant = new Walnut(X, Y, this, root);
            default:
                break;
        }
        return newPlant;
    }
    public void deletePlant(Plant oldPlant) {
        this.totalPlants.get(this.pixelToRow(oldPlant.getY())).remove(oldPlant);
        this.plantBoard[this.pixelToRow(oldPlant.getY())][this.pixelToColumn(oldPlant.getX())] = null;
        if (!this.totalZombies.get(this.pixelToRow(oldPlant.getY())).isEmpty()) {
            for (Zombie currentZombie : this.totalZombies.get(this.pixelToRow(oldPlant.getY()))) {
                currentZombie.resumeWalking();
                for (int i = 0; i < this.totalZombies.get(this.pixelToRow(oldPlant.getY())).size(); i++) {
                    this.totalZombies.get(this.pixelToRow(oldPlant.getY())).get(i).assignCorrespondingListOfPlants(this.totalPlants.get(this.pixelToRow(oldPlant.getY())));
                }
            }
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
        Zombie newZombie = this.randomZombie(randY, root);
        this.totalZombies.get(this.pixelToRow(randY)).add(newZombie);
        for (int i = 0; i < Constants.LAWN_COLUMN; i++){
            if (this.plantBoard[this.pixelToRow(randY)][i] != null){
                newZombie.assignCorrespondingListOfPlants(this.totalPlants.get(this.pixelToRow(randY)));
            }
        }
    }
    private Zombie randomZombie(int randY, Pane root){
        Zombie randomZombie;
        int randomNum = (int)(Math.random() * 7);

        switch (randomNum){
            case 0: case 1: case 2: case 3:
                randomZombie = new NormalZombie(randY, root);
                break;
            case 4: case 5:
                randomZombie = new ConeheadZombie(randY, root);
                break;
            case 6:
                randomZombie = new BucketheadZombie(randY, root);
                break;
            default:
                randomZombie = new NormalZombie(randY, root);
        }
        return randomZombie;
    }
    private int randomYpixel(){
        int randY = ((int)(Math.random() * 5) + 1) * Constants.LAWN_WIDTH;
        return randY;
    }

    private void checkZombiePlantIntersection(Pane root){
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            LinkedList<Zombie> currentZombieList = totalZombies.get(i);
            if (!currentZombieList.isEmpty()) {
                for (Zombie currentZombie : currentZombieList){
                    if(!currentZombie.getMyListOfPlants().isEmpty()) {
                        LinkedList<Plant> currentPlantList = currentZombie.getMyListOfPlants();
                        for (Plant currentPlant : currentPlantList){
                            if (currentZombie.didCollide(currentPlant.getX(), currentPlant.getY(), Constants.LAWN_WIDTH, Constants.LAWN_WIDTH)){
                                currentZombie.stopWalking();
                                currentPlant.checkHealth(root);
                            }
                        }
                    }
                }
            }
        }
    }

}
