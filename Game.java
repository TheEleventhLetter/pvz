package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class Game {
    private Lawn lawn;
    private boolean peaShooterSeedSelected;
    private int totalSun;
    private Label displayTotalSun;

    public Game(Pane gamepane, HBox buttonPane){
        this.totalSun = 500;
        this.peaShooterSeedSelected = false;
        this.createButtonPane(buttonPane);
        this.createGamePane(gamepane);
        this.setUpSunGenerationTimeline(gamepane);
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
    private void setUpSunGenerationTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.seconds(5), (ActionEvent e) -> this.generateSun(root));
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void generateSun(Pane root){
        int randX = (int)(Math.random() * Constants.SCENE_WIDTH);
        new Sun(randX, 0, root, this);
    }
    public void addToTotalSun(){
        this.totalSun = this.totalSun + 25;
        this.displayTotalSun.setText("Total Sun: " + this.totalSun);
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
