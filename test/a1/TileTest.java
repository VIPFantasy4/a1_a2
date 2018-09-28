package a1;

import a1.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;

/**
 * Created by Administrator on 2018/8/30.
 */
public class TileTest {
    private Tile tile1, tile2;

    @Before
    public void setup() throws TooHighException {
        tile1 = new Tile();
        List<Block> blockList = new ArrayList<>();
        blockList.add(new SoilBlock());
        blockList.add(new GrassBlock());
        tile2 = new Tile(blockList);
    }

    @After
    public void teardown() {
        tile1 = null;
        tile2 = null;
    }

    @Test
    public void testGetExits1() {
        Assert.assertEquals(0, tile1.getExits().size());
        Assert.assertEquals(0, tile2.getExits().size());
    }

    @Test
    public void testGetExits2() {
        Assert.assertEquals(1, tile1.getExits().size());
        Assert.assertEquals(1, tile2.getExits().size());
    }

    @Test
    public void testGetBlocks1() {
        Assert.assertEquals(3, tile1.getBlocks().size());
    }

    @Test
    public void testAddExit1() {
        try {
            tile1.addExit(null, null);
            fail("Expected a a1.NoExitException to be thrown");
        } catch (NoExitException noExitException) {
            Assert.assertEquals("a1.NoExitException", noExitException.toString());
        }

    }
}