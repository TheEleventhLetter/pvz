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

public class Lawn {
    private Plant[][] plantBoard;
    private ArrayList<LinkedList<Zombie>> totalZombies;
    private ArrayList<LinkedList<Plant>> totalPlants;
    private Timeline timeline1;
    private Timeline timeline2;
    private int zombieCount;
    private Game myGame;

    public Lawn(Pane gamepane, int level, Game game){
        this.myGame = game;
        this.createZombieArrayList();
        this.createPlantArrayList();
        this.plantBoard = new Plant[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
        this.setUpZombieTimeline(gamepane, level);
        this.setUpTimeLine(gamepane);
    }
    public void setUpTimeLine(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkIntersection(root));
        this.timeline1 = new Timeline(kf);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
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
        ImageView lawnImage = new ImageView(new Image("indy/PVZ_Lawn.png"));
        gamepane.getChildren().add(lawnImage);
        lawnImage.setFitHeight(Constants.SCENE_HEIGHT - 115);
        lawnImage.setPreserveRatio(false);
        lawnImage.setFitWidth(Constants.SCENE_WIDTH + 350);
        lawnImage.setX(-205);
        lawnImage.setY(20);

    }
    private void setUpZombieTimeline(Pane root, int level){
        int duration = 0;
        if (level == 1){
            duration = Constants.LEVEL_ONE_RATIO;
            this.zombieCount = Constants.LEVEL_ONE_COUNT;
        } else if (level == 2){
            duration = Constants.LEVEL_TWO_RATIO;
            this.zombieCount = Constants.LEVEL_TWO_COUNT;
        } else if (level == 3){
            duration = Constants.LEVEL_THREE_RATIO;
            this.zombieCount = Constants.LEVEL_THREE_COUNT;
        } else if (level == 4){
            duration = Constants.LEVEL_FOUR_RATIO;
            this.zombieCount = Constants.LEVEL_FOUR_COUNT;
        }
        KeyFrame kf = new KeyFrame(Duration.millis(duration), (ActionEvent e) -> this.generateZombies(root));
        this.timeline2 = new Timeline(kf);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline2.play();
    }


    private int calculateNearestXPosition(double X){
        int nearestX = (int) (Math.floor(X/Constants.LAWN_WIDTH)) * Constants.LAWN_WIDTH;
        return nearestX;
    }

    private int calculateNearestYPosition(double Y){
        int nearestY = (int) (Math.floor(Y/Constants.LAWN_WIDTH)) * Constants.LAWN_WIDTH;
        return nearestY;
    }

    public int pixelToRow(int Y){
        int row = (Y / Constants.LAWN_WIDTH) - 1;
        return row;
    }

    public int pixelToColumn(int X){
        int column = X / Constants.LAWN_WIDTH - 1;
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
                break;
            case 5:
                newPlant = new CatTail(X, Y, this, root);
                break;
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
    public boolean checkPlacementValid(double MouseX, double MouseY){
        boolean spotOpen = false;
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            spotOpen = true;
        }
        return spotOpen;
    }
    public boolean checkDeletionValid(double MouseX, double MouseY){
        boolean spotFull = false;
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] != null) {
            spotFull = true;
        }
        return spotFull;
    }
    public Plant getPlant(double MouseX, double MouseY){
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        return this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)];
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
                randomZombie = new NormalZombie(randY, root, this);
                break;
            case 4: case 5:
                randomZombie = new ConeheadZombie(randY, root, this);
                break;
            case 6:
                randomZombie = new BucketheadZombie(randY, root, this);
                break;
            default:
                randomZombie = new NormalZombie(randY, root, this);
        }
        return randomZombie;
    }
    private int randomYpixel(){
        int randY = ((int)(Math.random() * Constants.LAWN_ROWS) + 1) * Constants.LAWN_WIDTH;
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
    public void addCount(Pane gamePane){
        if (this.zombieCount > 0) {
            this.zombieCount = this.zombieCount - 1;
        }
        if (this.zombieCount == 0) {
            this.myGame.gameWon(gamePane);
        }
        this.myGame.updateZombieCountDisplay();
    }
    public int getZombieCount(){
        return this.zombieCount;
    }
    public boolean isGameOver() {
        boolean gameOver = false;
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            LinkedList<Zombie> currentZombieList = this.totalZombies.get(i);
            if (!currentZombieList.isEmpty()) {
                for (Zombie currentZombie : currentZombieList) {
                    if (currentZombie.getX() < Constants.LAWN_WIDTH) {
                        gameOver = true;
                    }
                }
            }
        }
        return gameOver;
    }
    public void stopTimelines(){
        this.timeline1.stop();
        this.timeline2.stop();
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            for (int j = 0; j < Constants.LAWN_COLUMN; j++) {
                if (this.plantBoard[i][j] != null) {
                    this.plantBoard[i][j].stopTimeline();
                }
            }
            for (int k = 0; k < this.totalZombies.get(i).size(); k++){
                this.totalZombies.get(i).get(k).stopTimeline();
            }
            }
    }
    public void startTimelines(){
        this.timeline1.play();
        this.timeline2.play();
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            for (int j = 0; j < Constants.LAWN_COLUMN; j++) {
                if (this.plantBoard[i][j] != null) {
                    this.plantBoard[i][j].playTimeline();
                }
            }
            for (int k = 0; k < this.totalZombies.get(i).size(); k++){
                this.totalZombies.get(i).get(k).playTimeline();
            }
        }
    }

}
