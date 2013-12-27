package robot;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: aquassaut
 * Date: 12/26/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class RobotTest {
    public Robot unLandedR, landedR;
    public Random terre;

    public RobotTest() {
        unLandedR = new Robot();
        landedR = new Robot();

        terre = mock(Random.class);
        when(terre.nextInt(anyInt())).thenReturn(0);
    }

    @Rule
    public ExpectedException e = ExpectedException.none();

    @Test
    public void testLand() throws Exception {
        //on se fiche du random, pas besoin de mock
        landedR.land(new Coordinates(0,0), new LandSensor(new Random()));
        Assert.assertEquals("On devrait avoir une position de 0,0", 0, landedR.getXposition());
        Assert.assertEquals("On devrait avoir une position de 0,0", 0, landedR.getYposition());
        Assert.assertEquals("On devrait faire face au nord", Direction.NORTH, landedR.getDirection());

    }

    @Test
    public void testGetXposition() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(5,2), null);
        Assert.assertEquals("On devrait avoir un x de 5",5, landedR.getXposition());

        e.expect(UnlandedRobotException.class);
        unLandedR.getXposition();
        e = ExpectedException.none();
    }

    @Test
    public void testGetYposition() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(5,2), null);
        Assert.assertEquals("On devrait avoir un y de 2",2, landedR.getYposition());

        e.expect(UnlandedRobotException.class);
        unLandedR.getYposition();
        e = ExpectedException.none();
    }

    @Test
    public void testGetDirection() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(5,2), null);
        Assert.assertEquals("On devrait faire face au nord",Direction.NORTH, landedR.getDirection());

        e.expect(UnlandedRobotException.class);
        unLandedR.getDirection();
        e = ExpectedException.none();
    }

    @Test
    public void testMoveForward() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.moveForward();
        Assert.assertEquals("On devrait être à -1 en y",-1, landedR.getYposition());

        e.expect(UnlandedRobotException.class);
        unLandedR.moveForward();
        e = ExpectedException.none();
    }

    @Test
    public void testMoveBackward() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.moveBackward();
        Assert.assertEquals("On devrait être à 1 en y",1, landedR.getYposition());

        e.expect(UnlandedRobotException.class);
        unLandedR.moveBackward();
        e = ExpectedException.none();
    }

    @Test
    public void testPrivateMoveTo() throws Exception {
        Battery b = mock(Battery.class);
        when(b.canDeliver(anyDouble())).thenReturn(false);
        landedR = new Robot(1.0, b);
        landedR.land(new Coordinates(0, 0), new LandSensor(terre));
        e.expect(InsufficientChargeException.class);
        landedR.moveForward(); //moveTo(MaptTools.nextForwardPosition(new Coordinates(0,0), Direction.North));
        e = ExpectedException.none();


    }

    @Test
    public void testTurnLeft() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.turnLeft();
        Assert.assertEquals("On doit faire face à l'ouest",Direction.WEST, landedR.getDirection());

        e.expect(UnlandedRobotException.class);
        unLandedR.turnLeft();
        e = ExpectedException.none();
    }

    @Test
    public void testTurnRight() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.turnRight();
        Assert.assertEquals("On doit faire face à l'est",Direction.EAST, landedR.getDirection());


        e.expect(UnlandedRobotException.class);
        unLandedR.turnRight();
        e = ExpectedException.none();
    }

    @Test
    public void testSetRoadBook() throws Exception {
        landedR = new Robot();
        Coordinates co = new Coordinates(0,0);
        landedR.land(co, new LandSensor(terre));
        //Pour tester ça, on va voir si letsGo envoie une undefinedRoadBookException
        landedR.setRoadBook(new RoadBook(new ArrayList<Instruction>()));
        landedR.letsGo();
        Assert.assertEquals("On devrait pas avoir bougé", co.getX(), landedR.getXposition());
        Assert.assertEquals("On devrait pas avoir bougé", co.getY(), landedR.getYposition());

        //On s'assure que ça plante bien si on envoie null
        landedR.setRoadBook(null);
        e.expect(UndefinedRoadbookException.class);
        landedR.letsGo();
        e = ExpectedException.none();
    }

    @Test
    public void testLetsGo() throws Exception {
        landedR = new Robot();
        Coordinates co = new Coordinates(0,0);
        landedR.land(co, new LandSensor(terre));
        landedR.setRoadBook(new RoadBook(new ArrayList<Instruction>() {{
            add(Instruction.FORWARD); //face: nord, pos : 0,-1
            add(Instruction.TURNLEFT); //face : ouest, pos : 0,-1
            add(Instruction.FORWARD); //face : ouest, pos -1,-1
            add(Instruction.TURNRIGHT); //face : nord, pos -1,-1
            add(Instruction.BACKWARD); //face : nord, pos : -1, 0
        }}));
        landedR.letsGo();
        Assert.assertEquals("on devrait avoir un x de -1", -1, landedR.getXposition());
        Assert.assertEquals("on devrait avoir un y de 0", 0, landedR.getYposition());
        Assert.assertEquals("on devrait faire face au nord", Direction.NORTH, landedR.getDirection());


    }

    @Test
    public void testComputeRoadTo() throws Exception {
        landedR = new Robot();
        Coordinates src = new Coordinates(0,0);
        Coordinates dst = new Coordinates(2,2);
        landedR.land(src, new LandSensor(terre));
        //Pour tester ça, on va voir si letsGo envoie une undefinedRoadBookException
        landedR.computeRoadTo(dst);
        landedR.letsGo();
        Assert.assertEquals("On devrait être à 2 en x", 2, landedR.getXposition());
        Assert.assertEquals("On devrait être à 2 en y", 2, landedR.getYposition());

        e.expect(UnlandedRobotException.class);
        unLandedR.computeRoadTo(null);
        e = ExpectedException.none();
    }

    // Test fonctionnel
    ////////////////////

    @Test
    public void testRobotVersLeNordParDefaut() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        Assert.assertEquals("Le robot devrait être au Nord au démarrage", Direction.NORTH, landedR.getDirection());
    }

    @Test
    public void testRobotAvantGardeDirection() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.setRoadBook(new RoadBook(new ArrayList<Instruction>() {{
            add(Instruction.FORWARD); //face: nord, pos : 0,-1
        }}));
        landedR.letsGo();
        Assert.assertEquals("on devrait avoir un x de 0", 0, landedR.getXposition());
        Assert.assertEquals("on devrait avoir un y de -1", -1, landedR.getYposition());
        Assert.assertEquals("on devrait faire face au nord", Direction.NORTH, landedR.getDirection());
    }

    @Test
    public void testRobotArriereGardeDirection() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0,0), new LandSensor(terre));
        landedR.setRoadBook(new RoadBook(new ArrayList<Instruction>() {{
            add(Instruction.BACKWARD); //face: nord, pos : 0,-1
        }}));
        landedR.letsGo();
        Assert.assertEquals("on devrait avoir un x de 0", 0, landedR.getXposition());
        Assert.assertEquals("on devrait avoir un y de 1", 1, landedR.getYposition());
        Assert.assertEquals("on devrait faire face au nord", Direction.NORTH, landedR.getDirection());
    }

    // Test si la marche avant est priviliégiée alors qu'il n'y a pas d'obstacle
    @Test
    public void testRobotArriereQueSiObstacle() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0, 0), new LandSensor(terre));
        landedR.computeRoadTo(new Coordinates(10, 10));

        RoadBook roadbook = landedR.getRoadBook();
        Instruction instruction;

        while(roadbook.hasInstruction())
        {
            instruction = roadbook.next();
            Assert.assertNotSame("Le robot ne doit reculer qu'en cas d'obstacle", instruction, Instruction.BACKWARD);
        }
    }

    // Test si la rotation la plus optimisée est choisie (donc pas 3 fois la même à la suite)
    @Test
    public void testRotationLaPlusOptimisee() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(0, 0), new LandSensor(terre));
        landedR.computeRoadTo(new Coordinates(10, -10));

        RoadBook roadbook = landedR.getRoadBook();
        Instruction instruction;
        Instruction oldInstruction = null;
        Boolean test2sameInstruct = false;

        while(roadbook.hasInstruction())
        {
            instruction = roadbook.next();
            if(((instruction == Instruction.TURNLEFT) || (instruction == Instruction.TURNRIGHT)) && instruction == oldInstruction)
            {
                Assert.assertTrue("Le robot doit utiliser la rotation la plus adaptée", test2sameInstruct);
                test2sameInstruct = true;
            }
            else
                test2sameInstruct = false;

            oldInstruction = instruction;
        }
    }

    // Test avec exemple donné par les specs
    @Test
    public void testRobotParcoursConnu() throws Exception {
        landedR = new Robot();
        landedR.land(new Coordinates(5, 5), new LandSensor(terre));
        landedR.computeRoadTo(new Coordinates(4, 4));

        RoadBook roadbook = landedR.getRoadBook();
        Instruction instruction;

        instruction = roadbook.next();
        Assert.assertEquals("Le robot doit aller en avant", instruction, Instruction.FORWARD);

        instruction = roadbook.next();
        Assert.assertEquals("Le robot doit aller à gauche", instruction, Instruction.TURNLEFT);

        instruction = roadbook.next();
        Assert.assertEquals("Le robot doit aller en avant", instruction, Instruction.FORWARD);
    }
}
