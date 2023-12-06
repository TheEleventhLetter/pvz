package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class CatTailSeedPacket implements SeedPacket{
    private boolean catTailSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button catTailSeedPacket;
    public CatTailSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.catTailSeedSelected = false;
        this.catTailSeedPacket = new Button("CatTail");
        this.catTailSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.catTailSeedPacket);
        this.catTailSeedPacket.setOnAction((ActionEvent e) -> this.catTailSeedSelectChecker(this.TotalSun));
    }
    private void catTailSeedSelectChecker(int totalSun) {
        if (!this.catTailSeedSelected) {
            if (!this.myGame.preventDoubleChoicePacket()) {
                if (totalSun >= 200) {
                    this.catTailSeedSelected = true;
                }
            }
        } else {
            this.catTailSeedSelected = false;
        }

        this.seedColorChecker();
    }
    @Override
    public void seedColorChecker(){
        if (this.catTailSeedSelected){
            this.catTailSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.catTailSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.catTailSeedSelected;
    }
    @Override
    public int getPlantNumber(){
        return 5;
    }
    @Override
    public int myCost(){
        return Constants.CATTAIL_COST;
    }
    @Override
    public void reset(){
        this.catTailSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }

}
