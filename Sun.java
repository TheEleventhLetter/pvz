package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Sun class. Handles graphical and logical components of the Sun currency.
 */

public class Sun {
    /**
     * Creates two instance variables. Sun is a Javafx ImageView that passes in a relative url path to present a graphical
     * image. Timeline is responsible for dropping the sun.
     */
    private ImageView sun;
    private Timeline timeline;

    /**
     * main constructor of sun. Graphically instantiates sun and sets up timeline. Calls for handler when clicked on.
     * @param X determines X position of sun when generated
     * @param Y determines Y position of sun when generated
     * @param root passed to graphically add usn
     * @param myGame passed for association (used to add sun to total sun counter).
     */
    public Sun(int X, int Y, Pane root, Game myGame){
        this.sun = new ImageView(new Image("indy/PVZ_Sun.png"));
        this.sun.setFitWidth(Constants.SUN_RADIUS * 3);
        this.sun.setFitHeight(Constants.SUN_RADIUS * 3);
        this.sun.setX(X - (Constants.SUN_RADIUS * 3));
        this.sun.setY(Y - (Constants.SUN_RADIUS * 3));
        root.getChildren().add(this.sun);
        this.setUpDroppingTimeline();
        this.sun.setOnMouseClicked((MouseEvent e) -> this.addSunCost(root, myGame));
    }

    /**
     * Method sets up timeline. Handles the dropping of sun. Takes a random Y coordinate as the stopping threshold so
     * the sun does not fall any further.
     */
    private void setUpDroppingTimeline(){
        int randY = ThreadLocalRandom.current().nextInt(Constants.LAWN_WIDTH * 2, Constants.SCENE_HEIGHT - Constants.SUN_RADIUS);
        KeyFrame kf = new KeyFrame(Duration.millis(100), (ActionEvent e) -> this.drop(randY));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * method drops the sun graphically. Checks the sun's Y coordinate and makes sure sun stops at that location
     * @param randY Y coordinate at which the sun will stop falling.
     */
    private void drop(int randY){
        this.sun.setY(this.sun.getY() + 2);
        this.checkStop(randY);
    }

    /**
     * method adds sun to total sun cost in game class. Adds value to total sun value in game class before graphically
     * removing the sun and  logically from the sunArrayList in the game class
     * @param root passed to graphically remove sun
     * @param myGame passed for association.
     */
    private void addSunCost(Pane root, Game myGame){
        if (myGame.getIsPlaying()){
            if (!myGame.getIsPaused()) {
                myGame.addToTotalSun();
                root.getChildren().remove(this.sun);
                myGame.removeFromSunList(this);
            }
        }
    }

    /**
     * method stops sun from dropping farther than its stopping threshold Y coordinate.
     * @param randY Y coordinate at which sun should stop
     */
    private void checkStop(int randY){
        if (this.sun.getY() - (Constants.SUN_RADIUS * 3) > randY){
            this.timeline.stop();
        }
    }

    /**
     * method stops timeline of sun
     */
    public void stopTimeline(){
        this.timeline.stop();
    }

    /**
     * method plays/resumes timeline of sun
     */
    public void playTimeline(){
        this.timeline.play();
    }

}
