package Utilities;

import Actors.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Configuration for what animals should be on volcano cards and locations of volcano cards
 *
 * @author CL_Monday06pm_Team001
 * @version 1.0.0
 */
public class VolcanoCardConfiguration implements Serializable {

    private static VolcanoCardConfiguration instance = null;

    private final ArrayList<ArrayList<Animal>> cavedConfiguration;
    private final ArrayList<ArrayList<Animal>> nonCavedConfiguration;

    private final ArrayList<ArrayList<Integer>> locations;

    /**
     * Constructor for VolcanoCardConfiguration
     */
    private VolcanoCardConfiguration() {
        cavedConfiguration = new ArrayList<>();
        nonCavedConfiguration = new ArrayList<>();
        cavedConfiguration.add(new ArrayList<>(Arrays.asList(new BabyDragon(), new Bat(), new Spider())));
        cavedConfiguration.add(new ArrayList<>(Arrays.asList(new Salamander(), new Spider(), new Bat())));
        cavedConfiguration.add(new ArrayList<>(Arrays.asList(new Spider(), new Salamander(), new BabyDragon())));
        cavedConfiguration.add(new ArrayList<>(Arrays.asList(new Bat(), new Spider(), new BabyDragon())));
        nonCavedConfiguration.add(new ArrayList<>(Arrays.asList(new Spider(), new Bat(), new Salamander())));
        nonCavedConfiguration.add(new ArrayList<>(Arrays.asList(new BabyDragon(), new Salamander(), new Bat())));
        nonCavedConfiguration.add(new ArrayList<>(Arrays.asList(new Bat(), new BabyDragon(), new Salamander())));
        nonCavedConfiguration.add(new ArrayList<>(Arrays.asList(new Salamander(), new BabyDragon(), new Spider())));

        locations = new ArrayList<>();
        locations.add(new ArrayList<>(Arrays.asList(8, 2)));
        locations.add(new ArrayList<>(Arrays.asList(26, 2)));
        locations.add(new ArrayList<>(Arrays.asList(44, 6)));
        locations.add(new ArrayList<>(Arrays.asList(44, 15)));
        locations.add(new ArrayList<>(Arrays.asList(38, 25)));
        locations.add(new ArrayList<>(Arrays.asList(20, 25)));
        locations.add(new ArrayList<>(Arrays.asList(2, 21)));
        locations.add(new ArrayList<>(Arrays.asList(2, 12)));
    }

    /**
     * gets single instance of this class
     *
     * @return instance of this class
     */
    public static VolcanoCardConfiguration getInstance() {
        if (instance == null) {
            instance = new VolcanoCardConfiguration();
        }
        return instance;
    }

    /**
     * gets what animals should be on volcano cards with caves
     *
     * @return list of what animals should be on volcano cards with caves
     */
    public ArrayList<ArrayList<Animal>> getCavedConfiguration() {
        return this.cavedConfiguration;
    }

    /**
     * gets what animals should be on volcano cards with no caves
     *
     * @return list of what animals should be on volcano cards with no caves
     */
    public ArrayList<ArrayList<Animal>> getNonCavedConfiguration() {
        return this.nonCavedConfiguration;
    }

    /**
     * get coordinates of initial location of the volcano card
     *
     * @param index identifier of the volcano card to get coordinates of
     * @return coordinates of initial location of the volcano card
     */
    public ArrayList<Integer> getLocation(int index) {
        return locations.get(index);
    }
}
