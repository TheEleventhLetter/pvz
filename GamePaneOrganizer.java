package indy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class GamePaneOrganizer {
    private BorderPane root;
    private Pane gamePane;
    private HBox buttonPane;
    private VBox menuPane;

    public GamePaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #705301");
        this.gamePane = new Pane();
        this.buttonPane = new HBox();
        this.menuPane = new VBox();
        this.root.setCenter(this.gamePane);
        this.root.setTop(this.buttonPane);
        this.addMenu();
    }

    public Pane getRoot(){
        return this.root;
    }
    public void removeMenu(){
        this.root.getChildren().remove(this.menuPane);
    }
    public void addMenu(){
        new Menu(this.gamePane, this.buttonPane, this.menuPane, this);
        this.root.setLeft(this.menuPane);
    }
}
