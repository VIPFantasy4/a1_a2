package a1;

import a1.GrassBlock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Administrator on 2018/8/30.
 */
public class GrassBlockTest {
    @Test
    public void testGetBlockType1() {
        GrassBlock grassBlock = new GrassBlock();
        String blockType = grassBlock.getBlockType();
        Assert.assertEquals("grass", blockType);
    }

    @Test
    public void testGetBlockType2() {
        GrassBlock grassBlock = new GrassBlock();
        String blockType = grassBlock.getBlockType();
        Assert.assertEquals("stone", blockType);
    }

    @Test
    public void testGetColour1() {
        GrassBlock grassBlock = new GrassBlock();
        String blockType = grassBlock.getColour();
        Assert.assertEquals("green", blockType);
    }

    @Test
    public void testGetColour2() {
        GrassBlock grassBlock = new GrassBlock();
        String blockType = grassBlock.getColour();
        Assert.assertEquals("brown", blockType);
    }

    @Test
    public void testIsCarryable1() {
        GrassBlock grassBlock = new GrassBlock();
        boolean blockType = grassBlock.isCarryable();
        Assert.assertEquals(false, blockType);
    }

    @Test
    public void testIsCarryable2() {
        GrassBlock grassBlock = new GrassBlock();
        boolean blockType = grassBlock.isCarryable();
        Assert.assertEquals(true, blockType);
    }
}
