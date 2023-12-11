package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * PeaShooter Seed Packet Class. Manages all graphical and logical components of the seedPacket.
 */
public class PeaShooterSeedPacket implements SeedPacket {
    /**
     * creates 4 instance variables. peaShooterSeedSelected determines whether this current packet has been selected.
     * TotalSun represents the total sun available. Game is set up for association. peaShooterSeedPacket is the graphical
     * button.
     */
    private boolean peaShooterSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button peaShooterSeedPacket;
    /**
     * main constructor of the PeaShooter Seed Packet. Graphically sets up packet as button. Calls for handler when button
     * is clicked.
     * @param root passed to graphically handle button
     * @param mygame passed to set up association
     */
    public PeaShooterSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.peaShooterSeedSelected = false;
        this.peaShooterSeedPacket = new Button("PeaShooter \n 100", new ImageView(new Image("indy/Peashooter_Sprite.png",
                25, 25, true, true)));
        this.peaShooterSeedPacket.setContentDisplay(ContentDisplay.TOP);
        this.peaShooterSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.peaShooterSeedPacket);
        this.peaShooterSeedPacket.setOnAction((ActionEvent e) -> this.peaShooterSeedSelectChecker(this.TotalSun));
    }
    /**
     * method checks if the seed packet has been selected. Makes sure that no other seed packet is selected in order for
     * the PeaShooter Seed Packet to be selected. Can select and deselect. Updates button Color after checking selection.
     * @param totalSun passed to check if a plant can be selected and placed. Compares plant cost to total cost.
     */
    private void peaShooterSeedSelectChecker(int totalSun) {
        if (this.myGame.getIsPlaying()) {
            if (!this.myGame.getIsPaused()) {
                if (!this.peaShooterSeedSelected) {
                    if (!this.myGame.preventDoubleChoicePacket()) {
                        if (totalSun >= Constants.PEASHOOTER_COST) {
                            this.peaShooterSeedSelected = true;
                        }
                    }
                } else {
                    this.peaShooterSeedSelected = false;
                }
                this.myGame.preventDoubleChoicePacket();
            }
        }
        this.seedColorChecker();
    }
    /**
     * this method is part of the seed packet interface.
     * method updates the color of the seed packet button depending on boolean instance variable determining if the
     * packet has been selected or not. If selected, updates button to Green. If not, updates button to beige.
     */
    @Override
    public void seedColorChecker(){
        if (this.peaShooterSeedSelected){
            this.peaShooterSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.peaShooterSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }

    /**
     * this method is part of the seed packet interface.
     * method returns whether a seed has been selected.
     * @return true for seed selected, false for otherwise
     */
    @Override
    public boolean isSeedSelected(){
        return this.peaShooterSeedSelected;
    }

    /**
     * this method is part of the seed packet interface.
     * method returns plant number of PeaShooter so the lawn can determine what plant to place down.
     * @return integer corresponding to PeaShooter Plant number.
     */
    @Override
    public int getPlantNumber(){
        return Constants.PEASHOOTER_NUMBER;
    }

    /**
     * this method is part of the seed packet interface.
     * method returns cost of plant to deduct from total sun in lawn.
     * @return PeaShooter's cost.
     */
    @Override
    public int myCost(){
        return Constants.PEASHOOTER_COST;
    }
    /**
     * this method is part of the seed packet interface.
     * method resets Seed Packet when it has been used.
     */
    @Override
    public void reset(){
        this.peaShooterSeedSelected = false;
        this.updateSun();
    }
    /**
     * this method is part of the seed packet interface.
     * method updates the sun count for the seed packet to use for comparison in SeedPacketSelectChecker method.
     */
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }

}
