package indy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;

public interface Plant {
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies);
    public void playTimeline();
    public void stopTimeline();
    public int getX();
    public int getY();
    public void checkHealth(Pane root);
    public void removePlant(Pane root);

}
