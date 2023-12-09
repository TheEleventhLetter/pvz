package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ThornProjectile {
    private Circle thorn;
    private double lastAngle;
    private double lastCalculatedAngle;
    private double angle;
    private double angleDisplacement;
    private double angleChange;
    public ThornProjectile(int X, int Y, Pane root){
        this.lastAngle = 0;
        this.angleDisplacement = 0;
        this.lastCalculatedAngle = 0;
        this.thorn = new Circle(X, Y, Constants.PEA_RADIUS, Color.PINK);
        root.getChildren().add(this.thorn);
    }
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.thorn);
    }

    public void home(int destinationX, int destinationY) {
        this.homeProto3(destinationX, destinationY);
    }

    public void resetAngle(int destinationX, int destinationY){
        this.angle = this.findRelative(this.angle);
        //this.lastAngle = this.findRelative(this.lastAngle);
    }
    private double findRelative(double angle){
        double newAngle = 0;
        if (angle < Math.toRadians(180) && angle > Math.toRadians(-180)){
            newAngle = angle;
        } else if (angle > Math.toRadians(180)){
            newAngle = -Math.toRadians(360) + angle;
        } else if (angle < Math.toRadians(-180)){
            newAngle = Math.toRadians(360) + angle;
        }
        return newAngle;
    }
    public void homeProto3(int destinationX, int destinationY) {
        int maxMagnitude = 2;
        this.angleChange = Math.toRadians(2);
        double mustMoveX = 0;
        double mustMoveY = 0;
        this.angle = this.findAngleProto3(destinationX, destinationY);
        System.out.println(Math.toDegrees(this.angleDisplacement));


        if (this.angle > this.lastAngle + this.angleChange){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle + this.angleChange));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle + this.angleChange));
            this.lastAngle = this.lastAngle + this.angleChange;
            this.angleDisplacement = this.angleChange;
        }
        else if (this.angle < this.lastAngle - this.angleChange){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - this.angleChange));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - this.angleChange));
            this.lastAngle = this.lastAngle - this.angleChange;
            this.angleDisplacement = -this.angleChange;
        }
        else if (this.angle < this.lastAngle + this.angleChange || this.angle > this.lastAngle - this.angleChange){
            mustMoveX = (maxMagnitude * Math.cos(this.angle));
            mustMoveY = (maxMagnitude * Math.sin(this.angle));
            this.angleDisplacement =  this.angle - this.lastAngle;
            this.lastAngle = this.angle;
        }
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);
        this.lastCalculatedAngle = this.angle;
    }
    private double findAngleProto3(int destinationX, int destinationY){
        double angle = 0;
        double angle1 = this.calcNegativeAngle(destinationX, destinationY);
        double angle2 = this.calcPositiveAngle(destinationX, destinationY);

        if (this.lastCalculatedAngle < Math.toRadians(180) - this.angleChange && lastCalculatedAngle > Math.toRadians(-180) + this.angleChange) {
            if (Math.abs(angle1) < Math.abs(angle2)) {
                angle = angle1;
            } else if (Math.abs(angle2) < Math.abs(angle1)) {
                angle = angle2;
            } else {
                angle = angle1;
            }
        }
        if (this.lastCalculatedAngle > Math.toRadians(180) - this.angleChange) {
            if (this.angleDisplacement > 0) {
                angle = this.calcPositiveAngle(destinationX, destinationY);
            } else if (this.angleDisplacement < 0){
                angle = this.calcPositiveAngle(destinationX, destinationY);
            } else {
                angle = this.lastCalculatedAngle;
            }
        } else if (this.lastCalculatedAngle < Math.toRadians(-180) + this.angleChange) {
            if (this.angleDisplacement < 0) {
                angle = this.calcNegativeAngle(destinationX, destinationY);
            } else if (this.angleDisplacement > 0){
                angle = this.calcNegativeAngle(destinationX, destinationY);
            } else {
                angle = this.lastCalculatedAngle;
            }
        }

        return angle;
    }


    private double calcPositiveAngle(int destinationX, int destinationY){
        double displacementX;
        double displacementY;
        double angle = 0;
        if (this.isFirstQuadrant(destinationX, destinationY)){
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = Math.toRadians(360) - Math.atan(displacementY / displacementX);
        } else if (this.isSecondQuadrant(destinationX, destinationY)){
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = Math.toRadians(180) + Math.atan(displacementY / displacementX);
        } else if (this.isThirdQuadrant(destinationX, destinationY)){
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = Math.toRadians(180) - Math.atan(displacementY / displacementX);
        } else if (this.isFourthQuadrant(destinationX, destinationY)){
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = Math.atan(displacementY / displacementX);
        }
        return angle;
    }
    private double calcNegativeAngle(int destinationX, int destinationY){
        double displacementX;
        double displacementY;
        double angle = 0;
        if (this.isFirstQuadrant(destinationX, destinationY)){
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = -Math.atan(displacementY / displacementX);
        } else if (this.isSecondQuadrant(destinationX, destinationY)){
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = this.thorn.getCenterY() - (destinationY + ((double) Constants.LAWN_WIDTH / 2));
            angle = -(Math.toRadians(180) - Math.atan(displacementY / displacementX));
        } else if (this.isThirdQuadrant(destinationX, destinationY)){
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = -(Math.toRadians(180) + Math.atan(displacementY / displacementX));
        } else if (this.isFourthQuadrant(destinationX, destinationY)){
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = -(Math.toRadians(360) - Math.atan(displacementY / displacementX));
        }
        return angle;
    }
    private boolean isFirstQuadrant(int X, int Y){
        if (X > this.thorn.getCenterX() && Y > this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    private boolean isSecondQuadrant(int X, int Y){
        if (X < this.thorn.getCenterX() && Y > this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    private boolean isThirdQuadrant(int X, int Y){
        if (X < this.thorn.getCenterX() && Y < this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    private boolean isFourthQuadrant(int X, int Y){
        if (X > this.thorn.getCenterX() && Y < this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    public void move(){
        double mustMoveX = (2 * Math.cos(this.angle));
        double mustMoveY = (2 * Math.sin(this.angle));
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);
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
