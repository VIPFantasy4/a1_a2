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

    public Tile getTile(Position position) {
        return map.get(position);
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }
}
