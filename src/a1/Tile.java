package a1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/30.
 */
public class Tile implements Serializable {
    private Map<String, Tile> exits;     //the tile's exit
    private List<Block> blocks;         //tile's block

    public Tile() {
        this.exits = new HashMap<>();
        this.blocks = new ArrayList<>();
        this.blocks.add(new SoilBlock());
        this.blocks.add(new SoilBlock());
        this.blocks.add(new GrassBlock());
    }

    public Tile(List<Block> startingBlocks) throws TooHighException {
        if (null == startingBlocks || startingBlocks.size() == 0) {
            throw new RuntimeException("startingBlocks can not be null");
        }
        if (startingBlocks.size() > 8) {
            throw new TooHighException();
        }
        for (int i = 0; i < startingBlocks.size(); i++) {
            if (i < 3) {
                continue;
            }
            if (startingBlocks.get(i) instanceof GroundBlock) {
                throw new TooHighException();
            }
        }
        this.exits = new HashMap<>();
        this.blocks = new ArrayList<>();
        this.blocks.addAll(startingBlocks);
    }

    /**
     * used to get tile's exits
     *
     * @return tile's exits
     */
    public Map<String, Tile> getExits() {
        return exits;
    }

    public void addExit(String name, Tile target) throws NoExitException {
        if (null == name || null == target) {
            throw new NoExitException();
        }
        exits.put(name, target);
    }

    /**
     * used to get tile's block
     *
     * @return the list of tile's block
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * used to get top block
     *
     * @return the top of tile's blocks
     * @throws TooLowException
     */
    public Block getTopBlock() throws TooLowException {
        if (blocks.size() == 0) {
            throw new TooLowException();
        }
        return blocks.get(blocks.size() - 1);
    }

    /**
     * Attempt to move the current top block to another tile.
     *
     * @param exitName the exit's name
     * @throws TooHighException
     * @throws InvalidBlockException
     * @throws NoExitException       if current tile top block is moveable ,the current tile top block will be removed,and the target tile block will be added
     */
    public void moveBlock(String exitName) throws TooHighException, InvalidBlockException, NoExitException {
        Block topBlock;
        try {
            topBlock = getTopBlock();
            removeTopBlock();
        } catch (TooLowException e) {
            e.printStackTrace();
            return;
        }
        if (!topBlock.isMoveable()) {
            throw new InvalidBlockException();
        }
        if (null == exits.get(exitName)) {
            throw new NoExitException();
        }
        if (exits.get(exitName).getBlocks().size() >= getBlocks().size()) {
            throw new TooHighException();
        }
        exits.get(exitName).getBlocks().add(topBlock);
    }

    /**
     * Place a block on a tile. Add the block to the top of the blocks on this tile.
     * If the block is an instance of a1.GroundBlock, it can only be placed underground.
     *
     * @param block will be placed block
     * @throws TooHighException
     * @throws InvalidBlockException the number of tile's block will be added
     */
    public void placeBlock(Block block) throws TooHighException, InvalidBlockException {
        if (null == block) {
            throw new InvalidBlockException();
        }
        if (block instanceof GroundBlock && blocks.size() >= 3) {
            throw new TooHighException();
        }
        if (blocks.size() == 8) {
            throw new TooHighException();
        }
        blocks.add(block);
    }

    /**
     * Remove an exit from this tile
     *
     * @param name exit will be removed
     * @throws NoExitException
     */
    public void removeExit(String name) throws NoExitException {
        if (null == name || !exits.containsKey(name)) {
            throw new NoExitException();
        }
        exits.remove(name);
    }

    /**
     * Remove the block on top of the tile
     *
     * @throws TooLowException
     */
    public void removeTopBlock() throws TooLowException {
        if (blocks.size() == 0) {
            throw new TooLowException();
        }
        blocks.remove(blocks.size() - 1);
    }

    /**
     * @return be diggable block(the top of blocks)
     * @throws TooLowException
     * @throws InvalidBlockException
     */
    public Block dig() throws TooLowException, InvalidBlockException {
        if (blocks.size() == 0) {
            throw new TooLowException();
        }
        Block block = this.getTopBlock();
        if (!block.isDiggable()) {
            throw new InvalidBlockException();
        }
        this.removeTopBlock();
        return block;
    }
}
