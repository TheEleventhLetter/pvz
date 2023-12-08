package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class PeaShooterSeedPacket implements SeedPacket {
    private boolean peaShooterSeedSelected;
    private int TotalSun;
    private Game myGame;
    private Button peaShooterSeedPacket;
    public PeaShooterSeedPacket(Pane root, Game mygame){
        this.myGame = mygame;
        this.TotalSun = this.myGame.getTotalSun();
        this.peaShooterSeedSelected = false;
        this.peaShooterSeedPacket = new Button("PeaShooter");
        this.peaShooterSeedPacket.setStyle("-fx-background-color: #e3b44f");
        root.getChildren().add(this.peaShooterSeedPacket);
        this.peaShooterSeedPacket.setOnAction((ActionEvent e) -> this.peaShooterSeedSelectChecker(this.TotalSun));
    }
    private void peaShooterSeedSelectChecker(int totalSun) {
        if (!this.myGame.getIsPaused()) {
            if (!this.peaShooterSeedSelected) {
                if (!this.myGame.preventDoubleChoicePacket()) {
                    if (totalSun >= 100) {
                        this.peaShooterSeedSelected = true;
                    }
                }
            } else {
                this.peaShooterSeedSelected = false;
            }
        }
        this.seedColorChecker();
    }
    @Override
    public void seedColorChecker(){
        if (this.peaShooterSeedSelected){
            this.peaShooterSeedPacket.setStyle("-fx-background-color: #00ff00");
        } else {
            this.peaShooterSeedPacket.setStyle("-fx-background-color: #e3b44f");
        }
    }
    @Override
    public boolean isSeedSelected(){
        return this.peaShooterSeedSelected;
    }
    @Override
    public int getPlantNumber(){
        return 1;
    }
    @Override
    public int myCost(){
        return Constants.PEASHOOTER_COST;
    }
    @Override
    public void reset(){
        this.peaShooterSeedSelected = false;
        this.TotalSun = this.myGame.getTotalSun();
    }
    @Override
    public void updateSun(){
        this.TotalSun = this.myGame.getTotalSun();
    }

}
