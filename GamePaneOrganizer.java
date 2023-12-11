package indy;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * PaneOrganizer Class. Responsible for organizing panes that will be responsible for graphical handling of gameplay.
 */
public class GamePaneOrganizer {
    private BorderPane root;
    private Pane gamePane;
    private HBox buttonPane;
    private VBox menuPane;

    /**
     * main constructor. Creates a new borderPane as a root. Restarts menu.
     */
    public GamePaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #705301");
        this.restartMenu();
    }

    /**
     * getter/accessor method. returns root.
     * @return root BorderPane.
     */
    public Pane getRoot(){
        return this.root;
    }

    /**
     * public method to remove menu after selected.
     */
    public void removeMenu(){
        this.root.getChildren().remove(this.menuPane);
    }

    /**
     * adds Menu to root.
     */
    private void addMenu(){
        new Menu(this.gamePane, this.buttonPane, this.menuPane, this);
        this.root.setLeft(this.menuPane);
    }

    /**
     * restarts menu and all game panes. Used when Menu button is clicked.
     */
    public void restartMenu(){
        this.gamePane = new Pane();
        this.buttonPane = new HBox();
        this.menuPane = new VBox();
        this.root.setCenter(this.gamePane);
        this.root.setTop(this.buttonPane);
        this.addMenu();
    }
}
