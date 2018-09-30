package a2;

import csse2002.block.world.Tile;

import java.util.*;

/**
 * Created by Administrator on 2018/9/28.
 */
public class SparseTileArray {
    private Map<Position, Tile> map;
    private List<Tile> tiles;

    public SparseTileArray() {
        map = new TreeMap<>();
        tiles = new LinkedList<>();
    }

    public void addLinkedTiles(Tile startingTile, int startingX, int startingY) throws WorldMapInconsistentException {
    }

    public Tile getTile(Position position) {
        return map.get(position);
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }
}
