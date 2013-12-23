package robot;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class RoadBookCalculatorTest {
    private Direction n, s;
    private Coordinates c1, c2;
    private RoadBookCalculator rbc;
    private RoadBook rb;
    private ArrayList<Instruction> i;
    private ArrayList<Instruction> attendu;
    private Iterator<Instruction> it;

    public RoadBookCalculatorTest() {
        rbc = new RoadBookCalculator(); //Coverage $$$$
        Assert.assertTrue("L'instanciation devrait marcher (woohoo)", rbc instanceof RoadBookCalculator);

        i = new ArrayList<Instruction>();
        n =  Direction.NORTH;
        s =  Direction.SOUTH;
        c1 = new Coordinates(-5, 2);
        c2 = new Coordinates(5, -2);
    }

    @Test
    public void testCalculateRoadBook() {
        /*
            DEBUT TOUT NOEUD ET TOUT SEGMENT
         */

        rb = RoadBookCalculator.calculateRoadBook(n, c2, c1, i);
        attendu = new ArrayList<Instruction>() {{
            add(Instruction.TURNRIGHT);
            add(Instruction.TURNRIGHT);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.TURNRIGHT);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
        }};
        it = attendu.iterator();
        int etape = 0;
        while(it.hasNext() && rb.hasInstruction()) {
            etape ++;
            Assert.assertEquals("Parcours emprunté, étape " + etape, it.next(), rb.next());
        }


        rb = RoadBookCalculator.calculateRoadBook(s, c1, c2, i);
        attendu = new ArrayList<Instruction>() {{
            add(Instruction.TURNRIGHT);
            add(Instruction.TURNRIGHT);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.TURNRIGHT);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
            add(Instruction.FORWARD);
        }};
        it = attendu.iterator();
        int i = 0;
        while(it.hasNext() && rb.hasInstruction()) {
            i ++;
            Assert.assertEquals("Parcours emprunté, étape " + i, it.next(), rb.next());
        }

        /*
            FIN TOUT NOEUD ET TOUT SEGMENT
            TODO: tout chemin indépendant
         */
    }

}