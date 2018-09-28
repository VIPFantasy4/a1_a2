package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public abstract class GroundBlock implements Block {
    public GroundBlock() {
    }

    @Override
    public boolean isDiggable() {
        return true;
    }   //is ground block diggable

    @Override
    public boolean isMoveable() {
        return false;
    }   //is ground block movebale
}
