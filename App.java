package indy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * It's time for Indy! This is the main class to get things started.
 *
 * This is the highest level class. We call an instance of Pane Organizer and get the application started.
 * GO DEFEND YOUR LAWN!!
 */

public class App extends Application {
    @Override
    public void start(Stage stage) {
        GamePaneOrganizer organizer = new GamePaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.DISPLAY_SCENE_WIDTH, Constants.DISPLAY_SCENE_HEIGHT);
        scene.setFill(Color.GRAY);
        stage.setScene(scene);
        stage.setTitle("Plants Vs Zombies!!");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args); // launch is a method inherited from Application
    }
}
