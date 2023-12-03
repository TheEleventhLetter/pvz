package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PeaProjectile {
    private Circle pea;
    public PeaProjectile(int X, int Y, Pane root){
        this.pea = new Circle(X, Y, Constants.PEA_RADIUS, Color.RED);
        root.getChildren().add(this.pea);
    }
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.pea);
    }
    public void move(int displacement){
        this.pea.setCenterX(this.pea.getCenterX() + displacement);
    }
    public boolean checkOutOfBounds(){
        boolean OutOfBounds = false;
        if (this.pea.getCenterX() > Constants.SCENE_WIDTH + Constants.PEA_RADIUS){
            OutOfBounds = true;
        }
        return OutOfBounds;
    }
    public int getX(){
        return (int) this.pea.getCenterX();
    }
    public int getY(){
        return (int) this.pea.getCenterX();
    }
    public boolean didCollide(int X, int Y){
        return this.pea.intersects(X, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
    }

}
