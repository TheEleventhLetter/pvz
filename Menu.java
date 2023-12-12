package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Menu class. Does all graphical and logical handling of menu page.
 */
public class Menu {
    /**
     * Creates an instance variable of GamePaneOrganizer. Set up for association
     */
    private GamePaneOrganizer organizer;

    /**
     * main constructor of menu class. Creates a menu with 4 options, each starting a different game with a varying
     * difficulty.
     * @param gamepane passed to graphically handle new game
     * @param buttonPane passed to graphically handle new game
     * @param menuPane passed to graphically add level options
     * @param myOrganizer passed for association
     */
    public Menu(Pane gamepane, HBox buttonPane, VBox menuPane, GamePaneOrganizer myOrganizer){
        this.organizer = myOrganizer;

        Button level1 = new Button("Level 1", new ImageView(new Image("indy/SHRIMP_FRIED_RICE.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level1.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 1));
        Button level2 = new Button("Level 2", new ImageView(new Image("indy/Hampter.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level2.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 2));
        Button level3 = new Button("Level 3", new ImageView(new Image("indy/Josh_Whistle.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level3.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 3));
        Button level4 = new Button("Level 4", new ImageView(new Image("indy/Not_like_the_others.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level4.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 4));
        Button level5 = new Button("Level 5", new ImageView(new Image("indy/Nah_Id_Win.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level5.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 5));
        Button level6 = new Button("Level 6", new ImageView(new Image("indy/Chica_Rizz.png",
                Constants.MENU_BUTTON_IMAGE_SIZE, Constants.MENU_BUTTON_IMAGE_SIZE, true, true)));
        level6.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 6));
        level1.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level2.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level3.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level4.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level5.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level6.setPrefSize(Constants.MENU_BUTTON_WIDTH, Constants.MENU_BUTTON_HEIGHT);
        level1.setStyle(Constants.MENU_BUTTON_FONT_SIZE);
        level2.setStyle(Constants.MENU_BUTTON_FONT_SIZE);
        level3.setStyle(Constants.MENU_BUTTON_FONT_SIZE);
        level4.setStyle(Constants.MENU_BUTTON_FONT_SIZE);
        level5.setStyle(Constants.MENU_BUTTON_FONT_SIZE);
        level6.setStyle(Constants.MENU_BUTTON_FONT_SIZE);

        menuPane.getChildren().addAll(level1, level2, level3, level4, level5, level6);
    }

    /**
     * method starts a game. Creates a new game and removes menu from pane.
     * @param gamepane passed to graphically handle new game
     * @param buttonPane passed to graphically handle new game
     * @param level passed to set difficult of new game
     */
    public void startGame(Pane gamepane, HBox buttonPane, int level){
        Game newGame = new Game(gamepane, buttonPane, level, this);
        this.organizer.removeMenu();
    }

    /**
     * method restarts game. Tells the organizer to restart the menu.
     */
    public void restartGame(){
        this.organizer.restartMenu();
    }
}
