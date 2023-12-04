package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SunFlowerSeedPacket implements SeedPacket{
    private boolean sunFlowerSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button sunFlowerSeedPacket;
    public SunFlowerSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.sunFlowerSeedSelected = false;
        this.sunFlowerSeedPacket = new Button("SunFlower");
        this.sunFlowerSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.sunFlowerSeedPacket);
        this.sunFlowerSeedPacket.setOnAction((ActionEvent e) -> this.sunFlowerSeedSelectChecker(this.TotalSun));
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
        this.seedColorChecker();
    }
    @Override
    public void seedColorChecker(){
        if (this.sunFlowerSeedSelected){
            this.sunFlowerSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.sunFlowerSeedPacket.setStyle("-fx-background-color: #e3b44f");
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
