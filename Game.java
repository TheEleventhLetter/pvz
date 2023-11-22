package indy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class Game {
    private Lawn lawn;
    private boolean peaShooterSeedSelected;

    public Game(Pane gamepane, HBox buttonPane){
        this.peaShooterSeedSelected = false;
        this.createButtonPane(buttonPane);
        this.createGamePane(gamepane);

    }

    private void createGamePane(Pane gamepane){
        this.lawn = new Lawn(gamepane);
        gamepane.setOnMouseClicked((MouseEvent e) -> this.handleMouseClick(e, gamepane));
    }

    private void createButtonPane(HBox buttonPane){
        buttonPane.setPrefSize(Constants.SCENE_WIDTH, Constants.LAWN_WIDTH);
        buttonPane.setStyle("-fx-background-color: #705301");
        Button quitButton = new Button("Quit");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        Button peaShooterSeedPacket = new Button("PeaShooter");
        peaShooterSeedPacket.setOnAction((ActionEvent e) -> this.peaShooterSeedSelectChecker());
        buttonPane.getChildren().addAll(quitButton, peaShooterSeedPacket);

        buttonPane.setFocusTraversable(false);
    }
    private void peaShooterSeedSelectChecker(){
        if (!this.peaShooterSeedSelected){
            this.peaShooterSeedSelected = true;
        } else {
            this.peaShooterSeedSelected = false;
        }
        System.out.println(this.peaShooterSeedSelected);
    }

    public void handleMouseClick(MouseEvent e, Pane gamepane) {
        double MouseX = e.getX();
        double MouseY = e.getY();
        if (MouseX > 0 && MouseX < 2 * Constants.SCENE_WIDTH) {
            if (MouseY > Constants.LAWN_WIDTH && MouseY < Constants.SCENE_HEIGHT) {
                if (this.peaShooterSeedSelected) {
                    if (this.lawn.checkValid(MouseX, MouseY)) {
                        this.lawn.addPlant(MouseX, MouseY, gamepane);
                        this.peaShooterSeedSelected = false;
                    }
                }
            }
        }
    }
}
