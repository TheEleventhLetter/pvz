package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * PeaProjectile Class. Responsible for logical and graphical handling of the Pea, main attacking method of the
 * PeaShooter.
 */
public class PeaProjectile {
    /**
     * Creates an instance variable of a circle that graphically represents the pea.
     */
    private Circle pea;

    /**
     * main constructor of peaProjectile. Instantiates pea graphically.
     * @param X X position of pea for generation
     * @param Y Y position of pea for generation
     * @param root passed to graphically add pea.
     */
    public PeaProjectile(int X, int Y, Pane root){
        this.pea = new Circle(X, Y, Constants.PEA_RADIUS, Color.LAWNGREEN);
        root.getChildren().add(this.pea);
    }

    /**
     * Method removes pea graphically.
     * @param root passed to graphically remove pea
     */
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.pea);
    }

    /**
     * method moves pea based on displacement.
     * @param displacement distance of pea movement
     */
    public void move(int displacement){
        this.pea.setCenterX(this.pea.getCenterX() + displacement);
    }

    /**
     * method checks if a pea is out of bounds. Returns a boolean whether or not pea is out of bounds.
     * @return true for out of bounds, false for on screen.
     */
    public boolean checkOutOfBounds(){
        boolean OutOfBounds = false;
        if (this.pea.getCenterX() > Constants.SCENE_WIDTH + Constants.PEA_RADIUS){
            OutOfBounds = true;
        }
        return OutOfBounds;
    }

    /**
     * getter/accessor method that returns pea's X position
     * @return Pea's X position int
     */
    public int getX(){
        return (int) this.pea.getCenterX();
    }

    /**
     * getter/accessor method that returns pea's Y position
     * @return Pea's Y position int
     */
    public int getY(){
        return (int) this.pea.getCenterX();
    }

    /**
     * method checks if a pea collided with a zombie. Returns a boolean to determine collision or not.
     * @param X X position of zombie used to check collision
     * @param Y Y position of zombie used to check collision
     * @return boolean. True for collided, false for no collision
     */
    public boolean didCollide(int X, int Y){
        return this.pea.intersects(X, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
    }

}
