package indy;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Thorn Projectile Class. Handles all graphical and logical aspects of the Thorn Projectile, the CatTail plant's form
 * of attacking.
 */
public class ThornProjectile {
    /**
     * Creates 6 instance variables. thorn is the graphical representation of the thorn projectile. Additionally, we have
     * double instance variables, each representing different things. lastAngle represents the final angle used by the
     * thorn to move. lastCalculatedAngle represents the last angle calculated that the thorn should move towards.
     * Angle represents the current angle being used by the thorn to move. angleDisplacement represents the angular
     * displacement from the last angle to the current angle. Finally, angleChange refers to the maximum angular displacement
     * that should be permitted by the thorn.
     */
    private Circle thorn;
    private double lastAngle;
    private double lastCalculatedAngle;
    private double angle;
    private double angleDisplacement;
    private double angleChange;

    /**
     * main constructor of ThornProjectile. Sets lastAngle, angularDisplacement, and lastCalculatedAngle to zero. It
     * is important to note that angle is considered to be the angle from the horizontal facing right. Also, graphically
     * instantiates thorn.
     * @param X determines x position of thorn when generated
     * @param Y determines y position of thorn when generated
     * @param root passed to graphically add thorn
     */
    public ThornProjectile(int X, int Y, Pane root){
        this.lastAngle = 0;
        this.angleDisplacement = 0;
        this.lastCalculatedAngle = 0;
        this.thorn = new Circle(X, Y, Constants.PEA_RADIUS, Color.DARKGOLDENROD);
        root.getChildren().add(this.thorn);
    }

    /**
     * method removes thorn graphically
     * @param root passed to graphically remove thorn
     */
    public void removeGraphic(Pane root){
        root.getChildren().remove(this.thorn);
    }

    /**
     * this method resents the angle. It will convert the current angle to a relative angle.
     */
    public void resetAngle(){
        this.angle = this.findRelative(this.angle);

    }

    /**
     * this method will convert an angle to its relative. Note that relative here does not mean an angle in between 0-90
     * but an angle in between 0-180 in magnitude. While the angle is within a 180 degree range, method will return angle
     * as is. If angle is over a 180 degree range, method will return reduced angle.
     * @param angle angle to be converted.
     * @return converted angle.
     */
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

    /**
     * this method is the homing method. By setting a maximumMagnitude for the thorn, we can change the speed at which
     * the thorn moves. We also set the maximum possible angular change permitted. mustMoveX and mustMoveY represent
     * the horizontal and vertical displacement the thorn must move. The method first updates the main angle, the angle
     * between the projectile and the targeted zombie. To prevent irregular movement and create a more smooth path,
     * the method will compare angles with the last angle, and only permit change in small increments of an angle,
     * dependent on the this.angleChange variable. Finally, the thorn will move in the calculated X and Y displacement
     * and repeat until the thorn is graphically removed from collision or out of bounds.
     * @param destinationX the X coordinate the thorn should be moving towards
     * @param destinationY the Y coordinate the thorn should be moving towards
     */
    public void home(int destinationX, int destinationY) {
        int maxMagnitude = 2;
        this.angleChange = Math.toRadians(2);
        double mustMoveX = 0;
        double mustMoveY = 0;
        this.angle = this.findAngle(destinationX, destinationY);


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

    /**
     * this method calculates the angle from the thorn to the targeted zombie. First it will calculate the angle in
     * negative (counter-clockwise motion) and in positive (clockwise motion). It will compare the magnitude of each angle
     * and determine the smallest. This will help the thorn decide the shortest path to its target. In the case that each
     * are equally distant, the thorn will move counter-clockwise (negative angle). However, in the case that the angle
     * is close to approaching 180 and the angular displacement indicates that the angle is opening up, the method will
     * automatically use the corresponding positive or negative angle to allow for angles above 180. This prevents the
     * thorn from moving in a sin wave.
     * @param destinationX X coordinate of zombie being targeted
     * @param destinationY Y coordinate of zombie being targeted
     * @return calculated Angle
     */
    private double findAngle(int destinationX, int destinationY){
        double angle = 0;
        double angle1 = this.calcNegativeAngle(destinationX, destinationY);
        double angle2 = this.calcPositiveAngle(destinationX, destinationY);

        if (this.lastCalculatedAngle < Math.toRadians(180) - this.angleChange && this.lastCalculatedAngle > Math.toRadians(-180) + this.angleChange) {
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

    /**
     * this method calculates the angle from the thorn to the zombie in clockwise motion.
     * @param destinationX x coordinate of zombie being targeted
     * @param destinationY y coordinate of zombie being targeted
     * @return calculated angle in clockwise motion (positive)
     */
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
    /**
     * this method calculates the angle from the thorn to the zombie in counter-clockwise motion.
     * @param destinationX x coordinate of zombie being targeted
     * @param destinationY y coordinate of zombie being targeted
     * @return calculated angle in counter-clockwise motion (negative)
     */
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

    /**
     * Method determines whether the targeted zombie is in the first quadrant when considering the thorn as the origin.
     * @param X x coordinate of zombie being targeted
     * @param Y y coordinate of zombie being targeted
     * @return true for first quadrant. False for otherwise.
     */
    private boolean isFirstQuadrant(int X, int Y){
        if (X > this.thorn.getCenterX() && Y > this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method determines whether the targeted zombie is in the second quadrant when considering the thorn as the origin.
     * @param X x coordinate of zombie being targeted
     * @param Y y coordinate of zombie being targeted
     * @return true for second quadrant. False for otherwise.
     */
    private boolean isSecondQuadrant(int X, int Y){
        if (X < this.thorn.getCenterX() && Y > this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method determines whether the targeted zombie is in the third quadrant when considering the thorn as the origin.
     * @param X x coordinate of zombie being targeted
     * @param Y y coordinate of zombie being targeted
     * @return true for third quadrant. False for otherwise.
     */
    private boolean isThirdQuadrant(int X, int Y){
        if (X < this.thorn.getCenterX() && Y < this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method determines whether the targeted zombie is in the fourth quadrant when considering the thorn as the origin.
     * @param X x coordinate of zombie being targeted
     * @param Y y coordinate of zombie being targeted
     * @return true for fourth quadrant. False for otherwise.
     */
    private boolean isFourthQuadrant(int X, int Y){
        if (X > this.thorn.getCenterX() && Y < this.thorn.getCenterY()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * this method moves the thorns when there is no target zombie. It is a simplified version of the homing method
     * but will move towards its last calculated angle.
     */
    public void move(){
        double mustMoveX = (2 * Math.cos(this.angle));
        double mustMoveY = (2 * Math.sin(this.angle));
        this.thorn.setCenterX(this.thorn.getCenterX() + mustMoveX);
        this.thorn.setCenterY(this.thorn.getCenterY() + mustMoveY);
    }

    /**
     * this method checks if a thorn is out of bounds.
     * @return true for out of bounds. False if otherwise
     */
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

    /**
     * getter/accessor method. Returns thorn's x location.
     * @return thorn's current x coordinate.
     */
    public int getX(){
        return (int) this.thorn.getCenterX();
    }

    /**
     * getter/accessor method. Returns thorn's y location.
     * @return thorn's current y coordinate.
     */
    public int getY(){
        return (int) this.thorn.getCenterX();
    }

    /**
     * method checks if currentThorn collided with a zombie.
     * @param X x coordinate of zombie
     * @param Y y coordinate of zombie
     * @return true for collision detected. False for otherwise.
     */
    public boolean didCollide(int X, int Y){
        return this.thorn.intersects(X, Y, Constants.ZOMBIE_WIDTH, Constants.LAWN_WIDTH);
    }
}
