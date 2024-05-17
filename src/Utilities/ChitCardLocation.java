package Utilities;

import Game.ChitCard;
import Game.Location;

import java.util.HashMap;
import java.util.Map;

public class ChitCardLocation {
    private Map<Location, ChitCard> chitCardLocation;
    public static ChitCardLocation instance;

    private ChitCardLocation() {
        chitCardLocation = new HashMap<Location, ChitCard>();
    }

    public void add(ChitCard chitCard, Location location) {
        chitCardLocation.put(location, chitCard);
    }

    public static ChitCardLocation getInstance() {
        if (instance == null) {
            instance = new ChitCardLocation();
        }
        return instance;
    }

    public ChitCard getChitCard(Location location) {
        return chitCardLocation.get(location);
    }

    public Location getLocation(ChitCard chitCard) {
        for (Map.Entry<Location, ChitCard> entry : chitCardLocation.entrySet()) {
            if (entry.getValue().equals(chitCard)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void remove(Location location) {
        chitCardLocation.remove(location);
    }

    public void remove(ChitCard chitCard) {
        chitCardLocation.values().remove(chitCard);
    }
}
