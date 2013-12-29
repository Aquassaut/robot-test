package robot;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.rmi.UnexpectedException;
import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * User: aquassaut
 * Date: 12/2/13
 * Time: 10:04 AM
 */
public class LandSensorTest {

    private LandSensor ls;
    private Random terre, infranchissable, rand;
    private Coordinates src, dst;

    public LandSensorTest() {
        terre = mock(Random.class);
        when(terre.nextInt(anyInt())).thenReturn(0);

        infranchissable = mock(Random.class);
        when(infranchissable.nextInt(anyInt())).thenReturn(4);

    }

    @Rule
    public ExpectedException e = ExpectedException.none();

    @Test
    public void testgptpecToutNoeud1() throws Exception {
        //1 2 3
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(-1);
        ls = new LandSensor(rand);

        src = new Coordinates(0,0);
        dst = new Coordinates(0,0);
        e.expect(LandSensorDefaillance.class);
        ls.getPointToPointEnergyCoefficient(src, dst);
    }

    @Test
    public void testgptpecToutNoeud2() throws Exception {
        //1 2 4 5 6
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(3, -1);
        ls = new LandSensor(rand);

        src = new Coordinates(0,0);
        dst = new Coordinates(0,0);
        e.expect(LandSensorDefaillance.class);
        ls.getPointToPointEnergyCoefficient(src, dst);
    }

    @Test
    public void testgptpecToutNoeud3() throws Exception {
        //1 2 4 5 7 8 9
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(3, 4);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);
        e.expect(InaccessibleCoordinate.class);
        ls.getPointToPointEnergyCoefficient(src, dst);

    }
    @Test
    public void testgptpecToutNoeud4() throws Exception {
        //1 2 4 5 7 8 R

        double result;

        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0); //terre
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);

        result = ls.getPointToPointEnergyCoefficient(src, dst);
        Assert.assertEquals("Ca devrait faire ~1", 1, result, 0.001);
    }

    //Manque (1,4) et (4,7) pour avoir tous les arcs
    @Test
    public void testgptpecToutArcs() throws Exception {
        //Ici on fait comme dans Toutoeuf4, mais 2 fois comme ça
        //les coords sont dans carte et et on doit plus les ajouter
        // => on passe par (1,4) et (4,7)
        double result;

        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0); //terre
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);
        ls.getPointToPointEnergyCoefficient(src, dst);
        result = ls.getPointToPointEnergyCoefficient(src, dst);
        Assert.assertEquals("Ca devrait faire ~1", 1, result, 0.001);
    }

    @Test
    public void testgptpecToutCheminLI() throws Exception {
        //1 4 5 6
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0, -1); //terre
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        ls.getPointToPointEnergyCoefficient(src, src);
        dst = new Coordinates(0, 1);
        e.expect(LandSensorDefaillance.class);
        ls.getPointToPointEnergyCoefficient(src, dst);
    }

    // Test la consommation de batterie dans la roche
    @Test
    public void testConsoRoche() throws Exception {
        double result;

        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(1);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);

        result = ls.getPointToPointEnergyCoefficient(src, dst);
        Assert.assertEquals("Ca devrait faire ~2", 2, result, 0.001);
    }

    // Test la consommation de batterie dans le sable
    @Test
    public void testConsoSable() throws Exception {
        double result;

        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(2);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);

        result = ls.getPointToPointEnergyCoefficient(src, dst);
        Assert.assertEquals("Ca devrait faire ~4", 4, result, 0.001);
    }

    // Test la consommation de batterie dans la boue
    @Test
    public void testConsoBoue() throws Exception {
        double result;

        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(3);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);

        result = ls.getPointToPointEnergyCoefficient(src, dst);
        Assert.assertEquals("Ca devrait faire ~3", 3, result, 0.001);
    }

    // Test getPointToPointEnergyCoefficient dans le cas où la position initiale est "Infranchissable"
    @Test
    public void testGetEnergyCoefficientCoorInitialeInfranch() throws Exception {
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(4,0);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(1, 0);

        e.expect(InaccessibleCoordinate.class);
        ls.getPointToPointEnergyCoefficient(src, dst);
    }

    // Test si getPointToPointEnergyCoefficient pour un point trop loin renvoie RuntimeException("Point trop distant")
    @Test
    public void testGetEnergyCoefficientHorsZone() throws Exception {
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(10, 0);

        e.expect(RuntimeException.class);
        ls.getPointToPointEnergyCoefficient(src, dst);
    }

    // Test getPointToPointEnergyCoefficient ne renvoie pas d'exception pour un point dans la zone connue
    @Test
    public void testGetEnergyCoefficientDansZone() throws Exception {
        rand = mock(Random.class);
        when(rand.nextInt(anyInt())).thenReturn(0);
        ls = new LandSensor(rand);
        src = new Coordinates(0, 0);
        dst = new Coordinates(3, 0);

        ls.getPointToPointEnergyCoefficient(src, dst);
    }

}
