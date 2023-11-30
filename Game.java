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
    private boolean somePacketSelected;
    private SeedPacket chosenPacket;
    private int totalSun;
    private Label displayTotalSun;
    private SeedPacket[] seedPackets;

    public Game(Pane gamepane, HBox buttonPane){
        this.seedPackets = new SeedPacket[5];
        this.totalSun = 500;
        this.somePacketSelected = false;
        this.createButtonPane(buttonPane);
        this.createGamePane(gamepane);
        this.setUpSunGenerationTimeline(gamepane);
    }
    private void setUpSeedPackets(Pane buttonPane, Game myGame){
        this.seedPackets[0] = new PeaShooterSeedPacket(buttonPane, myGame);
        this.seedPackets[1] = new SunFlowerSeedPacket(buttonPane, myGame);
        this.seedPackets[2] = new PeaShooterSeedPacket(buttonPane, myGame);
        this.seedPackets[3] = new PeaShooterSeedPacket(buttonPane, myGame);
        this.seedPackets[4] = new PeaShooterSeedPacket(buttonPane, myGame);
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
        this.displayTotalSun = new Label("Total Sun: " + this.totalSun);
        this.displayTotalSun.setTextFill(Color.WHITE);
        this.displayTotalSun.setFont(new Font(20));
        this.setUpSeedPackets(buttonPane, this);
        buttonPane.getChildren().addAll(this.displayTotalSun, quitButton);

        buttonPane.setFocusTraversable(false);
    }
    public int getTotalSun(){
        return this.totalSun;
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
        for (int i = 0; i < 5; i++){
            this.seedPackets[i].updateSun();
        }
    }

    public boolean preventDoubleChoicePacket(){
        for (int i = 0; i < 5; i++){
            if (this.seedPackets[i].isSeedSelected()){
                this.somePacketSelected = true;
            }
        }
        return this.somePacketSelected;
    }
    private void findChosenSeedPacket(){
        for (int i = 0; i < 5; i++) {
            if (this.seedPackets[i].isSeedSelected()) {
                this.chosenPacket = this.seedPackets[i];
            }
        }
    }

    public void handleMouseClick(MouseEvent e, Pane gamepane) {
        double MouseX = e.getX();
        double MouseY = e.getY();
        if (MouseX > 0 && MouseX < 2 * Constants.SCENE_WIDTH) {
            if (MouseY > Constants.LAWN_WIDTH && MouseY < Constants.SCENE_HEIGHT) {
                this.findChosenSeedPacket();
                if (this.chosenPacket.isSeedSelected()) {
                    if (this.lawn.checkValid(MouseX, MouseY)) {
                        this.lawn.addPlant(MouseX, MouseY, gamepane, this, this.chosenPacket.getPlantNumber());
                        this.totalSun = this.totalSun - this.chosenPacket.myCost();
                        this.chosenPacket.reset();
                        this.somePacketSelected = false;
                        this.displayTotalSun.setText("Total Sun: " + this.totalSun);
                    }
                    /**
                     * } else if (this.sunFlowerSeedSelected) {
                     *                     if (this.lawn.checkValid(MouseX, MouseY)){
                     *                         this.lawn.addPlant(MouseX, MouseY, gamepane);
                     *                         this.sunFlowerSeedSelected = false;
                     *                         this.totalSun = this.totalSun - 50;
                     *                         this.displayTotalSun.setText("Total Sun; " + this.totalSun);
                     */
                    }
                }

            }
    }
}
