import csse2002.block.world.NoExitException;
import csse2002.block.world.SparseTileArray;
import csse2002.block.world.Tile;
import csse2002.block.world.WorldMapInconsistentException;
import org.junit.Test;

/**
 * Created by HP on 2018/10/1.
 */
public class SparseTileArrayTest {
    @Test
    public void testAddLinkedTiles() {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();
        try {
            tile.addExit("north", tile1);
            tile.addExit("east", tile2);
            tile.addExit("south", tile3);
            tile.addExit("west", tile4);
            tile1.addExit("south", tile);
        } catch (NoExitException e) {
            e.printStackTrace();
        }
        try {
            SparseTileArray sparseTileArray = new SparseTileArray();
            sparseTileArray.addLinkedTiles(tile, 0, 0);

        } catch (WorldMapInconsistentException e) {
            e.printStackTrace();
        }
    }
}
