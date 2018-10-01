import csse2002.block.world.Action;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by HP on 2018/10/1.
 */
public class ActionTest {
    @Test
    public void testGetPrimaryAction() {
        Assert.assertEquals(3, new Action(3, "1").getPrimaryAction());
    }

    @Test
    public void testGetSecondaryAction() {
        Assert.assertEquals("fuss", new Action(3, "1").getSecondaryAction());
    }
}
