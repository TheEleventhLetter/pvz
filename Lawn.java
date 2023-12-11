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
 * Lawn class. Responsible for lawn gameplay and interaction. Performs logical and graphical change.
 */

public class Lawn {
    /**
     * Creates 7 instance variable. A 2D Array of Plants is created to represent the lawn logically. Additionally, two
     * ArrayLists of LinkedLists are created, one for total Zombies, and one for total Plants. Two timelines are declared,
     * as well as a zombieCount, the number of zombies that must be defeated to win the game. Game is declared for
     * association.
     */
    private Plant[][] plantBoard;
    private ArrayList<LinkedList<Zombie>> totalZombies;
    private ArrayList<LinkedList<Plant>> totalPlants;
    private Timeline timeline1;
    private Timeline timeline2;
    private int zombieCount;
    private Game myGame;

    /**
     * main constructor of lawn. Calls for methods that instantiate totalZombies and totalPlants. Instantiates plantBoard
     * and sets up Lawn graphically. Additionally sets up zombie timeline and intersection timeline.
     * @param gamepane passed to graphically handle elements.
     * @param level integer to determine difficulty of game. Effects zombie generation speed and total zombie count.
     * @param game passed for association.
     */
    public Lawn(Pane gamepane, int level, Game game){
        this.myGame = game;
        this.createZombieArrayList();
        this.createPlantArrayList();
        this.plantBoard = new Plant[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
        this.setUpZombieTimeline(gamepane, level);
        this.setUpIntersectionTimeLine(gamepane);
    }

    /**
     * method checks for intersections between plants and zombies. Utilizes timeline1.
     * @param root passed to graphically handle elements after intersection.
     */
    private void setUpIntersectionTimeLine(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkZombiePlantIntersection(root));
        this.timeline1 = new Timeline(kf);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
    }

    /**
     * Instantiates ArrayList of LinkedLists of Zombies.
     */
    private void createZombieArrayList(){
        this.totalZombies = new ArrayList<LinkedList<Zombie>>(Constants.LAWN_ROWS);
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            this.totalZombies.add(i, new LinkedList<Zombie>());
        }
    }
    /**
     * Instantiates ArrayList of LinkedLists of Plants.
     */
    private void createPlantArrayList(){
        this.totalPlants = new ArrayList<LinkedList<Plant>>(Constants.LAWN_ROWS);
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            this.totalPlants.add(i, new LinkedList<Plant>());
        }
    }

    /**
     * Sets up lawn graphically.
     * @param gamepane passed to graphically add ImageView lawnImage.
     */
    private void setUpLawn(Pane gamepane) {
        ImageView lawnImage = new ImageView(new Image("indy/PVZ_Lawn.png"));
        gamepane.getChildren().add(lawnImage);
        lawnImage.setFitHeight(Constants.SCENE_HEIGHT - 115);
        lawnImage.setPreserveRatio(false);
        lawnImage.setFitWidth(Constants.SCENE_WIDTH + 350);
        lawnImage.setX(-205);
        lawnImage.setY(20);

    }

    /**
     * this method sets up the zombie generation timeline. Depending on the level parameter, the interval of the
     * timeline will change, as well as the zombie count.
     * @param root passed to handle graphics
     * @param level passed to determine difficult of game.
     */
    private void setUpZombieTimeline(Pane root, int level){
        int duration;
        switch (level){
            case 1:
                duration = Constants.LEVEL_ONE_RATIO;
                this.zombieCount = Constants.LEVEL_ONE_COUNT;
                break;
            case 2:
                duration = Constants.LEVEL_TWO_RATIO;
                this.zombieCount = Constants.LEVEL_TWO_COUNT;
                break;
            case 3:
                duration = Constants.LEVEL_THREE_RATIO;
                this.zombieCount = Constants.LEVEL_THREE_COUNT;
                break;
            case 4:
                duration = Constants.LEVEL_FOUR_RATIO;
                this.zombieCount = Constants.LEVEL_FOUR_COUNT;
                break;
            default:
                duration = Constants.LEVEL_ONE_RATIO;
                this.zombieCount = Constants.LEVEL_ONE_COUNT;
                break;
        }
        KeyFrame kf = new KeyFrame(Duration.millis(duration), (ActionEvent e) -> this.generateZombies(root));
        this.timeline2 = new Timeline(kf);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline2.play();
    }

    /**
     * This method calculates the nearest valid X position to a click. Truncates to the nearest hundred so position
     * is correspondent to grid positions.
     * @param X X coordinate from mouse click
     * @return integer for closest valid X position.
     */
    private int calculateNearestXPosition(double X){
        int nearestX = (int) (Math.floor(X/Constants.LAWN_WIDTH)) * Constants.LAWN_WIDTH;
        return nearestX;
    }

    /**
     * This method calculates the nearest valid Y position to a click. Truncates to the nearest hundred so position
     *  is correspondent to grid positions.
     * @param Y Y coordinate from mouse click
     * @return integer for closest valid Y position.
     */
    private int calculateNearestYPosition(double Y){
        int nearestY = (int) (Math.floor(Y/Constants.LAWN_WIDTH)) * Constants.LAWN_WIDTH;
        return nearestY;
    }

    /**
     * converts pixel to row on plantBoard.
     * @param Y pixelY coordinate
     * @return row number
     */
    public int pixelToRow(int Y){
        int row = (Y / Constants.LAWN_WIDTH) - 1;
        return row;
    }

    /**
     * converts pixel to colum on plantBoard
     * @param X pixelX coordinate
     * @return column number
     */
    public int pixelToColumn(int X){
        int column = X / Constants.LAWN_WIDTH - 1;
        return column;
    }

    /**
     * This method adds a plant based on an X and Y location from the mouse click. The method converts the random X and
     * Y location to a valid X and Y position lying on top of the plantBoard grid. The method than converts those positions
     * from pixel to row. Once the method checks the position on the plantBoard is open, it will add a new plant based
     * on the plantNumber passed from the Game class. It will assign the new plant its list of zombies and additionally
     * notify all zombies on the row of the plant's existence so plant zombie interactions are updated.
     * @param MouseX X position from Mouse click
     * @param MouseY Y position from Mouse click
     * @param gamepane passed to graphically handle elements
     * @param myGame passed for association in sunflower
     * @param plantNumber passed to determine plant type
     */
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

    /**
     * This method evaluates a plant Number into an actual plant instance. Using a switch case, depending on a certain
     * plant number, a different plant will be instantiated.
     * @param X X position of where the plant should be created
     * @param Y Y position of where the plant should be created
     * @param root passed to graphically add plant and all other elements
     * @param number determines the plant type
     * @param myGame passed for association with sunflower.
     * @return Plant type
     */
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

    /**
     * This method is responsible for deleting a plant logically and graphically. It takes in a plant as a parameter
     * and deletes it from the total list of plants. It then sets the position of the plant on the plantBoard to null,
     * so the plant is garbage collected. It will then tell all zombies that may have been eating the zombie and not
     * walking to resume walking. It will then update the zombie's list of plants so, they can continue on with their day.
     * @param oldPlant Plant that must be deleted.
     */
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

    /**
     * Validation method to check if placement is valid. Checks the board position is null or not full.
     * @param MouseX X position of mouse click
     * @param MouseY Y position of mouse click
     * @return true for valid, false for invalid.
     */
    public boolean checkPlacementValid(double MouseX, double MouseY){
        boolean spotOpen = false;
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            spotOpen = true;
        }
        return spotOpen;
    }

    /**
     * Validation method to check if deletion is valid. Checks the board position is not null or full
     * @param MouseX X position of mouse click
     * @param MouseY Y position of mouse click
     * @return true for valid, false for invalid.
     */
    public boolean checkDeletionValid(double MouseX, double MouseY){
        boolean spotFull = false;
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] != null) {
            spotFull = true;
        }
        return spotFull;
    }

    /**
     * getter/accessor method that returns a plant in a certain position.
     * @param MouseX X position of mouse click
     * @param MouseY Y position of mouse click
     * @return Plant in said position
     */
    public Plant getPlant(double MouseX, double MouseY){
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        return this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)];
    }

    /**
     * this method is responsible for generating zombies. It will generate a random Y pixel (that is on top of the lawn
     * grid) to generate a new zombie at. The zombie will then be added logically to the total list of zombies, and
     * each plant will be updated of the zombies existence so, they can target them.
     * @param root passed to graphically add zombie
     */
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

    /**
     * this method returns a random zombie type. Uses a switch case to return either a normalZombie, coneHeadZombie,
     * or a bucketHeadZombie.
     * @param randY Y position of zombie when generated/instantiated.
     * @param root passed to add zombie graphically
     * @return random zombie
     */
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

    /**
     * this method calculates a random Y coordinate in pixels that is on top of the grid.
     * @return a Y coordinate
     */
    private int randomYpixel(){
        int randY = ((int)(Math.random() * Constants.LAWN_ROWS) + 1) * Constants.LAWN_WIDTH;
        return randY;
    }

    /**
     * this method is responsible for checking plant zombie intersection. For each row of the lawn, it will take each plant
     * in that row and zombie in that row and check if they are intersecting. If they are intersecting (call the zombie
     * method didCollide), the zombie will stop walking. The plant will then be called to check its health (its being
     * eaten by the zombie!!!!).
     * @param root passed to graphically remove plant if eaten.
     */
    private void checkZombiePlantIntersection(Pane root){
        for (int i = 0; i < Constants.LAWN_ROWS; i++){
            LinkedList<Zombie> currentZombieList = this.totalZombies.get(i);
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

    /**
     * this method is meant to addCount to the total zombies defeated (though it literally subtracts from the total).
     * If the total number is defeated, the game will run the gameWon method. The method also updates the display of
     * zombies needed to defeat.
     * @param gamePane passed to add victoryLabel in game class.
     */
    public void addCount(Pane gamePane){
        if (this.zombieCount > 0) {
            this.zombieCount = this.zombieCount - 1;
        }
        if (this.zombieCount == 0) {
            this.myGame.gameWon(gamePane);
        }
        this.myGame.updateZombieCountDisplay();
    }

    /**
     * public getter/accessor method that returns the zombie count.
     * @return zombie count
     */
    public int getZombieCount(){
        return this.zombieCount;
    }

    /**
     * method checks if game is over. Checks whether any zombies have crossed the house.
     * @return returns boolean true for gameOver and false for game is not over
     */
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

    /**
     * method is meant to stop all timelines. Stops direct timelines that are part of the lawn class and also tells
     * every plant that exists on the plant board to stop their respective timelines, as well as the zombies.
     */
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
    /**
     * method is meant to play/resume all timelines. Plays direct timelines that are part of the lawn class and also tells
     * every plant that exists on the plant board to resume their respective timelines, as well as the zombies.
     */
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
