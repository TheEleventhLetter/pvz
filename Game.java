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

import java.util.ArrayList;


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
    private Label pausedLabel;
    private Label zombieCountLabel;
    private Label victoryLabel;
    private boolean isPaused;
    private boolean isPlaying;
    private ArrayList<Sun> sunArrayList;

    public Game(Pane gamepane, HBox buttonPane, int level, Menu myMenu){
        this.sunArrayList = new ArrayList<>();
        this.seedPackets = new SeedPacket[Constants.SEED_PACKET_NUMBER];
        this.totalSun = Constants.INITIAL_SUN;
        this.somePacketSelected = false;
        this.readyToRemove = false;
        this.isPaused = false;
        this.isPlaying = true;
        this.createGamePane(gamepane, level);
        this.createButtonPane(buttonPane, myMenu);
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

    private void createGamePane(Pane gamepane, int level){
        this.lawn = new Lawn(gamepane, level, this);
        gamepane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e, gamepane));
        this.gameOverLabel = new Label("THE ZOMBIES ATE YOUR BRAINS!!");
        this.gameOverLabel.setTextFill(Color.DARKSEAGREEN);
        this.gameOverLabel.setFont(new Font(50));
        this.pausedLabel = new Label("Game Paused");
        this.pausedLabel.setTextFill(Color.WHITE);
        this.pausedLabel.setFont(new Font(20));
        this.victoryLabel = new Label("YOU WON!!!");
        this.victoryLabel.setTextFill(Color.WHITE);
        this.victoryLabel.setFont(new Font(50));
    }

    private void createButtonPane(HBox buttonPane, Menu myMenu){
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.LAWN_WIDTH + 20);
        buttonPane.setStyle("-fx-background-color: #705301");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        Button removeButton = new Button("Remove Plant");
        removeButton.setOnAction((ActionEvent e) -> this.removeTrueFalse());
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction((ActionEvent e) -> this.pauseGame(buttonPane));
        Button menuButton = new Button("Menu");
        //menuButton.setOnAction((ActionEvent e) -> );
        this.displayTotalSun = new Label("Total Sun: " + this.totalSun);
        this.displayTotalSun.setTextFill(Color.WHITE);
        this.displayTotalSun.setFont(new Font(20));
        this.zombieCountLabel = new Label("Zombies To Defeat: " + this.lawn.getZombieCount());
        this.zombieCountLabel.setTextFill(Color.WHITE);
        this.zombieCountLabel.setFont(new Font(20));
        this.setUpSeedPackets(buttonPane, this);
        buttonPane.getChildren().addAll(this.displayTotalSun, removeButton, this.zombieCountLabel, pauseButton, quitButton, menuButton);

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
    private void startTimelines(){
        this.timeline1.play();
        this.timeline2.play();
    }
    public boolean getIsPaused(){
        return this.isPaused;
    }
    public boolean getIsPlaying(){
        return this.isPlaying;
    }
    private void generateSun(Pane root){
        int randX = (int)(Math.random() * Constants.SCENE_WIDTH);
        Sun newSun = new Sun(randX, 0, root, this);
        this.addToSunList(newSun);
    }
    public void addToSunList(Sun currentSun){
        this.sunArrayList.add(currentSun);
    }
    public void removeFromSunList(Sun currentSun){
        this.sunArrayList.remove(currentSun);
    }

    public void addToTotalSun(){
        this.totalSun = this.totalSun + Constants.SUN_COST;
        this.displayTotalSun.setText("Total Sun: " + this.totalSun);
        for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++){
            this.seedPackets[i].updateSun();
        }
    }

    public boolean preventDoubleChoicePacket(){
        if (!this.readyToRemove) {
            for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++) {
                if (this.seedPackets[i].isSeedSelected()) {
                    this.somePacketSelected = true;
                }
            }
        }
        return this.somePacketSelected;
    }
    private void removeTrueFalse() {
        if (this.isPlaying) {
            if (!this.isPaused) {
                if (!this.readyToRemove) {
                    this.readyToRemove = true;
                } else {
                    this.readyToRemove = false;
                }
            }
        }
    }
    private void findChosenSeedPacket(){
        for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++) {
            if (this.seedPackets[i].isSeedSelected()) {
                this.chosenPacket = this.seedPackets[i];
            }
        }
    }

    public void handleMouseClick(MouseEvent e, Pane gamepane) {
        if (this.isPlaying) {
            if (!this.isPaused) {
                double MouseX = e.getX();
                double MouseY = e.getY();
                if (MouseX > Constants.LAWN_WIDTH && MouseX < Constants.SCENE_WIDTH) {
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
        }
    }
    private void checkGameOver(Pane root){
        if (this.lawn.isGameOver()){
            this.stopTimelines();
            this.lawn.stopTimelines();
            this.stopSuns();
            root.getChildren().add(this.gameOverLabel);
            this.isPlaying = false;
        }
    }
    private void pauseGame(HBox root) {
        if (this.isPlaying) {
            if (!this.isPaused) {
                this.stopTimelines();
                this.lawn.stopTimelines();
                this.stopSuns();
                root.getChildren().add(this.pausedLabel);
                this.isPaused = true;
            } else {
                this.startTimelines();
                this.lawn.startTimelines();
                this.startSuns();
                root.getChildren().remove(this.pausedLabel);
                this.isPaused = false;
            }
        }
    }
    public void gameWon(Pane root){
        this.stopTimelines();
        this.lawn.stopTimelines();
        this.stopSuns();
        root.getChildren().add(this.victoryLabel);
        this.isPlaying = false;
    }
    public void updateZombieCountDisplay(){
        this.zombieCountLabel.setText("Zombies To Defeat: " + this.lawn.getZombieCount());
    }
    private void stopSuns(){
        for (Sun currentSun : this.sunArrayList){
            currentSun.stopTimeline();
        }
    }
    private void startSuns(){
        for (Sun currentSun : this.sunArrayList){
            currentSun.playTimeline();
        }
    }
}
