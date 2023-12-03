package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class WalnutSeedPacket implements SeedPacket{
    private boolean WalnutSeedSelected;
    private int TotalSun;
    private Game myGame;
    public WalnutSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.WalnutSeedSelected = false;
        Button WalnutSeedPacket = new Button("Walnut");
        root.getChildren().add(WalnutSeedPacket);
        WalnutSeedPacket.setOnAction((ActionEvent e) -> this.WalnutSeedSelectChecker(this.TotalSun));
    }
    private void WalnutSeedSelectChecker(int totalSun) {
        if (!this.WalnutSeedSelected) {
            if (!this.myGame.preventDoubleChoicePacket()) {
                if (totalSun >= 150) {
                    this.WalnutSeedSelected = true;
                }
            } else {
                this.WalnutSeedSelected = false;
            }
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.WalnutSeedSelected;
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
        this.WalnutSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }
}
