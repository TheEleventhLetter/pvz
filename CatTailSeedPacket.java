package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class CatTailSeedPacket implements SeedPacket{
    private boolean catTailSeedSelected;
    private int TotalSun;
    private Game myGame;
    public CatTailSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.catTailSeedSelected = false;
        Button catTailSeedPacket = new Button("CatTail");
        root.getChildren().add(catTailSeedPacket);
        catTailSeedPacket.setOnAction((ActionEvent e) -> this.catTailSeedSelectChecker(this.TotalSun));
    }
    private void catTailSeedSelectChecker(int totalSun) {
        if (!this.catTailSeedSelected) {
            if (!this.myGame.preventDoubleChoicePacket()) {
                if (totalSun >= 200) {
                    this.catTailSeedSelected = true;
                }
            } else {
                this.catTailSeedSelected = false;
            }
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
