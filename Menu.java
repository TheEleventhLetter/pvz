package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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

        Button level1 = new Button("Level 1");
        level1.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 1));
        Button level2 = new Button("Level 2");
        level2.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 2));
        Button level3 = new Button("Level 3");
        level3.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 3));
        Button level4 = new Button("Level 4");
        level4.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 4));
        Button level5 = new Button("Level 5");
        level5.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 5));
        Button level6 = new Button("Level 6");
        level6.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 6));
        level1.setPrefSize(200, 100);
        level2.setPrefSize(200, 100);
        level3.setPrefSize(200, 100);
        level4.setPrefSize(200, 100);
        level5.setPrefSize(200, 100);
        level6.setPrefSize(200, 100);
        level1.setStyle("-fx-font-size: 2em; ");
        level2.setStyle("-fx-font-size: 2em; ");
        level3.setStyle("-fx-font-size: 2em; ");
        level4.setStyle("-fx-font-size: 2em; ");
        level5.setStyle("-fx-font-size: 2em; ");
        level6.setStyle("-fx-font-size: 2em; ");

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
