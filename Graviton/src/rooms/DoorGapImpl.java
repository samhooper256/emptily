package rooms;

import javafx.geometry.Side;

public record DoorGapImpl(Side side, double dist1, double dist2) implements DoorGap {

}
