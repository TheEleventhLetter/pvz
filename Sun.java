package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Sun {
    private ImageView sun;
    private Timeline timeline;

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
    private void setUpDroppingTimeline(){
        int randY = ThreadLocalRandom.current().nextInt(Constants.LAWN_WIDTH * 2, Constants.SCENE_HEIGHT - Constants.SUN_RADIUS);
        KeyFrame kf = new KeyFrame(Duration.millis(100), (ActionEvent e) -> this.drop(randY));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }
    private void drop(int randY){
        this.sun.setY(this.sun.getY() + 2);
        this.checkStop(randY);
    }
    private void addSunCost(Pane root, Game myGame){
        if (myGame.getIsPlaying()){
            if (!myGame.getIsPaused()) {
                myGame.addToTotalSun();
                root.getChildren().remove(this.sun);
                myGame.removeFromSunList(this);
            }
        }
    }
    private void checkStop(int randY){
        if (this.sun.getY() - (Constants.SUN_RADIUS * 3) > randY){
            this.timeline.stop();
        }
    }
    public void stopTimeline(){
        this.timeline.stop();
    }
    public void playTimeline(){
        this.timeline.play();
    }

}
