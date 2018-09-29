package a2;

import csse2002.block.world.Builder;
import csse2002.block.world.Tile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/9/28.
 */
public class WorldMap {
    public WorldMap(String filename) throws WorldMapFormatException, WorldMapInconsistentException, FileNotFoundException {
    }

    public WorldMap(Tile startingTile, Position startPosition, Builder builder) throws WorldMapInconsistentException {
    }

    public Builder getBuilder() {
        return null;
    }

    public Position getStartPosition() {
        return null;
    }

    public Tile getTile(Position position) {
        return null;
    }

    public List<Tile> getTiles() {
        return null;
    }

    public void saveMap(String filename) throws IOException {
    }
}
