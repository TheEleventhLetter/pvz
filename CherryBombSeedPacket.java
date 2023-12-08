package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class CherryBombSeedPacket implements SeedPacket{
    private boolean cherryBombSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button cherryBombSeedPacket;
    public CherryBombSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.cherryBombSeedSelected = false;
        this.cherryBombSeedPacket = new Button("CherryBomb");
        this.cherryBombSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.cherryBombSeedPacket);
        this.cherryBombSeedPacket.setOnAction((ActionEvent e) -> this.cherryBombSeedSelectChecker(this.TotalSun));
    }
    private void cherryBombSeedSelectChecker(int totalSun) {
        if (!this.myGame.getIsPaused()) {
            if (!this.cherryBombSeedSelected) {
                if (!this.myGame.preventDoubleChoicePacket()) {
                    if (totalSun >= 150) {
                        this.cherryBombSeedSelected = true;
                    }
                }
            } else {
                this.cherryBombSeedSelected = false;
            }
        }
        this.seedColorChecker();
    }
    @Override
    public void seedColorChecker(){
        if (this.cherryBombSeedSelected){
            this.cherryBombSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.cherryBombSeedPacket.setStyle("-fx-background-color: #e3b44f");
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
