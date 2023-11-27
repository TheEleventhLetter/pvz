package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Game {
    private Lawn lawn;
    private boolean peaShooterSeedSelected;
    private int totalSun;
    private Label displayTotalSun;

    public Game(Pane gamepane, HBox buttonPane){
        this.peaShooterSeedSelected = false;
        this.createButtonPane(buttonPane);
        this.createGamePane(gamepane);
        this.totalSun = 500;
    }

    private void createGamePane(Pane gamepane){
        this.lawn = new Lawn(gamepane);
        gamepane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e, gamepane));
    }

    private void createButtonPane(HBox buttonPane){
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.LAWN_WIDTH);
        buttonPane.setStyle("-fx-background-color: #705301");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        Button peaShooterSeedPacket = new Button("PeaShooter");
        peaShooterSeedPacket.setOnAction((ActionEvent e) -> this.peaShooterSeedSelectChecker());
        this.displayTotalSun = new Label("Total Sun: " + this.totalSun);
        this.displayTotalSun.setTextFill(Color.WHITE);
        this.displayTotalSun.setFont(new Font(20));
        buttonPane.getChildren().addAll(this.displayTotalSun, peaShooterSeedPacket, quitButton);

        buttonPane.setFocusTraversable(false);
    }
    private void peaShooterSeedSelectChecker() {
        if (!this.peaShooterSeedSelected) {
            if (this.totalSun >= 100) {
                this.peaShooterSeedSelected = true;
            }
        } else {
            this.peaShooterSeedSelected = false;
        }
    }

    public void handleMouseClick(MouseEvent e, Pane gamepane) {
        double MouseX = e.getX();
        double MouseY = e.getY();
        if (MouseX > 0 && MouseX < 2 * Constants.SCENE_WIDTH) {
            if (MouseY > Constants.LAWN_WIDTH && MouseY < Constants.SCENE_HEIGHT) {
                if (this.peaShooterSeedSelected) {
                    if (this.lawn.checkValid(MouseX, MouseY)) {
                        this.lawn.addPlant(MouseX, MouseY, gamepane);
                        this.peaShooterSeedSelected = false;
                        this.totalSun = this.totalSun - 100;
                        this.displayTotalSun.setText("Total Sun: " + this.totalSun);
                    }
                }
            }
        }
    }
}
