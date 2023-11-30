package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SunFlowerSeedPacket implements SeedPacket{
    private boolean sunFlowerSeedSelected;
    private int TotalSun;
    private Game myGame;
    public SunFlowerSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.sunFlowerSeedSelected = false;
        Button peaShooterSeedPacket = new Button("SunFlower");
        root.getChildren().add(peaShooterSeedPacket);
        peaShooterSeedPacket.setOnAction((ActionEvent e) -> this.sunFlowerSeedSelectChecker(this.TotalSun));
    }
    private void sunFlowerSeedSelectChecker(int totalSun){
        if (!this.sunFlowerSeedSelected) {
            if (!this.myGame.preventDoubleChoicePacket()) {
                if (totalSun >= 100) {
                    this.sunFlowerSeedSelected = true;
                }
            } else {
                this.sunFlowerSeedSelected = false;
            }
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.sunFlowerSeedSelected;
    }
    @Override
    public int getPlantNumber(){
        return 2;
    }
    @Override
    public int myCost(){
        return Constants.SUNFLOWER_COST;
    }
    @Override
    public void reset(){
        this.sunFlowerSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }
}
