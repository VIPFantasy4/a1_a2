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
        map = new LinkedHashMap<>();
        tiles = new LinkedList<>();
    }

    public void addLinkedTiles(Tile startingTile, int startingX, int startingY) throws WorldMapInconsistentException{
        map.clear();
        tiles.clear();
        map.put(new Position(startingX, startingY), startingTile);
        tiles.add(startingTile);
        Tile targetN;
        Tile targetE;
        Tile targetS;
        Tile targetW;
        Queue<Tile> queue = new LinkedList<Tile>();
        queue.offer(startingTile);
        try {
            while(!queue.isEmpty()){
                Position position = null;
                Tile currentTile = queue.poll();
                for(Position key: map.keySet()){
                    if(map.get(key).equals(currentTile)){
                        position = key;
                        break;
                    }
                }
                int x = position.getX();
                int y = position.getY();
                Map<String, Tile> exits = currentTile.getExits();
                if ((targetN = exits.get("north")) != null) {
                    if (!targetN.equals(targetN.getExits().get("south")) && targetN.getExits().get("south") != null)
                        throw new WorldMapInconsistentException();
                    queue.offer(targetN);
                    map.put(new Position(x, y - 1), targetN);
                    tiles.add(targetN);
                }
                if ((targetE = exits.get("east")) != null) {
                    if (!targetE.equals(targetE.getExits().get("west")) && targetE.getExits().get("west") != null)
                        throw new WorldMapInconsistentException();
                    queue.offer(targetE);
                    map.put(new Position(x + 1, y), targetE);
                    tiles.add(targetE);
                }
                if ((targetS = exits.get("south")) != null) {
                    if (!targetS.equals(targetS.getExits().get("north")) && targetS.getExits().get("north") != null)
                        throw new WorldMapInconsistentException();
                    queue.offer(targetE);
                    map.put(new Position(x, y + 1), targetS);
                    tiles.add(targetS);
                }
                if ((targetW = exits.get("west")) != null) {
                    if (!targetW.equals(targetW.getExits().get("east")) && targetW.getExits().get("east") != null)
                        throw new WorldMapInconsistentException();
                    queue.offer(targetE);
                    map.put(new Position(x - 1, y), targetW);
                    tiles.add(targetW);
                }
            }
        }catch (WorldMapInconsistentException e){
            map.clear();
            tiles.clear();
            throw new WorldMapInconsistentException();
        }
    }

    // TODO:
//    public void addLinkedTiles(Tile startingTile, int startingX, int startingY) throws WorldMapInconsistentException {
////        Map<Position, Tile> map = new TreeMap<>();
//        map.put(new Position(startingX, startingY), startingTile);
//        tiles.add(startingTile);
//        Map<String, Tile> exits = startingTile.getExits();
//        Tile targetN;
//        Tile targetE;
//        Tile targetS;
//        Tile targetW;
//        List<Tile> targets = new LinkedList<>();
//        try {
//            if ((targetN = exits.get("north")) != null) {
//                if (!targetN.equals(targetN.getExits().get("south")) && targetN.getExits().get("south") != null)
//                    throw new WorldMapInconsistentException();
//                map.put(new Position(startingX, startingY - 1), targetN);
//                tiles.add(targetN);
//                targets.add(targetN);
//            }
//            if ((targetE = exits.get("east")) != null) {
//                if (!targetE.equals(targetE.getExits().get("west")) && targetE.getExits().get("west") != null)
//                    throw new WorldMapInconsistentException();
//                map.put(new Position(startingX + 1, startingY), targetE);
//                tiles.add(targetE);
//                targets.add(targetE);
//            }
//            if ((targetS = exits.get("south")) != null) {
//                if (!targetS.equals(targetS.getExits().get("north")) && targetS.getExits().get("north") != null)
//                    throw new WorldMapInconsistentException();
//                map.put(new Position(startingX, startingY + 1), targetS);
//                tiles.add(targetS);
//                targets.add(targetS);
//            }
//            if ((targetW = exits.get("west")) != null) {
//                if (!targetW.equals(targetW.getExits().get("east")) && targetW.getExits().get("east") != null)
//                    throw new WorldMapInconsistentException();
//                map.put(new Position(startingX - 1, startingY), targetW);
//                tiles.add(targetW);
//                targets.add(targetW);
//            }
//        } catch (WorldMapInconsistentException e) {
//            map.clear();
//            tiles.clear();
//            throw new WorldMapInconsistentException();
//        }
//    }

    public Tile getTile(Position position) {
        return map.get(position);
    }

    public List<Tile> getTiles() {
        return Collections.unmodifiableList(tiles);
    }
}
