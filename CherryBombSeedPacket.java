package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class CherryBombSeedPacket implements SeedPacket{
    private boolean cherryBombSeedSelected;
    private int TotalSun;
    private Game myGame;
    public CherryBombSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.cherryBombSeedSelected = false;
        Button cherryBombSeedPacket = new Button("CherryBomb");
        root.getChildren().add(cherryBombSeedPacket);
        cherryBombSeedPacket.setOnAction((ActionEvent e) -> this.cherryBombSeedSelectChecker(this.TotalSun));
    }
    private void cherryBombSeedSelectChecker(int totalSun) {
        if (!this.cherryBombSeedSelected) {
            if (!this.myGame.preventDoubleChoicePacket()) {
                if (totalSun >= 150) {
                    this.cherryBombSeedSelected = true;
                }
            } else {
                this.cherryBombSeedSelected = false;
            }
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.cherryBombSeedSelected;
    }
    @Override
    public int getPlantNumber(){
        return 3;
    }
    @Override
    public int myCost(){
        return Constants.CHERRYBOMB_COST;
    }
    @Override
    public void reset(){
        this.cherryBombSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }
}
