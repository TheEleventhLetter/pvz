package indy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Sun {
    private Circle sun;

    public Sun(int X, int Y, Pane root){
        this.sun = new Circle(X, Y, 20, Color.YELLOW);
        root.getChildren().add(this.sun);
        this.setUpDroppingTimeline();
    }
    private void setUpDroppingTimeline(){
        KeyFrame kf = new KeyFrame(Duration.millis(30), (ActionEvent e) -> this.drop());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void drop(){
        this.sun.setCenterY(this.sun.getCenterY() + 2);
    }
}
