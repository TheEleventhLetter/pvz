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
    private boolean readyToRemove;
    private SeedPacket chosenPacket;
    private int totalSun;
    private Label displayTotalSun;
    private SeedPacket[] seedPackets;
    private Timeline timeline1;
    private Timeline timeline2;
    private Label gameOverLabel;
    private boolean isPaused;

    public Game(Pane gamepane, HBox buttonPane){
        this.seedPackets = new SeedPacket[5];
        this.totalSun = 500;
        this.somePacketSelected = false;
        this.readyToRemove = false;
        this.isPaused = false;
        this.createButtonPane(buttonPane);
        this.createGamePane(gamepane);
        this.setUpSunGenerationTimeline(gamepane);
        this.setUpGameOverTimeline(gamepane);
    }
    private void setUpSeedPackets(Pane buttonPane, Game myGame){
        this.seedPackets[0] = new PeaShooterSeedPacket(buttonPane, myGame);
        this.seedPackets[1] = new SunFlowerSeedPacket(buttonPane, myGame);
        this.seedPackets[2] = new CherryBombSeedPacket(buttonPane, myGame);
        this.seedPackets[3] = new WalnutSeedPacket(buttonPane, myGame);
        this.seedPackets[4] = new CatTailSeedPacket(buttonPane, myGame);
    }

    private void createGamePane(Pane gamepane){
        this.lawn = new Lawn(gamepane);
        gamepane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e, gamepane));
        this.gameOverLabel = new Label("THE ZOMBIES ATE YOUR BRAINS!!");
        this.gameOverLabel.setTextFill(Color.DARKSEAGREEN);
        this.gameOverLabel.setFont(new Font(50));
    }

    private void createButtonPane(HBox buttonPane){
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.LAWN_WIDTH);
        buttonPane.setStyle("-fx-background-color: #705301");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        Button removeButton = new Button("Remove Plant");
        removeButton.setOnAction((ActionEvent e) -> removeTrueFalse());
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction((ActionEvent e) -> pauseGame());
        this.displayTotalSun = new Label("Total Sun: " + this.totalSun);
        this.displayTotalSun.setTextFill(Color.WHITE);
        this.displayTotalSun.setFont(new Font(20));
        buttonPane.getChildren().add(this.displayTotalSun);
        this.setUpSeedPackets(buttonPane, this);
        buttonPane.getChildren().addAll(removeButton, quitButton);

        buttonPane.setFocusTraversable(false);
    }
    public int getTotalSun(){
        return this.totalSun;
    }
    private void setUpSunGenerationTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.seconds(5), (ActionEvent e) -> this.generateSun(root));
        this.timeline1 = new Timeline(kf);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
    }
    private void setUpGameOverTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkGameOver(root));
        this.timeline2 = new Timeline(kf);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline2.play();

    }
    private void stopTimelines(){
        this.timeline1.stop();
        this.timeline2.stop();
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
        if (!this.readyToRemove) {
            for (int i = 0; i < 5; i++) {
                if (this.seedPackets[i].isSeedSelected()) {
                    this.somePacketSelected = true;
                }
            }
        }
        return this.somePacketSelected;
    }
    private void removeTrueFalse() {
        if (!this.readyToRemove) {
            this.readyToRemove = true;
        } else {
            this.readyToRemove = false;
        }
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
                if (this.chosenPacket != null) {
                    if (this.chosenPacket.isSeedSelected()) {
                        if (this.lawn.checkPlacementValid(MouseX, MouseY)) {
                            this.lawn.addPlant(MouseX, MouseY, gamepane, this, this.chosenPacket.getPlantNumber());
                            this.totalSun = this.totalSun - this.chosenPacket.myCost();
                            this.chosenPacket.reset();
                            this.chosenPacket.seedColorChecker();
                            this.somePacketSelected = false;
                            this.displayTotalSun.setText("Total Sun: " + this.totalSun);
                        }
                    }
                }
                if (this.readyToRemove) {
                    if (this.lawn.checkDeletionValid(MouseX, MouseY)) {
                        this.lawn.getPlant(MouseX, MouseY).removePlant(gamepane);
                        this.readyToRemove = false;
                    }
                }
            }
        }
    }
    private void checkGameOver(Pane root){
        if (this.lawn.isGameOver()){
            this.stopTimelines();
            this.lawn.stopTimelines();
            root.getChildren().add(this.gameOverLabel);
        }
    }
    private void pauseGame(){

    }
}
