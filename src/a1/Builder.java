package a1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */
public class Builder {
    private String name;            //builder's name
    private Tile startingTile;      //builder's start tile(current tile)
    private List<Block> inventory;  //builder's inventory

    public Builder(String name, Tile startingTile) {
        if (name == null) {
            throw new RuntimeException("name can not be null");
        }
        if (startingTile == null) {
            throw new RuntimeException("startingTile can not be null");
        }
        this.name = name;
        this.startingTile = startingTile;
    }

    public Builder(String name, Tile startingTile, List<Block> startingInventory) throws InvalidBlockException {
        if (name == null) {
            throw new RuntimeException("name can not be null");
        }
        if (startingTile == null) {
            throw new RuntimeException("startingTile can not be null");
        }
        this.name = name;
        this.startingTile = startingTile;
        List<Block> blockList = new ArrayList<>();
        for (Block block : startingInventory) {
            if (block == null) {
                throw new RuntimeException("block can not be null");
            }
            if (!block.isCarryable()) {
                throw new InvalidBlockException();
            }
            blockList.add(block);
        }
        this.inventory = blockList;
    }

    /**
     * used to get builder's name
     *
     * @return builder's name
     */
    public String getName() {
        return name;
    }

    /**
     * used to get current tile
     *
     * @return cunrrent tile
     */
    public Tile getCurrentTile() {
        return startingTile;
    }

    /**
     * used to get inventory
     *
     * @return inventory
     */
    public List<Block> getInventory() {
        return inventory;
    }

    /**
     * Drop a block from inventory on the top of the current tile
     *
     * @param inventoryIndex the index of a1.Builder's inventory
     * @throws InvalidBlockException
     * @throws TooHighException      the method will remove the block at index from inventory,and add it to current tile
     */
    public void dropFromInventory(int inventoryIndex) throws InvalidBlockException, TooHighException {
        if (inventoryIndex < 0 || inventoryIndex >= inventory.size()) {
            throw new InvalidBlockException();
        }
        int i = 0;
        List<Block> blocks = getCurrentTile().getBlocks();
        Block block = inventory.get(inventoryIndex);
        if (blocks.size() == 8 || (block instanceof GroundBlock && blocks.size() >= 3)) {
            throw new TooHighException();
        }
        inventory.remove(block);
        getCurrentTile().placeBlock(block);
    }

    /**
     * Attempt to dig in the current tile and add tile to the inventory
     *
     * @throws InvalidBlockException
     * @throws TooLowException       the current tile top block will be removed,and if the block is diggable,inventory will be incremented
     */
    public void digOnCurrentTile() throws InvalidBlockException, TooLowException {
        Tile tile = getCurrentTile();
        List<Block> blocks = tile.getBlocks();
        if (null == blocks || blocks.size() == 0) {
            throw new TooLowException();
        }
        if (!tile.getTopBlock().isDiggable()) {
            throw new InvalidBlockException();
        }
        if (tile.getTopBlock().isDiggable()) {
            Block block = tile.dig();
            getInventory().add(block);
        } else {
            tile.removeTopBlock();
        }
    }

    /**
     * Check if the a1.Builder can enter a tile from the current tile.
     *
     * @param newTile new tile
     * @return true or false
     */
    public boolean canEnter(Tile newTile) {
        if (null == newTile) {
            return false;
        } else {
            Tile tile = getCurrentTile();
            //the height of the new tile (number of blocks) is the same or different by 1 from the current tile (i.e. abs(current tile height - new tile) <= 1)
            if (null != tile.getExits() && Math.abs(tile.getBlocks().size() - newTile.getBlocks().size()) <= 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * move the builder to a new tile.
     *
     * @param newTile new tile
     * @throws NoExitException if canEnter,the new tile will be new start tile
     */
    public void moveTo(Tile newTile) throws NoExitException {
        if (canEnter(newTile)) {
            this.startingTile = newTile;
        } else {
            throw new NoExitException();
        }
    }
}