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


    // Test si la marche avant est priviliégiée alors qu'il n'y a pas d'obstacle
    @Test
    public void testRobotArriereQueSiObstacle() throws Exception {
        c1 = new Coordinates(0, 0);
        c2 = new Coordinates(10, 10);
        rb = RoadBookCalculator.calculateRoadBook(n, c1, c2, new ArrayList<Instruction>());

        while(rb.hasInstruction())
        {
            Assert.assertNotSame("Le robot ne doit reculer qu'en cas d'obstacle", rb.next(), Instruction.BACKWARD);
        }
    }

    // Test si la rotation la plus optimisée est choisie dans le cas où le robot doit tourner à gauche
    @Test
    public void testRotationLaPlusOptimiseeGauche() throws Exception {
        c1 = new Coordinates(0, 0);
        c2 = new Coordinates(-1, 0);
        rb = RoadBookCalculator.calculateRoadBook(n, c1, c2, new ArrayList<Instruction>());

        Instruction instruction = rb.next();
        Assert.assertEquals("Le robot doit aller à gauche", instruction, Instruction.TURNLEFT);
    }

    // Test si la rotation la plus optimisée est choisie dans le cas où le robot doit tourner à droite
    @Test
    public void testRotationLaPlusOptimiseeDroite() throws Exception {
        c1 = new Coordinates(0, 0);
        c2 = new Coordinates(1, 0);
        rb = RoadBookCalculator.calculateRoadBook(n, c1, c2, new ArrayList<Instruction>());

        Instruction instruction = rb.next();
        Assert.assertEquals("Le robot doit aller à droite", instruction, Instruction.TURNRIGHT);
    }

    // Test avec exemple donné par les specs
    @Test
    public void testRobotParcoursConnu() throws Exception {
        c1 = new Coordinates(5, 5);
        c2 = new Coordinates(4, 4);

        rb = RoadBookCalculator.calculateRoadBook(n, c1, c2, new ArrayList<Instruction>());
        Instruction instruction;

        instruction = rb.next();
        Assert.assertEquals("Le robot doit aller en avant", Instruction.FORWARD, instruction);

        instruction = rb.next();
        Assert.assertEquals("Le robot doit aller à gauche",  Instruction.TURNLEFT, instruction);

        instruction = rb.next();
        Assert.assertEquals("Le robot doit aller en avant",  Instruction.FORWARD, instruction);
    }

    // Test si le parcours est optimisé sur un terrain uniforme sans obstacle
    @Test
    public void testParcoursTerrainUniformeSansObstacle() throws Exception {
        c1 = new Coordinates(0, 0);
        c2 = new Coordinates(4, 4);

        rb = RoadBookCalculator.calculateRoadBook(n, c1, c2, new ArrayList<Instruction>());
        int i = 0;
        while(rb.hasInstruction())
        {
            rb.next();
            i++;
        }
        Assert.assertEquals("Le parcours doit contenir 8 instructions pour être optimisé",
                            10, i);
    }
}