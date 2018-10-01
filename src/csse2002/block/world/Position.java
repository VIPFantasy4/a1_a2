package csse2002.block.world;

import java.util.Objects;

/**
 * Created by Administrator on 2018/9/28.
 */
public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position other) {
        if (x < other.x || x == other.x && y < other.y) return -1;
        if (x == other.x && y == other.y) return 0;
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position o = (Position) obj;
            return o.x == x && o.y == y;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
}
