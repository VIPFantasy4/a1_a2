package a1;

/**
 * Created by Administrator on 2018/8/30.
 */
public class GrassBlock extends GroundBlock {
    public GrassBlock() {
    }

    @Override
    public String getBlockType() {
        return "grass";
    }   // get grass block type

    @Override
    public String getColour() {
        return "green";
    }   //get garss block colour

    @Override
    public boolean isCarryable() {
        return false;
    }   //is grass block carryable
}
