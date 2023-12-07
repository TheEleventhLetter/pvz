package indy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class GamePaneOrganizer {
    private BorderPane root;
    private VBox menuPane;

    public GamePaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #11d3f5");
        Pane gamePane = new Pane();
        HBox buttonPane = new HBox();
        this.menuPane = new VBox();
        this.root.setCenter(gamePane);
        this.root.setTop(buttonPane);
        this.root.setLeft(this.menuPane);
        new Menu(gamePane, buttonPane, this.menuPane, this);
    }

    public Pane getRoot(){
        return this.root;
    }
    public void removeMenu(){
        this.root.getChildren().remove(this.menuPane);
    }
}
