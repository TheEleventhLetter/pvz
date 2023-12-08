package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class WalnutSeedPacket implements SeedPacket{
    private boolean walnutSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button walnutSeedPacket;
    public WalnutSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.walnutSeedSelected = false;
        this.walnutSeedPacket = new Button("Walnut");
        this.walnutSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.walnutSeedPacket);
        this.walnutSeedPacket.setOnAction((ActionEvent e) -> this.WalnutSeedSelectChecker(this.TotalSun));
    }
    private void WalnutSeedSelectChecker(int totalSun) {
        if (!this.myGame.getIsPaused()) {
            if (!this.walnutSeedSelected) {
                if (!this.myGame.preventDoubleChoicePacket()) {
                    if (totalSun >= 50) {
                        this.walnutSeedSelected = true;
                    }
                }
            } else {
                this.walnutSeedSelected = false;
            }
        }
        this.seedColorChecker();
    }
    @Override
    public void seedColorChecker(){
        if (this.walnutSeedSelected){
            this.walnutSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.walnutSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.walnutSeedSelected;
    }
    @Override
    public int getPlantNumber(){
        return 4;
    }
    @Override
    public int myCost(){
        return Constants.WALNUT_COST;
    }
    @Override
    public void reset(){
        this.walnutSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }
}
