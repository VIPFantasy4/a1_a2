package a2;

import csse2002.block.world.BlockWorldException;

/**
 * A World Map file contains the wrong format.
 */
public class WorldMapFormatException extends BlockWorldException {
    public WorldMapFormatException() {
    }

    public WorldMapFormatException(String message) {
        super(message);
    }
}
