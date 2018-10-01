package a2;

import csse2002.block.world.BlockWorldException;

/**
 * A World Map file is geometrically inconsistent.
 */
public class WorldMapInconsistentException extends BlockWorldException {
    public WorldMapInconsistentException() {
    }

    public WorldMapInconsistentException(String message) {
        super(message);
    }
}
