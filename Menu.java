package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Menu {
    private Game newGame;
    private GamePaneOrganizer organizer;

    public Menu(Pane gamepane, HBox buttonPane, VBox menuPane, GamePaneOrganizer OMGorganizer){
        this.organizer = OMGorganizer;

        Button level1 = new Button("Level 1");
        level1.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 1));
        Button level2 = new Button("Level 2");
        level2.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 2));
        Button level3 = new Button("Level 3");
        level3.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 3));
        Button level4 = new Button("Level 4");
        level4.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 4));
        menuPane.getChildren().addAll(level1, level2, level3, level4);
    }
    private void startGame(Pane gamepane, HBox buttonPane, int level){
        this.newGame = new Game(gamepane, buttonPane, level, this);
        this.organizer.removeMenu();
    }
}
