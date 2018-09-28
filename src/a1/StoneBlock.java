package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public class StoneBlock implements Block {

    public StoneBlock() {
    }

    @Override
    public String getBlockType() {
        return "stone";
    }  //get stone block type

    @Override
    public String getColour() {
        return "gray";
    }  //get stone block colour

    @Override
    public boolean isCarryable() {
        return false;
    }  //is stone block carryable

    @Override
    public boolean isDiggable() {
        return false;
    }   //is stone block diggable

    @Override
    public boolean isMoveable() {
        return false;
    }   //is stone block moveable
}
