package a2;

import csse2002.block.world.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class to store a world map
 */
public class WorldMap {
    private Position position;
    private Builder builder;
    /*create a SparseTileArray as a member, and use the addLinkedTiles to populate it.*/
    private SparseTileArray sparseTileArray = new SparseTileArray();

    /**
     * Construct a block world map from the given filename.
     * abandon all hopes of irregular
     * Note: Files may end with or without a single newline character, but there should not be any blank lines at the end of the file.
     * Tile IDs are the ordering of tiles returned by getTiles()
     * Tiles must have IDs bewteen 0 and N-1, where N is the number of tiles.
     * The ordering does not need to be checked when loading a map (but the saveMap function below does when saving).
     * Note: A blank line is required for an empty inventory, and lines with just an ID followed by a space are required for:
     * A tile entry below "total:N", if the tile has no blocks
     * A tile entry below "exits", if the tile has no exits
     * The function should do the following:
     * Open the filename and read a map in the format given above.
     * Construct a new Builder with the name and inventory from the file (to be returned by getBuilder()), and a starting tile set to the tile with ID 0
     * Construct a new Position for the starting position from the file to be returned as getStartPosition()
     * Construct a Tile for each tile entry in the file (to be returned by getTiles() and getTile())
     * Link each tile by the exits that are given.
     * Throw a WorldMapFormatException if the format of the file is incorrect. This includes:
     * Any lines are missing, including the blank lines before "total:N", and before exits
     * startingX or startingY (lines 1 and 2) are not valid integers
     * There are not N entries under the line that says "total:N"
     * There are not N entries under the "exits" line (there should be exactly N entries and then the file should end.)
     * N is not a valid integer, or N is negative
     * The names of blocks in inventory and on tiles are not one of "grass", "soil", "wood", "stone"
     * The names of exits in the "exits" sections are not one of "north", "east", "south", "west"
     * The ids of tiles are not valid integers, are less than 0 or greater than N - 1
     * The ids that the exits refer to do not exist in the list of tiles
     * loaded tiles contain too many blocks, or GroundBlocks that have an index that is too highA file operation throws an IOException that is not a FileNotFoundException
     * Throw a WorldMapInconsistentException if the format is correct, but tiles would end up in geometrically impossible locations (see SparseTileArray.addLinkedTiles()).
     * Throw a FileNotFoundException if the file does not exist.
     *
     * @param filename
     * @throws WorldMapFormatException
     * @throws WorldMapInconsistentException
     * @throws FileNotFoundException
     */
    public WorldMap(String filename) throws WorldMapFormatException, WorldMapInconsistentException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int startingX = -4396;
        int startingY = -4396;
        String name = null;
        List<Block> contents = new LinkedList<>();
        Map<Integer, Tile> tileMap = new TreeMap<>();
        Map<Integer, Tile> totalTileMap = new TreeMap<>();
        List<Tile> tiles = new LinkedList<>();
        Tile tile;
        int n = -4396;
        boolean flag = false;
        boolean matched = false;
        int i = 0;
        while (true) {
            i++;
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new WorldMapFormatException();
            }
            if (line == null) {
                if (i - 9 == n * 2 || i - 10 == n * 2) break;
                throw new WorldMapFormatException();
            }
            if ((line.startsWith("total") || line.startsWith("exits")) && matched) throw new WorldMapFormatException();
            if (flag && !line.startsWith("total") && !line.startsWith("exits")) throw new WorldMapFormatException();
            if (line.isEmpty() && (i != 4 && (i < 5 || n != -4396 && i - 7 != n && i - 9 != n * 2)))
                throw new WorldMapFormatException();
            if (i != 4 && (flag = line.isEmpty())) continue;
            boolean isProcessed = false;
            switch (i) {
                case 1: {
                    try {
                        startingX = Integer.parseInt(line);
                        isProcessed = true;
                        break;
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                }
                case 2: {
                    try {
                        startingY = Integer.parseInt(line);
                        isProcessed = true;
                        break;
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                }
                case 3: {
                    name = line;
                    isProcessed = true;
                    break;
                }
                case 4: {
                    if (!line.isEmpty()) {
                        String[] tokens = line.split(",");
                        if (tokens.length == 0) throw new WorldMapFormatException();
                        for (String token : tokens) {
                            switch (token) {
                                case "grass":
                                    contents.add(new GrassBlock());
                                    break;
                                case "soil":
                                    contents.add(new SoilBlock());
                                    break;
                                case "wood":
                                    contents.add(new WoodBlock());
                                    break;
                                case "stone":
                                    contents.add(new StoneBlock());
                                    break;
                                default:
                                    throw new WorldMapFormatException();
                            }
                        }
                    }
                    isProcessed = true;
                    break;
                }
                case 5:
                    throw new WorldMapFormatException();
                case 6: {
                    if (!line.startsWith("total")) throw new WorldMapFormatException();
                    String[] tokens = line.split(":");
                    if (tokens.length != 2) throw new WorldMapFormatException();
                    try {
                        if ((n = Integer.parseInt(tokens[1])) < 0) throw new WorldMapFormatException();
                        isProcessed = true;
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                }
            }
            if (isProcessed) continue;
            if (i == n + 8) {
                if (!line.equals("exits")) throw new WorldMapFormatException();
                matched = true;
                continue;
            }
            String[] tokens = line.split(" ");
            if (tokens.length == 0 || tokens.length > 2) throw new WorldMapFormatException();
            int index;
            if (!matched) {
                try {
                    index = Integer.parseInt(tokens[0]);
                    if (index < 0 || index > n - 1 || tileMap.containsKey(index)) throw new WorldMapFormatException();
                } catch (NumberFormatException e) {
                    throw new WorldMapFormatException();
                }
                List<Block> blocks = new LinkedList<>();
                if (tokens.length == 2) {
                    tokens = tokens[1].split(",");
                    if (tokens.length == 0) throw new WorldMapFormatException();
                    for (String token : tokens) {
                        switch (token) {
                            case "grass":
                                blocks.add(new GrassBlock());
                                break;
                            case "soil":
                                blocks.add(new SoilBlock());
                                break;
                            case "wood":
                                blocks.add(new WoodBlock());
                                break;
                            case "stone":
                                blocks.add(new StoneBlock());
                                break;
                            default:
                                throw new WorldMapFormatException();
                        }
                    }
                }
                try {
                    tileMap.put(index, new Tile(blocks));
                } catch (TooHighException e) {
                    throw new WorldMapFormatException();
                }
            } else {
                try {
                    index = Integer.parseInt(tokens[0]);
                    if (index < 0 || index > n - 1 || !tileMap.containsKey(index)) throw new WorldMapFormatException();
                } catch (NumberFormatException e) {
                    throw new WorldMapFormatException();
                }
                if (totalTileMap.containsKey(index)) throw new WorldMapFormatException();
                if (tokens.length == 1) {
                    totalTileMap.put(index, tileMap.get(index));
                } else {
                    tokens = tokens[1].split(",");
                    if (tokens.length == 0) throw new WorldMapFormatException();
                    for (String token : tokens) {
                        String[] exits = token.split(":");
                        if (exits.length != 2) throw new WorldMapFormatException();
                        int tileIndex;
                        try {
                            tileIndex = Integer.parseInt(exits[1]);
                        } catch (NumberFormatException e) {
                            throw new WorldMapFormatException();
                        }
                        if (!tileMap.containsKey(tileIndex)) throw new WorldMapFormatException();
                        if (!"north".equals(exits[0]) && !"east".equals(exits[0]) && !"south".equals(exits[0]) && !"west".equals(exits[0]))
                            throw new WorldMapFormatException();
                        if (tileMap.get(index).getExits().containsKey(exits[0])) throw new WorldMapFormatException();
                        try {
                            tileMap.get(index).addExit(exits[0], tileMap.get(tileIndex));
                        } catch (NoExitException e) {
                            throw new WorldMapFormatException();
                        }
                    }
                    totalTileMap.put(index, tileMap.get(index));
                }
            }
        }
        tile = totalTileMap.get(0);
        this.position = new Position(startingX, startingY);
        try {
            this.builder = new Builder(name, tile, contents);
        } catch (InvalidBlockException e) {
            throw new WorldMapFormatException();
        }
        sparseTileArray.addLinkedTiles(tile, startingX, startingY);
    }

    /**
     * Constructs a new block world map from a startingTile, position and builder,
     * such that getBuilder() == builder, getStartPosition() == startPosition, and getTiles() returns a list of tiles that are linked to startingTile.
     *
     * @param startingTile
     * @param startPosition
     * @param builder
     * @throws WorldMapInconsistentException
     */
    public WorldMap(Tile startingTile, Position startPosition, Builder builder) throws WorldMapInconsistentException {
        this.position = startPosition;
        this.builder = builder;
//        this.sparseTileArray = new SparseTileArray();
        sparseTileArray.addLinkedTiles(startingTile, startPosition.getX(), startPosition.getY());
    }

    public Builder getBuilder() {
        return builder;
    }

    public Position getStartPosition() {
        return position;
    }

    public Tile getTile(Position position) {
        return sparseTileArray.getTile(position);
    }

    public List<Tile> getTiles() {
        return sparseTileArray.getTiles();
    }

    /**
     * save the map in a text file as its load
     *
     * @param filename
     * @throws IOException
     */
    public void saveMap(String filename) throws IOException {
        String enter = "\r\n";
        Position position = getStartPosition();
        Builder builder = getBuilder();
        List<Tile> tileList = getTiles();
        StringBuffer sb = new StringBuffer();
        sb.append(position.getX()).append(enter).append(position.getY()).append(enter)
                .append(builder.getName()).append(enter);
        builder.getInventory().forEach(block -> sb.append(block.getBlockType() + ","));
        if (builder.getInventory().size() > 0) sb.deleteCharAt(sb.length() - 1);
        sb.append(enter).append(enter).append("total:").append(tileList.size()).append(enter);
        for (int i = 0; i < tileList.size(); i++) {
            sb.append(i + " ");
            List<Block> blocks = tileList.get(i).getBlocks();
            blocks.forEach(block -> sb.append(block.getBlockType() + ","));
            if (blocks.size() > 0) sb.deleteCharAt(sb.length() - 1);
            sb.append(enter);
        }
        sb.append(enter).append("exits").append(enter);
        for (int i = 0; i < tileList.size(); i++) {
            sb.append(i + " ");
            Map<String, Tile> map = tileList.get(i).getExits();
            boolean flag = false;
            if (map.containsKey("east")) {
                sb.append("east:" + tileList.indexOf(map.get("east")) + ",");
                flag = true;
            }
            if (map.containsKey("north")) {
                sb.append("north:" + tileList.indexOf(map.get("north")) + ",");
                flag = true;
            }
            if (map.containsKey("west")) {
                sb.append("west:" + tileList.indexOf(map.get("west")) + ",");
                flag = true;
            }
            if (map.containsKey("south")) {
                sb.append("south:" + tileList.indexOf(map.get("south")) + ",");
                flag = true;
            }
            if (flag) sb.deleteCharAt(sb.length() - 1);
            sb.append(enter);
        }
        BufferedOutputStream buffOut = new BufferedOutputStream(new FileOutputStream(new File(filename)));
        buffOut.write(sb.toString().getBytes());
        buffOut.flush();
        buffOut.close();
    }
}