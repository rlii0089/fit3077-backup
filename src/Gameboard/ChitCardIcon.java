package Gameboard;

import Game.ChitCard;
import Game.Location;
import Game.Printable;

public class ChitCardIcon implements Printable {
    private ChitCard chitCard;
    private Location location;

    public ChitCardIcon() {
    }

    public ChitCard getChitCard() {
        return chitCard;
    }

    public void setChitCard(ChitCard chitCard) {
        this.chitCard = chitCard;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public char getDisplayChar() {
        return '0';
    }
}
