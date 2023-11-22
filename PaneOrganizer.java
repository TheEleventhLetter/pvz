package indy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class PaneOrganizer {
    private BorderPane root;

    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #11d3f5");
        Pane gamePane = new Pane();
        HBox buttonPane = new HBox();
        this.root.setCenter(gamePane);
        this.root.setTop(buttonPane);
        new Game(gamePane, buttonPane);
    }

    public Pane getRoot(){
        return this.root;
    }
}
