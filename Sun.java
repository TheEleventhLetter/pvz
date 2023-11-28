package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Sun {
    private Circle sun;
    private Timeline timeline;

    public Sun(int X, int Y, Pane root, Game myGame){
        this.sun = new Circle(X, Y, Constants.SUN_RADIUS, Color.YELLOW);
        root.getChildren().add(this.sun);
        this.setUpDroppingTimeline();
        this.sun.setOnMouseClicked((MouseEvent e) -> this.addSunCost(root, myGame));
    }
    private void setUpDroppingTimeline(){
        int randY = ThreadLocalRandom.current().nextInt(Constants.LAWN_WIDTH * 2, Constants.SCENE_HEIGHT - Constants.SUN_RADIUS);
        KeyFrame kf = new KeyFrame(Duration.millis(30), (ActionEvent e) -> this.drop(randY));
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }
    private void drop(int randY){
        this.sun.setCenterY(this.sun.getCenterY() + 2);
        this.checkStop(randY);
    }
    private void addSunCost(Pane root, Game myGame){
        myGame.addToTotalSun();
        root.getChildren().remove(this.sun);
    }
    private void checkStop(int randY){
        if (this.sun.getCenterY() > randY){
            this.timeline.stop();
        }
    }

}
