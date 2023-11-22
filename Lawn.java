package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Lawn {
    private LawnSquare[][] lawnGraphic;
    private PeaShooter[][] plantBoard;
    private LinkedList<NormalZombie>[] totalZombies;

    public Lawn(Pane gamepane){
        //this.totalZombies = new LinkedList<NormalZombie>[5];
        this.lawnGraphic = new LawnSquare[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.plantBoard = new PeaShooter[Constants.LAWN_ROWS][Constants.LAWN_COLUMN];
        this.setUpLawn(gamepane);
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
    private void generateZombies(int Y, Pane root){
        int randY = this.randomY();

    }
    private int randomY(){
        int randY = ((int)(Math.random() * 5) + 1) * Constants.LAWN_WIDTH;
        return randY;
    }

}
