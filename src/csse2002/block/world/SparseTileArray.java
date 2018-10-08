package csse2002.block.world;

import java.util.*;

/**
 * A sparse representation of tiles in an Array.
 * Contains Tiless stored with an associated Position (x, y) in a map.
 */
public class SparseTileArray {
    private Map<Position, Tile> map;
    private List<Tile> tiles;

    public SparseTileArray() {
        map = new TreeMap<>();
        tiles = new LinkedList<>();
    }

    /**
     * Add a set of tiles to the sparse tilemap.
     * This function does the following:
     * Remove any tiles that are already existing in the sparse map.
     * Add startingTile at position (startingX, startingY), such that getTile(new Position(startingX, startingY)) == startingTile.
     * For each pair of linked tiles (tile1 at (x1, y1) and tile2 at (x2, y2) that are accessible from startingTile , tile2 will get a new position based on tile1's position, and tile1's exit name.
     * tile2 at "north" exit should get a new position of (x1, y1 - 1), i.e. getTile(new Position(x1, y1 - 1)) == tile1.getExits().get("north")
     * tile2 at "east" exit should get a position of (x1 + 1, y1), i.e. getTile(new Position(x1 + 1, y1)) == tile1.getExits().get("east")
     * tile2 at "south" exit should get a position of (x1, y1 + 1), i.e. getTile(new Position(x1, y1 + 1)) == tile1.getExits().get("south")
     * tile2 at "west" exit should get a position of (x1 - 1, y1), i.e. getTile(new Position(x1 - 1, y1)) == tile1.getExits().get("west")
     * If there are tiles that are not geometrically consistent, i.e. Tiles that would occupy the same position or require two different coordinates for getTile() method to work, throw a WorldMapInconsistentException.
     * Two examples of inconsistent tiles are:
     * tile1.getExits().get("north").getExits().get("south) is non null and not == to tile1, throw a WorldMapInconsistentException.
     * Note: one way exits are allowed, so tile1.getExits().get("north").getExits().get("south) == null would be acceptable, but tile1.getExits().get("north").getExits().get("south) == tile2 for some other non-null tile2 is not.
     * tile1.getExits().get("north").getExits().get("north") == tile1. tile1 exits in two different places in this case.
     * getTiles() should return a list of each accessible tile in a breadth-first search order (see getTiles())
     * If an exception is thrown, reset the state of the SparseTileArray such that getTile(new Position(x, y)) returns null for any x and y.
     *
     * @param startingTile
     * @param startingX
     * @param startingY
     * @throws WorldMapInconsistentException
     */
    public void addLinkedTiles(Tile startingTile, int startingX, int startingY) throws WorldMapInconsistentException {
        map.clear();
        tiles.clear();
        Map<Tile, Position> rMap = new LinkedHashMap<>();
        Position position = new Position(startingX, startingY);
        map.put(position, startingTile);
        rMap.put(startingTile, position);
        tiles.add(startingTile);
        Queue<Tile> targets = new LinkedList<>();
        targets.offer(startingTile);
        try {
            while (!targets.isEmpty()) {
                Tile tile = targets.poll();
                Map<String, Tile> exits = tile.getExits();
                int x = rMap.get(tile).getX();
                int y = rMap.get(tile).getY();
                Tile target;
                if ((target = exits.get("north")) != null) {
                    Position p = new Position(x, y - 1);
                    if (tiles.lastIndexOf(target) == -1) {
                        if (map.get(p) != null || !tile.equals(target.getExits().get("south")) && target.getExits().get("south") != null)
                            throw new WorldMapInconsistentException();
                        map.put(p, target);
                        rMap.put(target, p);
                        tiles.add(target);
                        targets.offer(target);
                    } else {
                        if (rMap.get(target).compareTo(p) != 0) throw new WorldMapInconsistentException();
                    }
                }
                if ((target = exits.get("east")) != null) {
                    Position p = new Position(x + 1, y);
                    if (tiles.lastIndexOf(target) == -1) {
                        if (map.get(p) != null || !tile.equals(target.getExits().get("west")) && target.getExits().get("west") != null)
                            throw new WorldMapInconsistentException();
                        map.put(p, target);
                        rMap.put(target, p);
                        tiles.add(target);
                        targets.offer(target);
                    } else {
                        if (rMap.get(target).compareTo(p) != 0) throw new WorldMapInconsistentException();
                    }
                }
                if ((target = exits.get("south")) != null) {
                    Position p = new Position(x, y + 1);
                    if (tiles.lastIndexOf(target) == -1) {
                        if (map.get(p) != null || !tile.equals(target.getExits().get("north")) && target.getExits().get("north") != null)
                            throw new WorldMapInconsistentException();
                        map.put(p, target);
                        rMap.put(target, p);
                        tiles.add(target);
                        targets.offer(target);
                    } else {
                        if (rMap.get(target).compareTo(p) != 0) throw new WorldMapInconsistentException();
                    }
                }
                if ((target = exits.get("west")) != null) {
                    Position p = new Position(x - 1, y);
                    if (tiles.lastIndexOf(target) == -1) {
                        if (map.get(p) != null || !tile.equals(target.getExits().get("east")) && target.getExits().get("east") != null)
                            throw new WorldMapInconsistentException();
                        map.put(p, target);
                        rMap.put(target, p);
                        tiles.add(target);
                        targets.offer(target);
                    } else {
                        if (rMap.get(target).compareTo(p) != 0) throw new WorldMapInconsistentException();
                    }
                }
            }
        } catch (WorldMapInconsistentException e) {
            map.clear();
            tiles.clear();
            throw new WorldMapInconsistentException();
        }
    }

    /**
     * Get the tile at position at (x, y), given by position.getX() and position.getY()
     *
     * @param position
     * @return tile at position or null if there is no tile at (x, y).
     */
    public Tile getTile(Position position) {
        return map.get(position);
    }

    /**
     * Get a set of ordered tiles from SparseTileArray in breadth-first-search order.
     *
     * @return a breadth-first-search list
     */
    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }
}
