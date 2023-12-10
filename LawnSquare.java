package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LawnSquare {
    private Rectangle lawnSquare;

    public LawnSquare(int X, int Y, Color color, Pane root){
        this.lawnSquare = new Rectangle(X, Y, Constants.LAWN_WIDTH, Constants.LAWN_WIDTH);
        this.lawnSquare.setFill(color);
        this.lawnSquare.setStroke(Color.BLACK);
        root.getChildren().add(this.lawnSquare);

    }
}
