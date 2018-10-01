import csse2002.block.world.NoExitException;
import csse2002.block.world.SparseTileArray;
import csse2002.block.world.Tile;
import csse2002.block.world.WorldMapInconsistentException;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HP on 2018/10/1.
 */
public class SparseTileArrayTest {
    @Test
    public void testAddLinkedTiles1() {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();
        Tile tile5 = new Tile();
        try {
            tile.addExit("north", tile1);
            tile.addExit("east", tile2);
            tile.addExit("south", tile3);
            tile.addExit("west", tile4);
            tile1.addExit("south", tile);
            tile1.addExit("north", tile5);
        } catch (NoExitException e) {
            e.printStackTrace();
        }
        List list = new LinkedList();
        try {
            SparseTileArray sparseTileArray = new SparseTileArray();
            sparseTileArray.addLinkedTiles(tile, 0, 0);
            list = sparseTileArray.getTiles();
        } catch (WorldMapInconsistentException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(0, list.indexOf(tile));
        Assert.assertEquals(1, list.indexOf(tile1));
        Assert.assertEquals(2, list.indexOf(tile2));
        Assert.assertEquals(3, list.indexOf(tile3));
        Assert.assertEquals(4, list.indexOf(tile4));
        Assert.assertEquals(5, list.indexOf(tile5));
    }

    @Test
    public void testAddLinkedTiles2() {
        Tile tile = new Tile();
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        try {
            tile.addExit("north", tile1);
            tile1.addExit("south", tile2);
        } catch (NoExitException e) {
            e.printStackTrace();
        }
        try {
            SparseTileArray sparseTileArray = new SparseTileArray();
            sparseTileArray.addLinkedTiles(tile, 0, 0);
        } catch (Exception e) {
            Assert.assertEquals("NoExitException", e.toString());
        }
    }
}
