package indy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Plant interface. Implemented in all plant classes.
 */
public interface Plant {
    /**
     * Method assigns plant its list of corresponding zombies. Removes out of target zombies and redundant checking.
     * @param ListOfZombies totalZombies from lawn class
     */
    public void assignCorrespondingListOfZombies(ArrayList<LinkedList<Zombie>> ListOfZombies);

    /**
     * method plays or resumes all timelines in a plant class
     */
    public void playTimeline();

    /**
     * method stops all timelines in a plant class
     */
    public void stopTimeline();

    /**
     * method returns X position of plant
     * @return X position of plant
     */
    public int getX();

    /**
     * method returns Y position of plant
     * @return Y position of plant
     */
    public int getY();

    /**
     * method checks health of plant.
     * @param root passed to handle graphical change based on health
     */
    public void checkHealth(Pane root);

    /**
     * method removes plant.
     * @param root passed to handle graphical removal of plant elements.
     */
    public void removePlant(Pane root);

}
