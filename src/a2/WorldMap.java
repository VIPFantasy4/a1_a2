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
    private Position position;
    private Builder builder;
    private SparseTileArray sparseTileArray = new SparseTileArray();

    public WorldMap(String filename) throws WorldMapFormatException, WorldMapInconsistentException, FileNotFoundException {
    }

    public WorldMap(Tile startingTile, Position startPosition, Builder builder) throws WorldMapInconsistentException {
        this.position = startPosition;
        this.builder = builder;
//        this.sparseTileArray = new SparseTileArray();
        sparseTileArray.addLinkedTiles(startingTile, startPosition.getX(), startPosition.getY());
    }

    public Builder getBuilder() {
        return builder;
    }

    public Position getStartPosition() {
        return position;
    }

    public Tile getTile(Position position) {
        return sparseTileArray.getTile(position);
    }

    public List<Tile> getTiles() {
        return sparseTileArray.getTiles();
    }

    public void saveMap(String filename) throws IOException {
    }
}
