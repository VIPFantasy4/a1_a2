package a2;

/**
 * Created by Administrator on 2018/9/28.
 */
public class Position implements Comparable<Position> {
    private int x;
    private int y;

    public Position(int x, int y) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Position other) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
