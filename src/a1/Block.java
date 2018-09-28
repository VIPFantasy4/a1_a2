package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public interface Block {
    String getBlockType(); //get the block's type

    String getColour();    //get the block's colour

    boolean isCarryable(); // is the block darryable

    boolean isDiggable();  //is the block  diggable

    boolean isMoveable();  // is the block moveable
}
