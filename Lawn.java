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
    private ArrayList<LinkedList<Zombie>> totalZombies;

    public Lawn(Pane gamepane){
        this.createZombieArrayList();
        this.lawnGraphic = new LawnSquare[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.plantBoard = new PeaShooter[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
        this.setUpZombieTimeline(gamepane);
        this.setUpTimeLine(gamepane);
    }
    public void setUpTimeLine(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkPeaZombieIntersection(root));
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

    public void addPlant(double MouseX, double MouseY, Pane gamepane) {
        int plantX = this.calculateNearestXPosition(MouseX);
        int plantY = this.calculateNearestYPosition(MouseY);
        PeaShooter newPeaShooter = new PeaShooter(plantX, plantY, gamepane);
        if (this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] == null) {
            this.plantBoard[this.pixelToRow(plantY)][this.pixelToColumn(plantX)] = newPeaShooter;
            newPeaShooter.assignCorrespondingListOfZombies(this.totalZombies.get(this.pixelToRow(plantY)));
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
    private void checkPeaZombieIntersection(Pane root){
        for (int i = 0; i < Constants.LAWN_ROWS; i++) {
            for (int j = 0; j < Constants.LAWN_COLUMN; j++) {
                if (this.plantBoard[i][j] != null) {
                    LinkedList<PeaProjectile> ListOfPeas = this.plantBoard[i][j].getPeaList();
                    LinkedList<Zombie> ListOfZombies = this.totalZombies.get(i);
                    if (!ListOfPeas.isEmpty()) {
                        if (!ListOfZombies.isEmpty()) {
                            for (int k = 0; k < ListOfPeas.size(); k++) {
                                for (int z = 0; z < ListOfZombies.size(); z++) {
                                    PeaProjectile currentPea = ListOfPeas.get(k);
                                    Zombie currentZombie = ListOfZombies.get(z);
                                    if (currentPea.didCollide(currentZombie.getX(), currentZombie.getY())) {
                                        currentPea.removeGraphic(root);
                                        ListOfPeas.remove(currentPea);
                                        currentZombie.checkHealth(root, ListOfZombies);
                                        if (ListOfPeas.isEmpty()){
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
    }

}
