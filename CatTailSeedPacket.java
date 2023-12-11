package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Cattail Seed Packet Class. Manages all graphical and logical components of the seedPacket.
 */
public class CatTailSeedPacket implements SeedPacket{
    /**
     * creates 4 instance variables. catTailSeedSelected determines whether this current packet has been selected.
     * TotalSun represents the total sun available. Game is set up for association. catTailSeedPacket is the graphical
     * button.
     */
    private boolean catTailSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button catTailSeedPacket;

    /**
     * main constructor of the Cattail Seed Packet. Graphically sets up packet as button. Calls for handler when button
     * is clicked.
     * @param root passed to graphically handle button
     * @param mygame passed to set up association
     */
    public CatTailSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.catTailSeedSelected = false;
        this.catTailSeedPacket = new Button("CatTail");
        this.catTailSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.catTailSeedPacket);
        this.catTailSeedPacket.setOnAction((ActionEvent e) -> this.catTailSeedSelectChecker(this.TotalSun));
    }

    /**
     * method checks if the seed packet has been selected. Makes sure that no other seed packet is selected in order for
     * the Cattail Seed Packet to be selected. Can select and deselect. Updates button Color after checking selection.
     * @param totalSun passed to check if a plant can be selected and placed. Compares plant cost to total cost.
     */
    private void catTailSeedSelectChecker(int totalSun) {
        if (this.myGame.getIsPlaying()) {
            if (!this.myGame.getIsPaused()) {
                if (!this.catTailSeedSelected) {
                    if (!this.myGame.preventDoubleChoicePacket()) {
                        if (totalSun >= Constants.CATTAIL_COST) {
                            this.catTailSeedSelected = true;
                        }
                    }
                } else {
                    this.catTailSeedSelected = false;
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
        if (this.catTailSeedSelected){
            this.catTailSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.catTailSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    /**
     * this method is part of the seed packet interface.
     * method returns whether a seed has been selected.
     */
    @Override
    public boolean isSeedSelected(){
        return this.catTailSeedSelected;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns plant number of Cattail so the lawn can determine what plant to place down.
     */
    @Override
    public int getPlantNumber(){
        return Constants.CATTAIL_NUMBER;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns cost of plant to deduct from total sun in lawn.
     */
    @Override
    public int myCost(){
        return Constants.CATTAIL_COST;
    }
    /**
     * this method is part of the seed packet interface.
     * method resets Seed Packet when it has been used.
     */
    @Override
    public void reset(){
        this.catTailSeedSelected = false;
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
