package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Menu {

    public Menu(Pane gamepane, HBox buttonPane, VBox menuPane, GamePaneOrganizer organizer){
        Button level1 = new Button("Level 1");
        level1.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 1, organizer));
        Button level2 = new Button("Level 2");
        level2.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 2, organizer));
        Button level3 = new Button("Level 3");
        level3.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 3, organizer));
        Button level4 = new Button("Level 4");
        level4.setOnAction((ActionEvent e) -> this.startGame(gamepane, buttonPane, 4, organizer));
        menuPane.getChildren().addAll(level1, level2, level3, level4);


    }
    private void startGame(Pane gamepane, HBox buttonPane, int level, GamePaneOrganizer organizer){
        new Game(gamepane, buttonPane, level);
        organizer.removeMenu();
    }
}
