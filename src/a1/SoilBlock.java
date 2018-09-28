package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public class SoilBlock extends GroundBlock {
    public SoilBlock() {
    }

    @Override
    public String getBlockType() {
        return "soil";
    } //get soil block type

    @Override
    public String getColour() {
        return "black";
    }  //get soil block colour

    @Override
    public boolean isCarryable() {
        return true;
    }  //is soil block carryable
}
