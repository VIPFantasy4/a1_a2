package a2;

import csse2002.block.world.Block;
import csse2002.block.world.Builder;
import csse2002.block.world.Tile;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2018/9/28.
 */
public class WorldMap {
    private Position position;
    private Builder builder;
    private SparseTileArray sparseTileArray = new SparseTileArray();

    public WorldMap(String filename) throws WorldMapFormatException, WorldMapInconsistentException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int startingX;
        int startingY;
        String name;
        List<Block> contents;
        int n = 0;
        boolean flag = false;
        int i = 0;
        while (true) {
            i++;
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                throw new WorldMapFormatException();
            }
            if (line == null) break;
            if ((line.startsWith("total") || line.startsWith("exits")) && (i < 6 || !flag))
                throw new WorldMapFormatException();
            if (flag && !line.startsWith("total") && !line.startsWith("exits")) throw new WorldMapFormatException();
            if (flag = line.isEmpty()) continue;
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
                    String[] tokens = line.split(",");
                    if (tokens.length == 0) throw new WorldMapFormatException();
                    for (String token : tokens) {
                        if (!"grass".equals(token) && !"soil".equals(token) && !"wood".equals(token) && !"stone".equals(token))
                            throw new WorldMapFormatException();
                    }
                }
            }
            if (isProcessed) continue;
            String[] tokens = line.split(" ");
            if (tokens.length == 0 || tokens.length > 2) throw new WorldMapFormatException();
        }
    }

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

    public void saveMap(String filename) throws IOException {
    }
}
