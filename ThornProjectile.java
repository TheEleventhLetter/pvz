package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ThornProjectile {
    private Circle thorn;
    private double secondLastAngle;
    private double lastAngle;
    private double angle;
    private double angleDisplacement;
    private boolean sameZombie;
    private CatTail parent;
    private Zombie myZombie;
    public ThornProjectile(int X, int Y, Pane root, CatTail myParent){
        this.lastAngle = 0;
        this.secondLastAngle = 0;
        this.angleDisplacement = 0;
        this.parent = myParent;
        this.thorn = new Circle(X, Y, Constants.PEA_RADIUS, Color.PINK);
        root.getChildren().add(this.thorn);
    }
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.thorn);
    }

    public void home(int destinationX, int destinationY) {
        this.homeProto3(destinationX, destinationY);
    }

    private double findAngle(int destinationX, int destinationY) {
        double angle = 0;
        double angle2 = this.calcPositiveAngle(destinationX, destinationY);

        return angle;
    }
    public void resetAngle(){
        System.out.println("SDJKF");
        this.angle = 0;
        this.lastAngle = 0;
    }
    public void homeProto3(int destinationX, int destinationY) {
        int maxMagnitude = 2;
        double angleDis = Math.toRadians(0.8);
        double mustMoveX = 0;
        double mustMoveY = 0;
        this.angle = this.findAngleProto3(destinationX, destinationY);
        if (Math.abs(this.angleDisplacement) > Math.toRadians(6)) {
            if (this.secondLastAngle > 0) {
                this.angle = this.secondLastAngle + Math.toRadians(5);
                this.lastAngle = this.angle;
            } else if (this.secondLastAngle < 0) {
                this.angle = this.secondLastAngle - Math.toRadians(5);
                this.lastAngle = this.angle;
            }
        } else if (this.lastAngle > Math.toRadians(180)) {
            this.angle = Math.toRadians(180) + this.findAngleProto3(destinationX, destinationY);
            this.lastAngle = this.angle;
        } else if (this.lastAngle < Math.toRadians(-180)) {
            this.angle = -Math.toRadians(180) + this.findAngleProto3(destinationX, destinationY);
            this.lastAngle = this.angle;
        }
        this.angleDisplacement = this.lastAngle - this.angle;
        if (this.angle > this.lastAngle + angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle + angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle + angleDis));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = this.lastAngle + angleDis;
        }
        else if (this.angle < this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - angleDis));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = this.lastAngle - angleDis;
        }
        else if (this.angle < this.lastAngle + angleDis || this.angle > this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.angle));
            mustMoveY = (maxMagnitude * Math.sin(this.angle));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = this.angle;
        }
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);
    }
    public void homeProto2(int destinationX, int destinationY){
        int maxMagnitude = 2;
        double angleDis = Math.toRadians(0.8);
        double mustMoveX = 0;
        double mustMoveY = 0;
        double angle = this.findAngleProto2(destinationX, destinationY);
        if (this.isFirstQuadrant(destinationX, destinationY)){
            if (Math.abs(angle) > Math.abs(this.lastAngle) - angleDis){
                mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - angleDis));
                mustMoveY = -(maxMagnitude * Math.sin(this.lastAngle - angleDis));
                this.lastAngle = this.lastAngle - angleDis;
            }
            else if (Math.abs(angle) < Math.abs(this.lastAngle) - angleDis){
                mustMoveX = (maxMagnitude * Math.cos(angle));
                mustMoveY = -(maxMagnitude * Math.sin(angle));
                this.lastAngle = angle;
            }
        } else if (this.isSecondQuadrant(destinationX, destinationY)){
            if (Math.abs(angle) > Math.abs(this.lastAngle) - angleDis){
                mustMoveX = -(maxMagnitude * Math.cos(this.lastAngle - angleDis));
                mustMoveY = -(maxMagnitude * Math.sin(this.lastAngle - angleDis));
                this.lastAngle = this.lastAngle - angleDis;
            }
            else if (Math.abs(angle) < Math.abs(this.lastAngle) - angleDis){
                mustMoveX = -(maxMagnitude * Math.cos(angle));
                mustMoveY = -(maxMagnitude * Math.sin(angle));
                this.lastAngle = angle;
            }
        } else if (this.isThirdQuadrant(destinationX, destinationY)){
            if (Math.abs(angle) > Math.abs(this.lastAngle) - angleDis){
                mustMoveX = -(maxMagnitude * Math.cos(this.lastAngle - angleDis));
                mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - angleDis));
                this.lastAngle = this.lastAngle - angleDis;
            }
            else if (Math.abs(angle) < Math.abs(this.lastAngle) - angleDis){
                mustMoveX = -(maxMagnitude * Math.cos(angle));
                mustMoveY = (maxMagnitude * Math.sin(angle));
                this.lastAngle = angle;
            }
        } else if (this.isFourthQuadrant(destinationX, destinationY)){
            if (Math.abs(angle) > Math.abs(this.lastAngle) - angleDis){
                mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - angleDis));
                mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - angleDis));
                this.lastAngle = this.lastAngle - angleDis;
            }
            else if (Math.abs(angle) < Math.abs(this.lastAngle) - angleDis){
                mustMoveX = (maxMagnitude * Math.cos(angle));
                mustMoveY = (maxMagnitude * Math.sin(angle));
                this.lastAngle = angle;
            }
        }
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);

    }
    public void homeProto1(int destinationX, int destinationY){
        int maxMagnitude = 2;
        double angleDis = Math.toRadians(0.8);
        double mustMoveX = 0;
        double mustMoveY = 0;
        double angle = this.findAngleProto1(destinationX, destinationY);
        this.angleDisplacement = this.lastAngle - angle;
        if (angle > this.lastAngle + angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle + angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle + angleDis));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = this.lastAngle + angleDis;
        }
        else if (angle < this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(this.lastAngle - angleDis));
            mustMoveY = (maxMagnitude * Math.sin(this.lastAngle - angleDis));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = this.lastAngle - angleDis;
        }
        else if (angle < this.lastAngle + angleDis || angle > this.lastAngle - angleDis){
            mustMoveX = (maxMagnitude * Math.cos(angle));
            mustMoveY = (maxMagnitude * Math.sin(angle));
            this.secondLastAngle = this.lastAngle;
            this.lastAngle = angle;
        }
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);

    }
    private double findAngleProto3(int destinationX, int destinationY){
        double angle = 0;
        double angle1 = this.calcNegativeAngle(destinationX, destinationY);
        double angle2 = this.calcPositiveAngle(destinationX, destinationY);
        if (Math.abs(angle1) < Math.abs(angle2)){
            angle = angle1;
        } else if (Math.abs(angle2) < Math.abs(angle1)){
            angle = angle2;
        } else {
            angle = angle1;
        }
        return angle;
    }

    private double findAngleProto2(int destinationX, int destinationY){
        double displacementX;
        double displacementY;
        double angle = 0;
        displacementX = destinationX - this.thorn.getCenterX();
        displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
        if (this.isFirstQuadrant(destinationX, destinationY)){
            angle = Math.atan(-displacementY / displacementX);
        } else if (this.isSecondQuadrant(destinationX, destinationY)){
            angle = Math.atan(-displacementY / -displacementX);
        } else if (this.isThirdQuadrant(destinationX, destinationY)){
            angle = Math.atan(displacementY / -displacementX);
        } else if (this.isFourthQuadrant(destinationX, destinationY)){
            angle = Math.atan(displacementY / displacementX);
        }
        return angle;
    }
    private double findAngleProto1(int destinationX, int destinationY){
        double angle = 0;
        double displacementX;
        double displacementY;
        if (destinationX > this.thorn.getCenterX()) {
            displacementX = destinationX - this.thorn.getCenterX();
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            angle = Math.atan(displacementY / displacementX);
        } else if (destinationX < this.thorn.getCenterX()) {
            displacementX = this.thorn.getCenterX() - destinationX;
            displacementY = (destinationY + ((double) Constants.LAWN_WIDTH / 2)) - this.thorn.getCenterY();
            if (this.lastAngle > 0) {
                System.out.println(this.lastAngle);
                angle = Math.toRadians(180) - Math.atan(displacementY / displacementX);
                if (Math.abs(this.angleDisplacement) > Math.toRadians(10)) {
                    angle = this.secondLastAngle + 5;
                } else if (this.lastAngle > 180) {
                    angle = Math.toRadians(180) + Math.atan(displacementY / displacementX);
                }
            } else if (this.lastAngle < 0) {
                angle = -(Math.toRadians(180) - Math.atan(displacementY / displacementX));
                if (Math.abs(this.angleDisplacement) > Math.toRadians(10)) {
                    angle = this.secondLastAngle - 5;
                } else if (this.lastAngle < -180) {
                    angle = -(Math.toRadians(180) + Math.atan(displacementY / displacementX));
                }
            } else {
                double angle1 = Math.toRadians(180) - Math.atan(displacementY / displacementX);
                double angle2 = -(Math.toRadians(180) - Math.atan(displacementY / displacementX));
                if (Math.abs(angle1) < Math.abs(angle2)) {
                    System.out.println("DOWN");
                    angle = angle1;
                } else if (Math.abs(angle2) < Math.abs(angle1)) {
                    System.out.println("UP");
                    angle = angle2;
                } else {
                    angle = angle1;
                }
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
