package indy;

/**
 * SeedPacket Interface. Implemented in all seedPacket classes.
 */
public interface SeedPacket {
    /**
     * method returns whether current seedPacket is Selected or not.
     * @return boolean determining selection
     */
    public boolean isSeedSelected();

    /**
     * method returns plant Number to identify plant type
     * @return integer corresponding to Plant
     */
    public int getPlantNumber();

    /**
     * method returns plant cost
     * @return plant's cost
     */
    public int myCost();

    /**
     * method resets seedPacket after use
     */
    public void reset();

    /**
     * method updates sun in seedPacket
     */
    public void updateSun();

    /**
     * method handles color representation of seedPacket button for friendlier GUI.
     */
    public void seedColorChecker();
}
