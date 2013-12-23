package robot;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aquassaut on 12/23/13.
 */
public class RoadBookTest {
    private RoadBook r;

    @Test
    public void testHasInstruction() throws Exception {
        List<Instruction> i = new ArrayList<Instruction>();
        r = new RoadBook(i);
        Assert.assertEquals("Il n'y a pas d'instruction", false, r.hasInstruction());

        i.add(Instruction.FORWARD);
        r = new RoadBook(i);
        Assert.assertEquals("Il devrait y avoir une instruction", true, r.hasInstruction());
    }

    @Test
    public void testNext() throws Exception {
        List<Instruction> i = new ArrayList<Instruction>();
        i.add(Instruction.FORWARD);
        r = new RoadBook(i);
        Assert.assertEquals("On devrait avoir la même instruction", Instruction.FORWARD, r.next());
    }
}