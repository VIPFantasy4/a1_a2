package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public class WoodBlock implements Block {
    public WoodBlock() {
    }

    @Override
    public String getBlockType() {
        return "wood";
    }  //get wood block type

    @Override
    public String getColour() {
        return "brown";
    }  //get wood block colour

    @Override
    public boolean isCarryable() {
        return true;
    }  //is wood block carryable

    @Override
    public boolean isDiggable() {
        return true;
    }  //is wood block diggable

    @Override
    public boolean isMoveable() {                    //is wood block moveable
        return true;
    }
}
