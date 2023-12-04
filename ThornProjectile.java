package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ThornProjectile {
    private Circle thorn;
    private double lastAngle;
    public ThornProjectile(int X, int Y, Pane root){
        this.lastAngle = 0;
        this.thorn = new Circle(X, Y, Constants.PEA_RADIUS, Color.PINK);
        root.getChildren().add(this.thorn);
    }
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.thorn);
    }

    public void home(int destinationX, int destinationY) {
        int maxMagnitude = 2;
        double angleDis = Math.toRadians(0.4);
        double mustMoveX = 0;
        double mustMoveY = 0;
        double angle = this.findAngle(destinationX, destinationY);
        System.out.println(Math.toDegrees(angle));
        if (angle > this.lastAngle + angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle + angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle + angleDis));
            this.lastAngle = this.lastAngle + angleDis;
        }
        else if (angle < this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - angleDis));
            this.lastAngle = this.lastAngle - angleDis;
        }
        else if (angle < this.lastAngle + angleDis || angle > this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(angle));
            mustMoveY = (maxMagnitude * Math.sin(angle));
            this.lastAngle = angle;
        }
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);

    }
    private double findAngle(int destinationX, int destinationY) {
        double angle = 0;
        double displacementX;
        double displacementY;
        if (destinationX > this.thorn.getCenterX() && destinationY > this.thorn.getCenterY()) {
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = Math.atan(displacementY / displacementX);
        } else if (destinationX < this.thorn.getCenterX() && destinationY > this.thorn.getCenterY()) {
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = Math.toRadians(180) - Math.atan(displacementY / displacementX);
        } else if (destinationX < this.thorn.getCenterX() && destinationY < this.thorn.getCenterY()){
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = Math.toRadians(180) + Math.atan(displacementY / displacementX);
        } else if (destinationX > this.thorn.getCenterX() && destinationY < this.thorn.getCenterY()){
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = Math.toRadians(360) - Math.atan(displacementY / displacementX);
        }
        return angle;
    }
    public void move(){
        this.thorn.setCenterX(this.thorn.getCenterX() + 2);
    }

    public boolean checkOutOfBounds(){
        boolean OutOfBounds = false;
        if (this.thorn.getCenterX() > Constants.SCENE_WIDTH + Constants.PEA_RADIUS){
            OutOfBounds = true;
        if (this.thorn.getCenterY() < 0 || this.thorn.getCenterY() > Constants.SCENE_HEIGHT)    {
            OutOfBounds = true;
        }
        }
        return OutOfBounds;
    }
    public int getX(){
        return (int) this.thorn.getCenterX();
    }
    public int getY(){
        return (int) this.thorn.getCenterX();
    }
    public boolean didCollide(int X, int Y){
        return this.thorn.intersects(X, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
    }
}
