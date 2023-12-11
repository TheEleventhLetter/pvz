package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * CherryBomb Seed Packet Class. Manages all graphical and logical components of the seedPacket.
 */
public class CherryBombSeedPacket implements SeedPacket{
    /**
     * creates 4 instance variables. cherryBombSeedSelected determines whether this current packet has been selected.
     * TotalSun represents the total sun available. Game is set up for association. cherryBombSeedPacket is the graphical
     * button.
     */
    private boolean cherryBombSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button cherryBombSeedPacket;

    /**
     * main constructor of the CherryBomb Seed Packet. Graphically sets up packet as button. Calls for handler when button
     * is clicked.
     * @param root passed to graphically handle button
     * @param mygame passed to set up association
     */
    public CherryBombSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.cherryBombSeedSelected = false;
        this.cherryBombSeedPacket = new Button("CherryBomb \n 150", new ImageView(new Image("indy/Cherrybomb_Sprite.png",
                25, 25, true, true)));
        this.cherryBombSeedPacket.setContentDisplay(ContentDisplay.TOP);
        this.cherryBombSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.cherryBombSeedPacket);
        this.cherryBombSeedPacket.setOnAction((ActionEvent e) -> this.cherryBombSeedSelectChecker(this.TotalSun));
    }
    /**
     * method checks if the seed packet has been selected. Makes sure that no other seed packet is selected in order for
     * the CherryBomb Seed Packet to be selected. Can select and deselect. Updates button Color after checking selection.
     * @param totalSun passed to check if a plant can be selected and placed. Compares plant cost to total cost.
     */
    private void cherryBombSeedSelectChecker(int totalSun) {
        if (this.myGame.getIsPlaying()) {
            if (!this.myGame.getIsPaused()) {
                if (!this.cherryBombSeedSelected) {
                    if (!this.myGame.preventDoubleChoicePacket()) {
                        if (totalSun >= Constants.CHERRYBOMB_COST) {
                            this.cherryBombSeedSelected = true;
                        }
                    }
                } else {
                    this.cherryBombSeedSelected = false;
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
        if (this.cherryBombSeedSelected){
            this.cherryBombSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.cherryBombSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    /**
     * this method is part of the seed packet interface.
     * method returns whether a seed has been selected.
     */
    @Override
    public boolean isSeedSelected(){
        return this.cherryBombSeedSelected;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns plant number of CherryBomb so the lawn can determine what plant to place down.
     */
    @Override
    public int getPlantNumber(){
        return Constants.CHERRYBOMB_NUMBER;
    }
    /**
     * this method is part of the seed packet interface.
     * method returns cost of plant to deduct from total sun in lawn.
     */
    @Override
    public int myCost(){
        return Constants.CHERRYBOMB_COST;
    }
    /**
     * this method is part of the seed packet interface.
     * method resets Seed Packet when it has been used.
     */
    @Override
    public void reset(){
        this.cherryBombSeedSelected = false;
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
