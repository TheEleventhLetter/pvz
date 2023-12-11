package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
/**
 * SunFlower Seed Packet Class. Manages all graphical and logical components of the seedPacket.
 */
public class SunFlowerSeedPacket implements SeedPacket{
    /**
     * creates 4 instance variables. sunFlowerSeedSelected determines whether this current packet has been selected.
     * TotalSun represents the total sun available. Game is set up for association. sunFlowerSeedPacket is the graphical
     * button.
     */
    private boolean sunFlowerSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button sunFlowerSeedPacket;
    /**
     * main constructor of the SunFlower Seed Packet. Graphically sets up packet as button. Calls for handler when button
     * is clicked.
     * @param root passed to graphically handle button
     * @param mygame passed to set up association
     */
    public SunFlowerSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.sunFlowerSeedSelected = false;
        this.sunFlowerSeedPacket = new Button("SunFlower");
        this.sunFlowerSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.sunFlowerSeedPacket);
        this.sunFlowerSeedPacket.setOnAction((ActionEvent e) -> this.sunFlowerSeedSelectChecker(this.TotalSun));
    }
    /**
     * method checks if the seed packet has been selected. Makes sure that no other seed packet is selected in order for
     * the SunFlower Seed Packet to be selected. Can select and deselect. Updates button Color after checking selection.
     * @param totalSun passed to check if a plant can be selected and placed. Compares plant cost to total cost.
     */
    private void sunFlowerSeedSelectChecker(int totalSun) {
        if (this.myGame.getIsPlaying()) {
            if (!this.myGame.getIsPaused()) {
                if (!this.sunFlowerSeedSelected) {
                    if (!this.myGame.preventDoubleChoicePacket()) {
                        if (totalSun >= Constants.SUNFLOWER_COST) {
                            this.sunFlowerSeedSelected = true;
                        }
                    }
                } else {
                    this.sunFlowerSeedSelected = false;
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
        if (this.sunFlowerSeedSelected){
            this.sunFlowerSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.sunFlowerSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    /**
     * this method is part of the seed packet interface.
     * method returns whether a seed has been selected.
     * @return true for seed selected, false for otherwise
     */
    @Override
    public boolean isSeedSelected(){
        return this.sunFlowerSeedSelected;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns plant number of SunFlower so the lawn can determine what plant to place down.
     * @return integer corresponding to SunFlower Plant number.
     */
    @Override
    public int getPlantNumber(){
        return Constants.SUNFLOWER_NUMBER;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns cost of plant to deduct from total sun in lawn.
     * @return SunFlower's cost.
     */
    @Override
    public int myCost(){
        return Constants.SUNFLOWER_COST;
    }
    /**
     * this method is part of the seed packet interface.
     * method resets Seed Packet when it has been used.
     */
    @Override
    public void reset(){
        this.sunFlowerSeedSelected = false;
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
