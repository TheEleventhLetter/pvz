package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Game class. Responsible for actual gameplay. Performs logical and graphical change.
 */
public class Game {
    /**
     * Creates 17 instance variables. Lawn is the main lawn used in the game. somePacketSelected is a boolean to
     * check whether a packet has already been selected or not. Prevents double selection of packets. readyToRemove is
     * a boolean that notifies whether a click will represent a generation or removal of a plant. isPaused is
     * a boolean to represent whether game is paused or not. isPlaying is a boolean to represent whether game is being
     * played or not. seedPackets is an array of SeedPacket to keep track of all seedPackets. chosenPacket is used
     * to determine which packet has been selected and what type of plant to place. totalSun represent the total sun
     * available to the user in game. displayTotalSun, pausedLabel, and zombieCountLabel are all labels that each
     * represent the user's total sun, whether the game is paused, and the total number of zombies needed to defeat
     * in order to win. Two timelines are created. gameOverLabel and victoryLabel are javafx ImageView that present
     * pngs of the winning and losing text. sunArrayList is an arraylist to keep track of all sun on screen. Finally,
     * the remove button removes plants.
     */
    private Lawn lawn;
    private boolean somePacketSelected;
    private boolean readyToRemove;
    private boolean isPaused;
    private boolean isPlaying;
    private SeedPacket[] seedPackets;
    private SeedPacket chosenPacket;
    private int totalSun;
    private Label displayTotalSun;
    private Label pausedLabel;
    private Label zombieCountLabel;
    private Timeline timeline1;
    private Timeline timeline2;
    private ImageView gameOverLabel;
    private ImageView victoryLabel;
    private ArrayList<Sun> sunArrayList;
    private Button removeButton;

    /**
     * main constructor of game. Instantiates ArrayList and seedPackets. Additionally, instantiates all boolean instance
     * variables. Calls for several setUp methods. Additionally, creates a menu button that is associated with menu
     * class above this class.
     * @param gamepane passed to handle all graphical elements of game on gameplay
     * @param buttonPane passed to handle all graphical elements of game on button area.
     * @param level passed to determine level of difficulty of game.
     * @param myMenu passed for association.
     */
    public Game(Pane gamepane, HBox buttonPane, int level, Menu myMenu){
        this.sunArrayList = new ArrayList<>();
        this.seedPackets = new SeedPacket[Constants.SEED_PACKET_NUMBER];
        this.totalSun = Constants.INITIAL_SUN;
        this.somePacketSelected = false;
        this.readyToRemove = false;
        this.isPaused = false;
        this.isPlaying = true;
        this.createGamePane(gamepane, level);
        this.createButtonPane(buttonPane, gamepane);
        this.setUpSunGenerationTimeline(gamepane);
        this.setUpGameOverTimeline(gamepane);
        Button menuButton = new Button("Menu");
        menuButton.setOnAction((ActionEvent e) -> myMenu.restartGame());
        buttonPane.getChildren().add(menuButton);
    }

    /**
     * this method is meant to set up seed packets. When adding more plants to this game, these seedPacket combinations
     * can be interchangeable.
     * @param buttonPane passed to handle graphics of seedPacket
     * @param myGame passed for association
     */
    private void setUpSeedPackets(Pane buttonPane, Game myGame){
        this.seedPackets[0] = new PeaShooterSeedPacket(buttonPane, myGame);
        this.seedPackets[1] = new SunFlowerSeedPacket(buttonPane, myGame);
        this.seedPackets[2] = new CherryBombSeedPacket(buttonPane, myGame);
        this.seedPackets[3] = new WalnutSeedPacket(buttonPane, myGame);
        this.seedPackets[4] = new CatTailSeedPacket(buttonPane, myGame);
    }
    /**
     * this method creates the gamePane. Instantiates a new lawn and calls for handler when user clicks on the lawn.
     * sets up several labels that will be added onto the gamePane under certain conditions (called in later methods)
     * @param gamepane passed to graphically handle gameplay on Lawn.
     * @param level integer to determine difficulty.
     */
    private void createGamePane(Pane gamepane, int level){
        this.lawn = new Lawn(gamepane, level, this);
        gamepane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e, gamepane));
        this.gameOverLabel = new ImageView(new Image("indy/PVZ_GameOverScreen.png"));
        this.gameOverLabel.setX(300);
        this.gameOverLabel.setY(100);
        this.pausedLabel = new Label("Game Paused");
        this.pausedLabel.setTextFill(Color.WHITE);
        this.pausedLabel.setFont(new Font(20));
        this.pausedLabel.setLayoutX(Constants.SCENE_WIDTH / 2.0);
        this.pausedLabel.setLayoutY(Constants.LAWN_WIDTH);
        this.victoryLabel = new ImageView(new Image("indy/PVZ_WinScreen.png"));
        this.victoryLabel.setX(300);
        this.victoryLabel.setY(100);
    }

    /**
     * this method creates the buttonPane. Instantiates all buttons for pausing, quitting, removing and adds them
     * graphically. Also, graphically adds seed packets. Additionally sets up two Labels that will be added to the button
     * Pane.
     * @param buttonPane passed to graphically add buttons and labels onto pane.
     */
    private void createButtonPane(HBox buttonPane, Pane gamePane){
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.LAWN_WIDTH + 20);
        buttonPane.setStyle("-fx-background-color: #705301");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        this.removeButton = new Button("Remove Plant");
        this.removeButton.setOnAction((ActionEvent e) -> this.removeTrueFalse());
        this.checkRemoveColor();
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction((ActionEvent e) -> this.pauseGame(gamePane));
        this.displayTotalSun = new Label("Total Sun: " + this.totalSun);
        this.displayTotalSun.setTextFill(Color.WHITE);
        this.displayTotalSun.setFont(new Font(15));
        this.zombieCountLabel = new Label("Zombies To Defeat: " + this.lawn.getZombieCount());
        this.zombieCountLabel.setTextFill(Color.WHITE);
        this.zombieCountLabel.setFont(new Font(15));
        this.setUpSeedPackets(buttonPane, this);
        buttonPane.getChildren().addAll(this.displayTotalSun, this.removeButton, this.zombieCountLabel, pauseButton, quitButton);

        buttonPane.setFocusTraversable(false);
    }

    /**
     * getter/accessor method that returns total sun.
     * @return total sun
     */
    public int getTotalSun(){
        return this.totalSun;
    }

    /**
     * method sets up sun generation. Sun generation is dependent on instance variable timeline1.
     * @param root passed to graphically handle new sun that is generated.
     */
    private void setUpSunGenerationTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.seconds(5), (ActionEvent e) -> this.generateSun(root));
        this.timeline1 = new Timeline(kf);
        this.timeline1.setCycleCount(Animation.INDEFINITE);
        this.timeline1.play();
    }

    /**
     * method checks if the game is over. Checks frequently based on instance variable timeline2.
     * @param root passed to graphically handle gameOver screen.
     */
    private void setUpGameOverTimeline(Pane root){
        KeyFrame kf = new KeyFrame(Duration.millis(10), (ActionEvent e) -> this.checkGameOver(root));
        this.timeline2 = new Timeline(kf);
        this.timeline2.setCycleCount(Animation.INDEFINITE);
        this.timeline2.play();

    }

    /**
     * method stops all timelines in the game class.
     */
    private void stopTimelines(){
        this.timeline1.stop();
        this.timeline2.stop();
    }

    /**
     * method plays/starts all timelines in the game class.
     */
    private void startTimelines(){
        this.timeline1.play();
        this.timeline2.play();
    }

    /**
     * getter/accessor method that returns whether the game is paused or not.
     * @return boolean true for paused, false for not paused.
     */
    public boolean getIsPaused(){
        return this.isPaused;
    }

    /**
     * getter/accessor method that returns whether the game is currently being played or not/
     * @return boolean true for playing, false for not being played.
     */
    public boolean getIsPlaying(){
        return this.isPlaying;
    }

    /**
     * method generates sun. Finds a random X coordinate to randomly generate sun. Adds sun logically to the sunArrayList.
     * @param root passed to add new Sun graphically.
     */
    private void generateSun(Pane root){
        int randX = (int)(Math.random() * Constants.SCENE_WIDTH);
        Sun newSun = new Sun(randX, 0, root, this);
        this.addToSunList(newSun);
    }

    /**
     * public method that adds a sun to the total sunArrayList.
     * @param currentSun sun that needs to be added to the list.
     */
    public void addToSunList(Sun currentSun){
        this.sunArrayList.add(currentSun);
    }
    /**
     * public method that removes a sun from the total sunArrayList.
     * @param currentSun sun that needs to be removed from the list.
     */
    public void removeFromSunList(Sun currentSun){
        this.sunArrayList.remove(currentSun);
    }

    /**
     * method adds to the sun currency. Updates totalSun integer as well as display. Finally, it updates the sun
     * for each seedPacket as well.
     */
    public void addToTotalSun(){
        this.totalSun = this.totalSun + Constants.SUN_COST;
        this.displayTotalSun.setText("Total Sun: " + this.totalSun);
        for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++){
            this.seedPackets[i].updateSun();
        }
    }

    /**
     * this method is responsible for preventing any two seed packets (or removal button) from being selected at the
     * same time. If one of the seedPackets has already been selected, another one cannot be selected. If none are collected,
     * then any seedPacket can be selected again.
     * @return boolean determining the validity of a selection to seedPacket.
     */
    public boolean preventDoubleChoicePacket(){
        int threshold = 0;
        if (!this.readyToRemove) {
            for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++) {
                if (this.seedPackets[i].isSeedSelected()) {
                    this.somePacketSelected = true;
                    threshold++;
                }
            }
            if (threshold == 0){
                this.somePacketSelected = false;
            }
        }
        return this.somePacketSelected;
    }

    /**
     * method responsible for removal boolean. Checks that no other seed packet has been selected to actually be
     * selected. If selection is valid, sets boolean readyToRemove to true, and is used later in the mouseClickHandler
     * method. At the end, updates the remove button color for good GUI.
     */
    private void removeTrueFalse() {
        if (this.isPlaying) {
            if (!this.isPaused) {
                if (!this.somePacketSelected) {
                    if (!this.readyToRemove) {
                        this.readyToRemove = true;
                        this.somePacketSelected = true;
                    }
                } else {
                    if (this.readyToRemove) {
                        this.readyToRemove = false;
                        this.somePacketSelected = false;
                    }
                }
            }
        }
        this.checkRemoveColor();
    }

    /**
     * updates color of the remove button based on whether a removal will take place on the next click (dependent on the
     * readyToRemove boolean). If ready to remove, button changes to green to indicate selection. If not, buttons changes
     * to beige.
     */
    private void checkRemoveColor(){
        if (this.readyToRemove){
            this.removeButton.setStyle("-fx-background-color: #00ff00");
        } else {
            this.removeButton.setStyle("-fx-background-color: #e3b44f");
        }
    }

    /**
     * this method finds the chosen seed packet. Iterates through all seedPackets and finds which one has been selected.
     */
    private void findChosenSeedPacket(){
        for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++) {
            if (this.seedPackets[i].isSeedSelected()) {
                this.chosenPacket = this.seedPackets[i];
            }
        }
    }

    /**
     * this method handles all mouse clicks made on the lawn. First, the method validates whether the click
     * was in the lawn. If not, the method considers the click invalid. The method then asks to find the chosen seed
     * packet. Depending on the packet, the method will then add a corresponding plant onto the lawn at the location of
     * the mouse click. The methods to interpret the mouse click locations into a valid plant placement area is elided
     * in the Lawn class. The method then updates the total sun, resets the packet and its color, resets the packet
     * selection boolean, updates the total sun display, and updates the sun for each seedPacket. In the case that the
     * player is attempting to remove a plant, the method will simply remove the plant based on the mouse location and
     * update the remove boolean and button color. The validation methods for the generation and removal of plants is
     * elided in the lawn class.
     * @param e mouse click
     * @param gamepane passed to handle graphics
     */
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
                                    for (int i = 0; i < Constants.SEED_PACKET_NUMBER; i++){
                                        this.seedPackets[i].updateSun();
                                    }
                                }
                            }
                        }
                        if (this.readyToRemove) {
                            if (this.lawn.checkDeletionValid(MouseX, MouseY)) {
                                this.lawn.getPlant(MouseX, MouseY).removePlant(gamepane);
                                this.readyToRemove = false;
                                this.checkRemoveColor();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * this method checks whether the game is over. If the game is over, the method stops all timelines and adds the
     * gameOverLabel and sets the isPlaying boolean to false.
     * @param root passed to graphically add the gameOverLabel
     */
    private void checkGameOver(Pane root){
        if (this.lawn.isGameOver()){
            this.stopTimelines();
            this.lawn.stopTimelines();
            this.stopSuns();
            root.getChildren().add(this.gameOverLabel);
            this.isPlaying = false;
        }
    }

    /**
     * this method is responsible for pausing and resuming the game. If the game needs to be paused, the method stops
     * all timelines and adds the pausedLabel. If the game needs to be resumed, the method resumes all timelines and
     * removes the pausedLabel.
     * @param root passed to graphically add the pausedLabel.
     */
    private void pauseGame(Pane root) {
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

    /**
     * this method is called when the game has been won. It will stop all timelines and add the victoryLabel. It will
     * set the isPlaying boolean to false.
     * @param root passed to graphically add the victoryLabel.
     */
    public void gameWon(Pane root){
        this.stopTimelines();
        this.lawn.stopTimelines();
        this.stopSuns();
        root.getChildren().add(this.victoryLabel);
        this.isPlaying = false;
    }

    /**
     * this method is responsible for updating the zombie count display.
     */
    public void updateZombieCountDisplay(){
        this.zombieCountLabel.setText("Zombies To Defeat: " + this.lawn.getZombieCount());
    }

    /**
     * this method is responsible for stopping all sun from dropping when the game is paused, over, etc.
     */
    private void stopSuns(){
        for (Sun currentSun : this.sunArrayList){
            currentSun.stopTimeline();
        }
    }

    /**
     * this method is responsible for resuming all sun after the game is resumed.
     */
    private void startSuns(){
        for (Sun currentSun : this.sunArrayList){
            currentSun.playTimeline();
        }
    }
}
